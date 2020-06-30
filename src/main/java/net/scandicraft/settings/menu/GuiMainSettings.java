package net.scandicraft.settings.menu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.scandicraft.settings.ScandiCraftSettings;
import net.scandicraft.settings.buttons.BooleanButton;
import optifine.TooltipManager;

import java.io.IOException;

public class GuiMainSettings extends GuiScreen {
    private final GuiScreen parentGuiScreen;
    protected String screenTitle = "ScandiCraft Settings";
    private final ScandiCraftSettings scandicraftSettings;
    private static final ScandiCraftSettings.Options[] options = new ScandiCraftSettings.Options[]{
            ScandiCraftSettings.Options.ITEMS_PHYSICS,
            ScandiCraftSettings.Options.INVENTORY_POTIONS,
            ScandiCraftSettings.Options.MOD_ARMOR,
            ScandiCraftSettings.Options.MOD_CPS,
            ScandiCraftSettings.Options.MOD_FPS,
            ScandiCraftSettings.Options.MOD_PING,
            ScandiCraftSettings.Options.MOD_POTION,
            ScandiCraftSettings.Options.MOD_KEYSTROKE,
            ScandiCraftSettings.Options.MOD_COMPASS,
            ScandiCraftSettings.Options.MOD_TOGGLE,
    };

    private final TooltipManager tooltipManager = new TooltipManager(this);

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

            int x = this.width / 2 - 155 + i % 2 * 160;
            int y = this.height / 6 + 21 * (i / 2);

            if (i != 0 && i != 1) {
                y += i * 2;
            }

            this.buttonList.add(new BooleanButton(actOption.returnEnumOrdinal(), x, y, actOption, this.scandicraftSettings.getKeyBinding(actOption)));
        }

        int l = this.height / 6 + 21 * (4 / 2) - 12;
        int i1 = this.width / 2 - 155;
//        this.buttonList.add(new GuiOptionButton(231, i1, l, Lang.get("of.options.shaders")));
        i1 = this.width / 2 - 155 + 160;
//        this.buttonList.add(new GuiOptionButton(202, i1, l, Lang.get("of.options.quality")));
        l = l + 21;
        i1 = this.width / 2 - 155;
//        this.buttonList.add(new GuiOptionButton(201, i1, l, Lang.get("of.options.details")));
        i1 = this.width / 2 - 155 + 160;
//        this.buttonList.add(new GuiOptionButton(212, i1, l, Lang.get("of.options.performance")));
        l = l + 21;
        i1 = this.width / 2 - 155;
//        this.buttonList.add(new GuiOptionButton(211, i1, l, Lang.get("of.options.animations")));
        i1 = this.width / 2 - 155 + 160;
//        this.buttonList.add(new GuiOptionButton(222, i1, l, Lang.get("of.options.other")));
        l = l + 21;
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done")));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
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
}
