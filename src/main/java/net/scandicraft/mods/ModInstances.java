package net.scandicraft.mods;

import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.mods.impl.ModArmorStatus;

public class ModInstances {

    private static ModArmorStatus modArmorStatus;

    public static void register(HUDManager api) {
        modArmorStatus = new ModArmorStatus();
        api.register(modArmorStatus);
    }

    public static ModArmorStatus getModArmorStatus() {
        return modArmorStatus;
    }
}
