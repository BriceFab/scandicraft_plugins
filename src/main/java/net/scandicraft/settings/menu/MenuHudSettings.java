package net.scandicraft.settings.menu;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import optifine.TooltipManager;

public class MenuHudSettings extends GuiScreen {
    protected String screenTitle = I18n.format("scandicraft.menu.hud.settings");
    private final TooltipManager tooltipManager = new TooltipManager(this);

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 15, 16777215);

        super.drawScreen(mouseX, mouseY, partialTicks);
        this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);  //a besoin que les boutons implements IOptionControl
    }
}
