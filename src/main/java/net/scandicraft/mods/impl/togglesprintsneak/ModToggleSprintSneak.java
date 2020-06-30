package net.scandicraft.mods.impl.togglesprintsneak;

import net.scandicraft.Config;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

public class ModToggleSprintSneak extends ModDraggable {
    @Override
    public ScreenPosition getDefaultPos() {
        return ScreenPosition.fromRelativePosition(0.7145833333333333, 0.9294117647058824);
    }

    //settings
    public int keyHoldTicks = 7;

    private String textToRender = "";

    @Override
    public String getName() {
        return "toggle";
    }

    @Override
    public int getWidth() {
        return font.getStringWidth(textToRender);
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {
        textToRender = mc.thePlayer.movementInput.getDisplayText();
        font.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        textToRender = "[" + Config.SERVER_NAME + " Toggle]";
        font.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
}
