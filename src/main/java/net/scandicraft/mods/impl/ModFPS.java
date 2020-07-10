package net.scandicraft.mods.impl;

import net.minecraft.client.Minecraft;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

public class ModFPS extends ModDraggable {
    public static ScreenPosition FPS_POSITION = ScreenPosition.fromRelativePosition(0.8416666666666667, 0.0392156862745098);

    @Override
    public String getName() {
        return "fps";
    }

    @Override
    public ScreenPosition getDefaultPos() {
        return FPS_POSITION;
    }

    @Override
    public int getWidth() {
        return font.getStringWidth("FPS: 000");
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
