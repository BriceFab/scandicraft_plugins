package net.scandicraft.mods;

import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.mods.impl.*;
import net.scandicraft.mods.impl.togglesprintsneak.ModToggleSprintSneak;

public class ModInstances {

    private static ModToggleSprintSneak modToggleSprintSneak;
    private static ModPing modPing;
    private static ModCPS modCPS;
    private static ModKeystrokes modKeystrokes;

    public static void register(HUDManager api) {
        ModArmorStatus modArmorStatus = new ModArmorStatus();
        api.register(modArmorStatus);

        ModFPS modFPS = new ModFPS();
        api.register(modFPS);

        modCPS = new ModCPS();
        api.register(modCPS);

        modPing = new ModPing();
        modPing.setEnabled(false);
        api.register(modPing);

        modKeystrokes = new ModKeystrokes();
        api.register(modKeystrokes);

        modToggleSprintSneak = new ModToggleSprintSneak();
        api.register(modToggleSprintSneak);
    }

    public static ModToggleSprintSneak getModToggleSprintSneak() {
        return modToggleSprintSneak;
    }

    public static ModPing getModPing() {
        return modPing;
    }

    public static ModCPS getModCPS() {
        return modCPS;
    }

    public static ModKeystrokes getModKeystrokes() {
        return modKeystrokes;
    }
}
