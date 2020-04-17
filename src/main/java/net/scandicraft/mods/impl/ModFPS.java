package net.scandicraft.mods.impl;

import net.minecraft.client.Minecraft;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

public class ModFPS extends ModDraggable {

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {
        font.drawString("FPS: " + Minecraft.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }

}
