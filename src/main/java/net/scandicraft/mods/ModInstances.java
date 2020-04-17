package net.scandicraft.mods;

import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.mods.impl.ModArmorStatus;
import net.scandicraft.mods.impl.ModFPS;

public class ModInstances {

    private static ModArmorStatus modArmorStatus;

    public static void register(HUDManager api) {
        modArmorStatus = new ModArmorStatus();
        api.register(modArmorStatus);

        ModFPS modFPS = new ModFPS();
        api.register(modFPS);
    }

    public static ModArmorStatus getModArmorStatus() {
        return modArmorStatus;
    }
}
