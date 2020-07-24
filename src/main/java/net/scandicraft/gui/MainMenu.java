package net.scandicraft.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.client.ScandiCraftClient;
import net.scandicraft.config.Config;
import net.scandicraft.config.Theme;
import net.scandicraft.gui.buttons.ButtonTemplate;
import net.scandicraft.gui.buttons.menu.MenuPrimaryButton;
import net.scandicraft.gui.buttons.menu.MenuSecondaryButton;
import net.scandicraft.gui.settings.GuiOptions;
import net.scandicraft.logs.LogManagement;
import net.scandicraft.utils.ArrayUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends GuiScreen implements GuiYesNoCallback {
    private static final ResourceLocation backgroundPath = new ResourceLocation("scandicraft/menu/main_background.png");
    private final ArrayList<ButtonTemplate> templateButtons = new ArrayList<>();
    private final ServerData server = new ServerData("ScandiCraft", Config.SERVER_IP_AND_PORT, false);

    public MainMenu() {
        ScandiCraftClient.getInstance().getDiscordRP().update("Menu principal");
//        this.addButtons();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void initGui() {
        this.addButtons();
        this.drawButtons();
    }

    private void addButtons() {
        templateButtons.clear();

        if (Config.ENV == Config.ENVIRONNEMENT.DEV) {
            String string_multiplayer = "Dev";
            templateButtons.add(new ButtonTemplate(1, 2, 2, string_multiplayer, this.fontRendererObj.getStringWidth(string_multiplayer) + 20, Theme.DEFAULT_BUTTON_HEIGHT));
        }

        templateButtons.add(new ButtonTemplate(0, I18n.format("menu.singleplayer")));
        templateButtons.add(new ButtonTemplate(4, "Multijoueur").setGuiButtonTemplate(MenuPrimaryButton.class));
        templateButtons.add(new ButtonTemplate(2, I18n.format("menu.options"), Theme.DEFAULT_BUTTON_WIDTH / 2, 0).setGuiButtonTemplate(MenuSecondaryButton.class));
        templateButtons.add(new ButtonTemplate(3, I18n.format("menu.quit"), Theme.DEFAULT_BUTTON_WIDTH / 2, 1).setGuiButtonTemplate(MenuSecondaryButton.class));
    }

    private void drawButtons() {
        final int totalNbrButtons = templateButtons.size();
        int totalNbrFullButton = ArrayUtils.filter(ButtonTemplate::getFullWidth, templateButtons).size() / 2;
        final int ySpace = 24;
        final int startHeight = (this.height / 2) - (ySpace * (totalNbrButtons - totalNbrFullButton) / 2);
        for (int i = 0; i < totalNbrButtons; i++) {
            ButtonTemplate buttonTemplate = templateButtons.get(i);
            int x, y;
            if (!buttonTemplate.hasXYPosition()) {
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
                x = this.width / 2 - shiftX;
                y = (startHeight + ySpace * i) + shiftY;
            } else {
                x = buttonTemplate.getX();
                y = buttonTemplate.getY();
            }

            GuiButton displayButton = this.getButton(buttonTemplate, x, y);
            if (displayButton != null) {
                this.buttonList.add(displayButton);
            }
        }
    }

    private GuiButton getButton(ButtonTemplate buttonTemplate, int x, int y) {
        if (GuiButton.class.equals(buttonTemplate.getGuiButtonTemplate())) {
            return new GuiButton(buttonTemplate.getId(), x, y, buttonTemplate.getWidth(), buttonTemplate.getHeight(), buttonTemplate.getText());
        } else if (MenuSecondaryButton.class.equals(buttonTemplate.getGuiButtonTemplate())) {
            return new MenuSecondaryButton(buttonTemplate.getId(), x, y, buttonTemplate.getWidth(), buttonTemplate.getHeight(), buttonTemplate.getText());
        } else if (MenuPrimaryButton.class.equals(buttonTemplate.getGuiButtonTemplate())) {
            return new MenuPrimaryButton(buttonTemplate.getId(), x, y, buttonTemplate.getWidth(), buttonTemplate.getHeight(), buttonTemplate.getText());
        }
        return null;
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

        String copyright_mojang = Config.MOJANG_COPYRIGHT;
        this.drawString(this.fontRendererObj, copyright_mojang, this.width - this.fontRendererObj.getStringWidth(copyright_mojang) - 2, this.height - 10, -1);
        String copyright_scandicraft = Config.SCANDICRAFT_COPYRIGHT;
        this.drawString(this.fontRendererObj, copyright_scandicraft, 2, this.height - 10, -1);

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
