package net.scandicraft.mods.impl;

import net.minecraft.client.Minecraft;
import net.scandicraft.Config;
import net.scandicraft.anti_cheat.auto_click.CheckAutoClick;
import net.scandicraft.events.EventTarget;
import net.scandicraft.events.impl.KeybordEvent;
import net.scandicraft.events.impl.MouseEvent;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ModCPS extends ModDraggable {

    @Override
    public ScreenPosition getDefaultPos() {
        return ScreenPosition.fromRelativePosition(0.00625, 0.011764705882352941);
    }

    @Override
    public String getName() {
        return "cps";
    }

    //Laisse activer les CPS (mais en arrière plan)
    @Override
    public boolean canUnregister() {
        return false;
    }

    private final List<Long> clicks = new ArrayList<>();

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
        int CPS = getCPS();
        CheckAutoClick.checkCPS(CPS);

        if (isEnabled()) {
            font.drawString("CPS: " + CPS, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
        }
    }

    @EventTarget
    public void onMouse(MouseEvent e) {
        int code = Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode();
        if (Mouse.getEventButtonState() && Mouse.getEventButton() == code + 100) {
            Config.print_debug("mouse attack click");
            this.clicks.add(System.currentTimeMillis());
        }
    }

    @EventTarget
    public void onKeybord(KeybordEvent e) {
        int code = Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode();
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == code) {
            Config.print_debug("keybord attack click");
            this.clicks.add(System.currentTimeMillis());
        }
    }

    public int getCPS() {
        final long time = System.currentTimeMillis();
        this.clicks.removeIf(aLong -> (aLong + 1000L < time));
        return this.clicks.size();
    }

}
