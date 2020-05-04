package net.minecraft.client.gui;

import net.scandicraft.gui.buttons.DefaultButton;

/**
 * ScandiCraft DefaultButton GUI
 */
public class GuiButton extends DefaultButton {
    public GuiButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }
}
