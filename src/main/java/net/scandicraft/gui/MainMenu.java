package net.scandicraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.Environnement;
import net.scandicraft.client.ScandiCraftClient;
import net.scandicraft.config.Config;
import net.scandicraft.config.Theme;
import net.scandicraft.gui.buttons.DefaultButton;
import net.scandicraft.gui.buttons.TexturedButton;
import net.scandicraft.gui.buttons.helper.BaseButton;
import net.scandicraft.gui.settings.GuiOptions;
import net.scandicraft.logs.LogManagement;

import java.io.IOException;

public class MainMenu extends GuiScreen implements GuiYesNoCallback {
    private static final ResourceLocation backgroundPath = new ResourceLocation("scandicraft/menu/main_background.jpg");
    private final ServerData server = new ServerData("ScandiCraft", Config.SERVER_IP_AND_PORT, false);
    private final ServerData local_server = new ServerData("localhost", "127.0.0.1:25565", true);
    private final int previous_guiScale;
    private boolean hasUpdateScale = false;

    public MainMenu() {
        ScandiCraftClient.getInstance().getDiscordRP().update("Menu principal");

        previous_guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void initGui() {
        //scale auto
        mc.gameSettings.guiScale = 0;

        if (Config.ENV == Environnement.DEV) {
            String string_multiplayer = "Dev";
            this.buttonList.add(new DefaultButton(1, 2, 2, this.fontRendererObj.getStringWidth(string_multiplayer) + 20, Theme.DEFAULT_BUTTON_HEIGHT, string_multiplayer));
            String string_localhost = "Local";
            this.buttonList.add(new DefaultButton(5, 2, 2 + Theme.DEFAULT_BUTTON_HEIGHT, this.fontRendererObj.getStringWidth(string_localhost) + 20, Theme.DEFAULT_BUTTON_HEIGHT, string_localhost));
        }

        TexturedButton btnSolo = new TexturedButton(0, 36, 72, 0.2F,
                new ResourceLocation(Config.BASE_SC_BUTTONS_RESSOURCES + "solo.png"),
                new ResourceLocation(Config.BASE_SC_BUTTONS_RESSOURCES + "solo_hover.png")
        );
        btnSolo.setWidth(450);
        btnSolo.setHeight(200);
        this.buttonList.add(btnSolo);

        TexturedButton btnPlay = new TexturedButton(4, 37, 118, 0.2F,
                new ResourceLocation(Config.BASE_SC_BUTTONS_RESSOURCES + "play.png"),
                new ResourceLocation(Config.BASE_SC_BUTTONS_RESSOURCES + "play_hover.png")
        );
        btnPlay.setWidth(450);
        btnPlay.setHeight(200);
        this.buttonList.add(btnPlay);

        TexturedButton btnExit = new TexturedButton(3, 84, 190, 0.04F,
                new ResourceLocation(Config.BASE_SC_BUTTONS_RESSOURCES + "exit.png"),
                new ResourceLocation(Config.BASE_SC_BUTTONS_RESSOURCES + "exit_hover.png")
        );
        btnExit.setWidth(512);
        btnExit.setHeight(512);
        this.buttonList.add(btnExit);

        TexturedButton btnSettings = new TexturedButton(2, 58, 190, 0.04F,
                new ResourceLocation(Config.BASE_SC_BUTTONS_RESSOURCES + "settings.png"),
                new ResourceLocation(Config.BASE_SC_BUTTONS_RESSOURCES + "settings_hover.png")
        );
        btnSettings.setWidth(512);
        btnSettings.setHeight(512);
        this.buttonList.add(btnSettings);
    }

    @Override
    protected void actionPerformed(BaseButton button) throws IOException {
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
                case 4: //scandicraft server
                    this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, this.server, true));
                    break;
                case 5: //localhost server
                    this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, this.local_server, true));
                    break;
                default:
                    LogManagement.warn("no action");
                    break;
            }
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        //reset gui scale to previous
        Minecraft.getMinecraft().gameSettings.guiScale = this.previous_guiScale;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableAlpha();
        this.renderBackGround();
        GlStateManager.enableAlpha();

        String copyright_mojang = Config.MOJANG_COPYRIGHT;
        this.drawString(this.fontRendererObj, copyright_mojang, this.width - this.fontRendererObj.getStringWidth(copyright_mojang) - 2, this.height - 10, -1);
        String copyright_scandicraft = Config.SCANDICRAFT_COPYRIGHT;
        this.drawString(this.fontRendererObj, copyright_scandicraft, 2, this.height - 10, -1);

        this.updateScale();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void updateScale() {
        if (!hasUpdateScale && previous_guiScale != 0) {
            //Met à jour le Gui Scale
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int width = scaledresolution.getScaledWidth();
            int height = scaledresolution.getScaledHeight();
            this.setWorldAndResolution(Minecraft.getMinecraft(), width, height);

            hasUpdateScale = true;
        }
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
