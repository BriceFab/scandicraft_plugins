package net.scandicraft.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.scandicraft.Config;
import net.scandicraft.gui.buttons.GuiSlider;
import net.scandicraft.mods.Mod;
import net.scandicraft.settings.ScandiCraftSettings;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

public class HUDConfigScreen extends GuiScreen {

    private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<>();

    private Optional<IRenderer> selectedRenderer = Optional.empty();

    private int prevX, prevY;

    private final int padding = 5;
    private final int scaleSize = 5;

    public HUDConfigScreen(HUDManager api) {
        Collection<IRenderer> registeredRenderers = api.getRegisteredRenderers();

        for (IRenderer renderer : registeredRenderers) {
            Mod actMod = (Mod) renderer;
            if (actMod.isEnabled() && actMod.isUsable()) {
                ScreenPosition pos = renderer.load();
                if (pos == null) {
                    pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
                }

                adjustBounds(renderer, pos);
                this.renderers.put(renderer, pos);
            }
        }

        org.lwjgl.opengl.Display.setVSyncEnabled(true); //fix FPS error
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        final float zBackup = this.zLevel;
        this.zLevel = 200;

        //Bordure de la fenêtre mc
        //this.drawHollowRect(0, 0, this.width - 1, this.height - 1, 0xFFFF0000);

        for (IRenderer renderer : renderers.keySet()) {
            ScreenPosition pos = renderers.get(renderer);

            GL11.glPushMatrix();
            GL11.glTranslatef(-pos.getAbsoluteX() * (pos.getScale() - 1.0F), -pos.getAbsoluteY() * (pos.getScale() - 1.0F), 0.0F);
            GL11.glScalef(pos.getScale(), pos.getScale(), 1.0F);

            renderer.renderDummy(pos);
            this.drawHollowRect(pos.getAbsoluteX() - padding, pos.getAbsoluteY() - padding, renderer.getWidth() + (padding * 2), renderer.getHeight() + (padding * 2), new Color(0, 0, 0, 50).getRGB());
//            this.drawScaleRect(pos.getAbsoluteX() + renderer.getWidth() + padding, pos.getAbsoluteY() + renderer.getHeight() + padding, scaleSize, scaleSize, Color.GREEN.getRGB());

            GL11.glScalef(1.0F / pos.getScale(), 1.0F / pos.getScale(), 1.0F);
            GL11.glTranslatef(pos.getAbsoluteX() * (pos.getScale() - 1.0F), pos.getAbsoluteY() * (pos.getScale() - 1.0F), 0.0F);
            GL11.glPopMatrix();
        }

        this.zLevel = zBackup;
    }

    @Override
    public void initGui() {
        int i = 0;
        for (Iterator<IRenderer> iterator = renderers.keySet().iterator(); iterator.hasNext(); i++) {
            IRenderer renderer = iterator.next();
            ScreenPosition pos = renderers.get(renderer);
            Config.print_debug("init gui renderer " + i + " sacle: " + pos.getScale());

            int currentButton = i;
            int currentWidth = getSliderWidth(renderer, pos);
            int currentHeigth = 12;
            this.buttonList.add(new GuiSlider(i, getSliderX(renderer, pos, currentWidth), getSliderY(renderer, pos), currentWidth, currentHeigth, pos.getScale(), (res) -> {
                float new_scale = res.floatValue();
                Config.print_debug("callback result: " + res.floatValue());
                pos.setScale(new_scale);
                GuiButton b = this.buttonList.get(currentButton);
                Config.print_debug("current button " + b);
            }, (button) -> {
                Config.print_debug("onfinish scale: " + pos.getScale());
                button.setWidth(getSliderWidth(renderer, pos));
                button.setxPosition(getSliderX(renderer, pos, button.getWidth()));
                button.setyPosition(getSliderY(renderer, pos));
                button.setSliderValue(pos.getScale());
            }, 0.0D, 1.0F));
        }
    }

    private int getSliderWidth(IRenderer renderer, ScreenPosition pos) {
        //Center
//        return (int) ((renderer.getWidth() + padding * 2) * pos.getScale());
        return (int) ((renderer.getWidth() + padding * 2 + 1) * pos.getScale());
    }

    private int getSliderX(IRenderer renderer, ScreenPosition pos, int w) {
        //Centrer
//        return (int) (pos.getAbsoluteX() + ((renderer.getWidth() / 2 - w / 2) * pos.getScale()));
        return (int) (pos.getAbsoluteX() - (padding * pos.getScale()));
    }

    private int getSliderY(IRenderer renderer, ScreenPosition pos) {
        return (int) (pos.getAbsoluteY() + ((renderer.getHeight() + padding) * pos.getScale()));
    }

    private void drawScaleRect(int x, int y, int w, int h, int color) {
        this.drawHollowRect(x - w / 2, y - h / 2, w, h, color);
        Gui.drawRect(x - padding / 2, y - padding / 2, x + padding, y + padding, color);
    }

    private void drawHollowRect(int x, int y, int w, int h, int color) {
        this.drawHorizontalLine(x, x + w, y, color);
        this.drawHorizontalLine(x, x + w, y + h, color);

        this.drawVerticalLine(x, y + h, y, color);
        this.drawVerticalLine(x + w, y + h, y, color);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == ScandiCraftSettings.OPEN_HUD_MANAGER.getKeyCode()) {
            renderers.forEach(IRenderConfig::save);
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void mouseClickMove(int x, int y, int button, long time) {
        super.mouseClickMove(x, y, button, time);

        if (selectedRenderer.isPresent()) {
            moveSelectedRenderBy(x - prevX, y - prevY);
        }

        this.prevX = x;
        this.prevY = y;
    }

    private void moveSelectedRenderBy(int offsetX, int offsetY) {
        IRenderer renderer = selectedRenderer.get();
        ScreenPosition pos = renderers.get(renderer);

        pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);
        adjustBounds(renderer, pos);
    }

    @Override
    public void onGuiClosed() {
        for (IRenderer renderer : renderers.keySet()) {
            renderer.save(renderers.get(renderer));
        }

        org.lwjgl.opengl.Display.setVSyncEnabled(false); //fix FPS error
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    private void adjustBounds(IRenderer renderer, ScreenPosition pos) {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();

        //avec le padding
//        int absoluteX = Math.max((int) (padding * pos.getScale()), Math.min(pos.getAbsoluteX(), Math.max(screenWidth - (int) ((renderer.getWidth() + padding + 1) * pos.getScale()), 0)));
//        int absoluteY = Math.max((int) (padding * pos.getScale()), Math.min(pos.getAbsoluteY(), Math.max(screenHeight - (int) ((renderer.getHeight() + padding + 1) * pos.getScale()), 0)));

        int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - (int) ((renderer.getWidth() + 1) * pos.getScale()), 0)));
        int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - (int) ((renderer.getHeight() + 1) * pos.getScale()), 0)));

        pos.setAbsolute(absoluteX, absoluteY);
    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton) throws IOException {
        super.mouseClicked(x, y, mouseButton);

        this.prevX = x;
        this.prevY = y;

        loadMouseOver(x, y);
    }

    private void loadMouseOver(int x, int y) {
        this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
    }

    private class MouseOverFinder implements Predicate<IRenderer> {

        private final int mouseX, mouseY;

        public MouseOverFinder(int x, int y) {
            this.mouseX = x;
            this.mouseY = y;
        }

        @Override
        public boolean test(IRenderer renderer) {
            ScreenPosition pos = renderers.get(renderer);
            int absoluteX = pos.getAbsoluteX();
            int absoluteY = pos.getAbsoluteY();
            float scale = pos.getScale();

            return (mouseX > absoluteX - (padding * scale) && mouseY > absoluteY - (padding * scale) && mouseX < absoluteX + (renderer.getWidth() + padding) * scale && mouseY < absoluteY + (renderer.getHeight() + padding) * scale);
        }
    }
}
