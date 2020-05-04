package net.scandicraft.mods.impl;

import net.scandicraft.anti_cheat.auto_click.CheckAutoClick;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ModCPS extends ModDraggable {

    @Override
    public ScreenPosition getDefaultPos() {
        return ScreenPosition.fromRelativePosition(0.00625, 0.011764705882352941);
    }

    private final List<Long> clicks = new ArrayList<>();
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

        boolean pressed = Mouse.isButtonDown(button);
        if (!pressed) { //CPS with Key (touche)
            pressed = mc.gameSettings.keyBindAttack.isKeyDown();
        }

        if (pressed != this.wasPressed) {
            this.wasPressed = pressed;
            if (pressed) {
                this.clicks.add(System.currentTimeMillis());
            }
        }

        int CPS = getCPS();
        CheckAutoClick.checkCPS(CPS);
        font.drawString("CPS: " + CPS, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);

    }

    public int getCPS() {
        this.clicks.removeIf(aLong -> (aLong < System.currentTimeMillis() - 1000L));
        return this.clicks.size();
    }

}
