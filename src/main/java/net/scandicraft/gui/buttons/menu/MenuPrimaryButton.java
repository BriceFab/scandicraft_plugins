package net.scandicraft.gui.buttons.menu;

import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class MenuPrimaryButton extends GuiButton {
    public MenuPrimaryButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);

        //Couleurs
        this.lineColor = new Color(226, 0, 0, 255);
        this.buttonColor = new Color(13, 13, 13, 191);
    }
}
