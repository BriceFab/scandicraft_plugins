package net.scandicraft.mods.impl;

import net.scandicraft.Config;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

public class ModCopyright extends ModDraggable {
    @Override
    public ScreenPosition getDefaultPos() {
        return ScreenPosition.fromRelativePosition(0.0125, 0.027450980392156862);
    }

    @Override
    public String getName() {
        return "sc_copyright";
    }

    @Override
    public int getWidth() {
        return font.getStringWidth(Config.SC_COPYRIGHT);
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {
        font.drawString(Config.SC_COPYRIGHT, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
}
