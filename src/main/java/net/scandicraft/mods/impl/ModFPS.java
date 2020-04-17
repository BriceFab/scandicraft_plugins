package net.scandicraft.mods.impl;

import net.minecraft.client.Minecraft;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

public class ModFPS extends ModDraggable {
    private ScreenPosition pos;

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

    @Override
    public void save(ScreenPosition pos) {
        this.pos = pos;
    }

    @Override
    public ScreenPosition load() {
        return pos;
    }
}
