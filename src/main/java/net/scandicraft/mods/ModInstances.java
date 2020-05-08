package net.scandicraft.mods;

import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.mods.impl.*;
import net.scandicraft.mods.impl.compass.ModCompass;
import net.scandicraft.mods.impl.togglesprintsneak.ModToggleSprintSneak;

import java.util.ArrayList;

public class ModInstances {

    public static ArrayList<Mod> registered_mods = new ArrayList<>();

    private static ModToggleSprintSneak modToggleSprintSneak;
    private static ModPing modPing;
    private static ModCPS modCPS;
    private static ModKeystrokes modKeystrokes;
    private static ModPotionStatus modPotionStatus;
    private static ModCompass modCompass;
    private static ModFPS modFPS;
    private static ModArmorStatus modArmorStatus;

    public static void register(HUDManager api) {
        modArmorStatus = new ModArmorStatus();
        api.register(modArmorStatus);

        modFPS = new ModFPS();
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

        modPotionStatus = new ModPotionStatus();
        api.register(modPotionStatus);

        modCompass = new ModCompass();
        api.register(modCompass);
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

    public static ModCompass getModCompass() {
        return modCompass;
    }

    public static ModArmorStatus getModArmorStatus() {
        return modArmorStatus;
    }

    public static ModFPS getModFPS() {
        return modFPS;
    }

    public static ModPotionStatus getModPotionStatus() {
        return modPotionStatus;
    }
}
