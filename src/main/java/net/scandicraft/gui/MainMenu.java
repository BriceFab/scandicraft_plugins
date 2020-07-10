package net.scandicraft.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.config.Config;
import net.scandicraft.Theme;
import net.scandicraft.client.ScandiCraftClient;
import net.scandicraft.gui.buttons.ButtonTemplate;
import net.scandicraft.gui.settings.GuiOptions;
import net.scandicraft.logs.LogManagement;
import net.scandicraft.utils.ArrayUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends GuiScreen implements GuiYesNoCallback {
    private static final ResourceLocation backgroundPath = new ResourceLocation("textures/gui/background.png");
    private final ArrayList<ButtonTemplate> templateButtons = new ArrayList<>();
    private final ServerData server = new ServerData("ScandiCraft", Config.SERVER_IP_AND_PORT, false);

    public MainMenu() {
        templateButtons.add(new ButtonTemplate(0, I18n.format("menu.singleplayer")));
        if (Config.ENV == Config.ENVIRONNEMENT.DEV) {
            templateButtons.add(new ButtonTemplate(1, I18n.format("menu.multiplayer")));
        }
        templateButtons.add(new ButtonTemplate(4, I18n.format("ScandiCraft")));
        templateButtons.add(new ButtonTemplate(2, I18n.format("menu.options"), Theme.DEFAULT_BUTTON_WIDTH / 2, 0));
        templateButtons.add(new ButtonTemplate(3, I18n.format("menu.quit"), Theme.DEFAULT_BUTTON_WIDTH / 2, 1));
//        LogManagement.info("test server data: " + server.serverIP + " - " + server.populationInfo);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void initGui() {
        this.addButtons();
//        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, yIn, I18n.format("menu.singleplayer")));
//        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, yIn + 24, I18n.format("menu.multiplayer")));
//        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, yIn + 72 + 12, 98, 20, I18n.format("menu.options")));
//        this.buttonList.add(new GuiButton(3, this.width / 2 + 2, yIn + 72 + 12, 98, 20, I18n.format("menu.quit")));

        ScandiCraftClient.getInstance().getDiscordRP().update("Menu principal");
    }

    private void addButtons() {
        final int totalNbrButtons = templateButtons.size();
        int totalNbrFullButton = ArrayUtils.filter(ButtonTemplate::getFullWidth, templateButtons).size() / 2;
        final int ySpace = 24;
        final int startHeight = (this.height / 2) - (ySpace * (totalNbrButtons - totalNbrFullButton) / 2);
        for (int i = 0; i < totalNbrButtons; i++) {
            ButtonTemplate buttonTemplate = templateButtons.get(i);
            int shiftX = 0;
            int shiftY = 0;
            if (buttonTemplate.getFullWidth()) {    //button normal
                shiftX = buttonTemplate.getWidth() / 2;
            } else {    //button column
                shiftX = (buttonTemplate.getWidth() / 2) * ((buttonTemplate.getColumnIndex() + 1) * 2);
                if (buttonTemplate.getColumnIndex() > 0) {
                    shiftX -= (buttonTemplate.getColumnIndex() + 1) * buttonTemplate.getWidth();
                    shiftX -= 2 * buttonTemplate.getColumnIndex();  //espace entre les 2 buttons sur la même ligne
                    shiftY -= ySpace * buttonTemplate.getColumnIndex();
                }
            }
            int x = this.width / 2 - shiftX;
            int y = (startHeight + ySpace * i) + shiftY;
            this.buttonList.add(new GuiButton(buttonTemplate.getId(), x, y, buttonTemplate.getWidth(), buttonTemplate.getHeight(), buttonTemplate.getText()));
        }
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
                case 4: //scandicraft server
                    this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, this.server));
                    break;
                default:
                    LogManagement.warn("no action");
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
