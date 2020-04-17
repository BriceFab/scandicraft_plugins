package net.scandicraft.mods.impl;

import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ModCPS extends ModDraggable {

    private List<Long> clicks = new ArrayList<>();
    private boolean wasPressed;

    @Override
    public int getWidth() {
        return font.getStringWidth("CPS: 00");
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {

        //0 = left; 1 = right
        int button = mc.gameSettings.keyBindAttack.getKeyCode() == -100 ? 0 : 1;

        final boolean pressed = Mouse.isButtonDown(button);
        if (pressed != this.wasPressed) {
            long lastPressed = System.currentTimeMillis();
            this.wasPressed = pressed;
            if (pressed) {
                this.clicks.add(lastPressed);
            }
        }

        font.drawString("CPS: " + getCPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);

    }

    private int getCPS() {
        final long time = System.currentTimeMillis();
        this.clicks.removeIf(aLong -> aLong + 1000 < time);
        return this.clicks.size();
    }

}
