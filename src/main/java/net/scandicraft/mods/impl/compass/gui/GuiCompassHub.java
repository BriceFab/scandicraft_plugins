package net.scandicraft.mods.impl.compass.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.scandicraft.mods.impl.compass.ModCompass;

import java.io.IOException;

public class GuiCompassHub extends GuiCompass {
    public GuiCompassHub(ModCompass mod) {
        super(mod, (GuiScreen) null);
    }

    public void initGui() {
        // this.buttonList.add(new GuiButton(0, this.width / 2 - 60, this.height / 2 - 50, 120, 20, getColoredBool("Enabled: ", this.compass.enabled)));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 60, this.height / 2 - 25, 120, 20, "Edit Layout"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 60, this.height / 2, 120, 20, "Edit Style"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 60, this.height / 2 + 25, 120, 20, "Reset Position"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 60, this.height / 2 + 50, 120, 20, "Done"));
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                // this.compass.enabled = !this.compass.enabled;
                // button.displayString = getColoredBool("Enabled: ", this.compass.enabled);
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiCompassLayout(this.mod, this));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiCompassStyle(this.mod, this));
                break;
            case 3:
                this.compass.offX = 0;
                this.compass.offY = 0;
                break;
            case 4:
                this.mc.displayGuiScreen(null);
                break;
        }
    }
}
