package net.scandicraft.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class HUDConfigScreen extends GuiScreen {

    private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<>();

    private Optional<IRenderer> selectedRenderer = Optional.empty();

    private int prevX, prevY;

    public HUDConfigScreen(HUDManager api) {
        Collection<IRenderer> registeredRenderers = api.getRegisteredRenderers();

        for (IRenderer renderer : registeredRenderers) {
            if (renderer.isEnabled()) {
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

        final float zBackup = this.zLevel;
        this.zLevel = 200;

        //Bordure de la fenêtre mc
        //this.drawHollowRect(0, 0, this.width - 1, this.height - 1, 0xFFFF0000);

        for (IRenderer renderer : renderers.keySet()) {
            ScreenPosition pos = renderers.get(renderer);

            renderer.renderDummy(pos);

            final int padding = 5;

            this.drawHollowRect(pos.getAbsoluteX() - padding, pos.getAbsoluteY() - padding, renderer.getWidth() + (padding * 2), renderer.getHeight() + (padding * 2), new Color(0, 0, 0, 50).getRGB());
            this.drawScaleRect(pos.getAbsoluteX() + renderer.getWidth() + padding, pos.getAbsoluteY() + renderer.getHeight() + padding, 10, 10, Color.GREEN.getRGB());
        }

        this.zLevel = zBackup;
    }

    private void drawScaleRect(int x, int y, int w, int h, int color) {
        this.drawHollowRect(x - w / 2, y - h / 2, w, h, color);
        // Gui.drawRect(10, 100, w, h, color);
    }

    private void drawHollowRect(int x, int y, int w, int h, int color) {
        this.drawHorizontalLine(x, x + w, y, color);
        this.drawHorizontalLine(x, x + w, y + h, color);

        this.drawVerticalLine(x, y + h, y, color);
        this.drawVerticalLine(x + w, y + h, y, color);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            renderers.forEach(IRenderConfig::save);
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void mouseClickMove(int x, int y, int button, long time) {
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

        int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
        int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));

        pos.setAbsolute(absoluteX, absoluteY);
    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton) throws IOException {
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

            if (mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth()) {
                if (mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight()) {
                    return true;
                }
            }

            return false;
        }
    }
}
