package net.scandicraft.capacities;

import net.scandicraft.events.EventTarget;
import net.scandicraft.events.impl.KeybordEvent;
import net.scandicraft.logs.LogManagement;
import org.lwjgl.input.Keyboard;

public class Action {

    @EventTarget
    public void onKeybord(KeybordEvent e) {
        LogManagement.info("key press code: " + Keyboard.getEventKey());

        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == 0) {
            LogManagement.info("call launch capacitie 1");
        }
    }

}
