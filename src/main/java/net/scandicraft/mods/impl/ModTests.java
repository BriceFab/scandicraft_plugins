package net.scandicraft.mods.impl;

import net.minecraft.tileentity.TileEntityChest;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;
import net.scandicraft.render.RenderUtils;

import java.awt.*;

public class ModTests extends ModDraggable {
    @Override
    public int getWidth() {
        return 10;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public void render(ScreenPosition pos) {
        for (Object object : mc.theWorld.loadedTileEntityList) {
            if (object instanceof TileEntityChest) {
                RenderUtils.drawBlockBox(((TileEntityChest) object).getPos(), Color.RED, true);
            }
        }
    }
}
