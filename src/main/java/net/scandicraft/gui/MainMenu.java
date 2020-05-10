package net.scandicraft.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.Config;
import net.scandicraft.client.ScandiCraftClient;
import net.scandicraft.gui.settings.GuiOptions;

import java.io.IOException;

public class MainMenu extends GuiScreen implements GuiYesNoCallback {
    private static final ResourceLocation backgroundPath = new ResourceLocation("textures/gui/background.png");

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void initGui() {
        int yIn = this.height / 4 + 48;

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, yIn, I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, yIn + 24, I18n.format("menu.multiplayer")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, yIn + 72 + 12, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 2, yIn + 72 + 12, 98, 20, I18n.format("menu.quit")));

        ScandiCraftClient.getInstance().getDiscordRP().update("Menu principal");
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            switch (button.id) {
                case 0: //singleplayer
                    this.mc.displayGuiScreen(new GuiSelectWorld(this));
                    break;
                case 1: //multiplayer
                    this.mc.displayGuiScreen(new GuiMultiplayer(this));
                    break;
                case 2: //options
                    this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings, this.mc.scandiCraftSettings));
                    break;
                case 3: //quitter
                    this.mc.shutdown();
                    break;
                default:
                    Config.print_debug("no action");
                    break;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableAlpha();
        this.renderBackGround();
        GlStateManager.enableAlpha();

        String copyright_mojang = Config.COPYRIGHT;
        this.drawString(this.fontRendererObj, copyright_mojang, this.width - this.fontRendererObj.getStringWidth(copyright_mojang) - 2, this.height - 10, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void renderBackGround() {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();

        mc.getTextureManager().bindTexture(backgroundPath);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, width, height, width, height, width, height);
    }
}
