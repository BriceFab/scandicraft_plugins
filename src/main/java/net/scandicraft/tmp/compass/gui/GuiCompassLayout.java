package net.scandicraft.tmp.compass.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.scandicraft.mods.impl.ModCompass;

import java.io.IOException;

public class GuiCompassLayout extends GuiCompass {
    private GuiSlider sliderWidth;

    private GuiSlider sliderCWidth;

    public GuiCompassLayout(ModCompass mod, GuiScreen parent) {
        super(mod, parent);
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 60, this.height / 2 - 25, 120, 20, getDetailString()));
        this.buttonList.add(this.sliderWidth = new GuiSlider(1, this.width / 2 - 60, this.height / 2, 120, 20, "Width: ", "", 50.0D, 300.0D, this.compass.width, false, true));
        this.buttonList.add(this.sliderCWidth = new GuiSlider(2, this.width / 2 - 60, this.height / 2 + 25, 120, 20, "Spacing: ", "", 200.0D, 1200.0D, this.compass.cwidth, false, true));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 60, this.height / 2 + 50, 120, 20, "Done"));
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                this.compass.details = (this.compass.details + 1) % 3;
                button.displayString = getDetailString();
                break;
            case 3:
                this.mc.displayGuiScreen(this.parent);
                break;
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        updateSliders();
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        updateSliders();
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        updateSliders();
    }

    private void updateSliders() {
        this.compass.width = 100;
        this.compass.cwidth = 100;
        this.compass.width = this.sliderWidth.getValueInt();
        this.compass.cwidth = this.sliderCWidth.getValueInt();
    }

    private String getDetailString() {
        switch (this.compass.details) {
            case 0:
                return "Details: " + EnumChatFormatting.GOLD + "LOW";
            case 1:
                return "Details: " + EnumChatFormatting.YELLOW + "MED";
            case 2:
                return "Details: " + EnumChatFormatting.GREEN + "HIGH";
        }
        return "Details: ???";
    }
}
