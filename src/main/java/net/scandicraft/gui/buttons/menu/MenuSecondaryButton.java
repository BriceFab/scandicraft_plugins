package net.scandicraft.gui.buttons.menu;

import net.minecraft.client.gui.GuiButton;
import net.scandicraft.fonts.Fonts;

import java.awt.*;

public class MenuSecondaryButton extends GuiButton {
    public MenuSecondaryButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);

        //Fonts
        this.fontSize = Fonts.fontSmall;
        this.hoverFontSize = Fonts.fontNormal;
        this.shiftFontHeight = -6;
        this.shiftHoverFontHeight = -7;

        //Couleurs
        this.textColor = new Color(255, 255, 255, 204);
        this.hoverTextColor = Color.WHITE;
        this.lineColor = new Color(190, 0, 0, 140);
        this.buttonColor = new Color(13, 13, 13, 153);
    }
}
