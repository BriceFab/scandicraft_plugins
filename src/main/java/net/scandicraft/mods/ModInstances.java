package net.scandicraft.mods;

import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.mods.impl.*;
import net.scandicraft.mods.impl.togglesprintsneak.ModToggleSprintSneak;

public class ModInstances {

    private static ModToggleSprintSneak modToggleSprintSneak;

    public static void register(HUDManager api) {
        ModArmorStatus modArmorStatus = new ModArmorStatus();
        api.register(modArmorStatus);

        ModFPS modFPS = new ModFPS();
        api.register(modFPS);

        ModCPS modCPS = new ModCPS();
        api.register(modCPS);

        ModPing modPing = new ModPing();
        api.register(modPing);

        ModKeystrokes modKeystrokes = new ModKeystrokes();
        api.register(modKeystrokes);

        modToggleSprintSneak = new ModToggleSprintSneak();
        api.register(modToggleSprintSneak);
    }

    public static ModToggleSprintSneak getModToggleSprintSneak() {
        return modToggleSprintSneak;
    }
}
