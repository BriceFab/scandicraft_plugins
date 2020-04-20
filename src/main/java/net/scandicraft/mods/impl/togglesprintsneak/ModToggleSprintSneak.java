package net.scandicraft.mods.impl.togglesprintsneak;

import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

public class ModToggleSprintSneak extends ModDraggable {

    //settings
    public boolean flyBoost = true;
    public float flyBoostFactor = 4;
    public int keyHoldTicks = 7;

    private String textToRender = "";

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
        textToRender = "[Dummy Text]";
        font.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
}
