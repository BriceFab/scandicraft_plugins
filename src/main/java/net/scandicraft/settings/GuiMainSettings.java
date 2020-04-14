package net.scandicraft.settings;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.scandicraft.Config;
import net.scandicraft.settings.buttons.BooleanButton;
import optifine.Lang;
import optifine.TooltipManager;

import java.io.IOException;

public class GuiMainSettings extends GuiScreen {
    private GuiScreen parentGuiScreen;
    protected String screenTitle = "ScandiCraft Settings";
    private ScandiCraftSettings scandicraftSettings;
    private static ScandiCraftSettings.Options[] options = new ScandiCraftSettings.Options[]{ScandiCraftSettings.Options.ITEMS_PHYSICS};

    private TooltipManager tooltipManager = new TooltipManager(this);

    public GuiMainSettings(GuiScreen parentScreenIn, ScandiCraftSettings scandicraftSettings) {
        this.parentGuiScreen = parentScreenIn;
        this.scandicraftSettings = scandicraftSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        this.screenTitle = I18n.format("scandicraft.settings.title");
        this.buttonList.clear();

        for (int i = 0; i < options.length; ++i) {
            ScandiCraftSettings.Options actOption = options[i];

            if (actOption != null) {
                int x = this.width / 2 - 155 + i % 2 * 160;
                int y = this.height / 6 + 21 * (i / 2) - 12;

                if (i == 0) y += 10; //espace du haut

                this.buttonList.add(new BooleanButton(actOption.returnEnumOrdinal(), x, y, actOption, this.scandicraftSettings.getKeyBinding(actOption)));
            }
        }

        int l = this.height / 6 + 21 * (4 / 2) - 12;
        int i1 = this.width / 2 - 155;
        this.buttonList.add(new GuiOptionButton(231, i1, l, Lang.get("of.options.shaders")));
        i1 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(202, i1, l, Lang.get("of.options.quality")));
        l = l + 21;
        i1 = this.width / 2 - 155;
        this.buttonList.add(new GuiOptionButton(201, i1, l, Lang.get("of.options.details")));
        i1 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(212, i1, l, Lang.get("of.options.performance")));
        l = l + 21;
        i1 = this.width / 2 - 155;
        this.buttonList.add(new GuiOptionButton(211, i1, l, Lang.get("of.options.animations")));
        i1 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(222, i1, l, Lang.get("of.options.other")));
        l = l + 21;
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done")));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        Config.print_debug("actionPerformed !!!");

        if (button.enabled) {
            if (button instanceof BooleanButton) {
                ScandiCraftSettings.Options actOption = ((BooleanButton) button).getOption();
                if (actOption.isBoolean()) {
                    actOption.setOptionValue(!(Boolean) actOption.getValue());
                    button.displayString = this.scandicraftSettings.getKeyBinding(actOption);
                }
                this.mc.scandiCraftSettings.saveOptions();
            }

            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 15, 16777215);

        super.drawScreen(mouseX, mouseY, partialTicks);
        this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
    }

    /*
    public static int getButtonWidth(GuiButton p_getButtonWidth_0_)
    {
        return p_getButtonWidth_0_.width;
    }

    public static int getButtonHeight(GuiButton p_getButtonHeight_0_)
    {
        return p_getButtonHeight_0_.height;
    }
     */

    /*
    public static void drawGradientRect(GuiScreen p_drawGradientRect_0_, int p_drawGradientRect_1_, int p_drawGradientRect_2_, int p_drawGradientRect_3_, int p_drawGradientRect_4_, int p_drawGradientRect_5_, int p_drawGradientRect_6_)
    {
        p_drawGradientRect_0_.drawGradientRect(p_drawGradientRect_1_, p_drawGradientRect_2_, p_drawGradientRect_3_, p_drawGradientRect_4_, p_drawGradientRect_5_, p_drawGradientRect_6_);
    }
     */
}
