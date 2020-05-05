package net.scandicraft.mods.impl.compass.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.scandicraft.mods.impl.compass.ModCompass;

import java.io.IOException;

public class GuiCompassStyle extends GuiCompass {
    private GuiSlider sliderMarkerTint;

    private GuiSlider sliderDirectionTint;

    public GuiCompassStyle(ModCompass mod, GuiScreen parent) {
        super(mod, parent);
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 60, this.height / 2 - 75, 120, 20,
                getColoredBool("Background: ", this.compass.background)));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 60, this.height / 2 - 50, 120, 20,
                getColoredBool("Chroma: ", this.compass.chroma)));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 60, this.height / 2 - 25, 120, 20,
                getColoredBool("Shadow: ", this.compass.shadow)));
        this.buttonList.add(this.sliderMarkerTint = new GuiSlider(3, this.width / 2 - 60, this.height / 2, 120, 20, "Marker Tint: ", "", 0.0D, 100.0D, this.compass.tintMarker, false, true));
        this.buttonList.add(this.sliderDirectionTint = new GuiSlider(4, this.width / 2 - 60, this.height / 2 + 25, 120, 20, "Direction Tint: ", "", 0.0D, 100.0D, this.compass.tintDirection, false, true));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 60, this.height / 2 + 50, 120, 20, "Done"));
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                this.compass.background = !this.compass.background;
                button.displayString = getColoredBool("Background: ", this.compass.background);
                break;
            case 1:
                this.compass.chroma = !this.compass.chroma;
                button.displayString = getColoredBool("Chroma: ", this.compass.chroma);
                break;
            case 2:
                this.compass.shadow = !this.compass.shadow;
                button.displayString = getColoredBool("Shadow: ", this.compass.shadow);
                break;
            case 5:
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
        this.compass.tintMarker = this.sliderMarkerTint.getValueInt();
        this.compass.tintDirection = this.sliderDirectionTint.getValueInt();
    }
}
