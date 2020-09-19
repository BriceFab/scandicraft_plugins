package net.scandicraft.mods;

import net.scandicraft.Environnement;
import net.scandicraft.config.Config;
import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.mods.impl.*;
import net.scandicraft.mods.impl.compass.ModCompass;
import net.scandicraft.mods.impl.notifications.ModNotifications;
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
    private static ModCapacities modCapacities;
    private static ModNotifications modNotifications;

    public static void register(HUDManager api) {
        modArmorStatus = new ModArmorStatus();
        api.register(modArmorStatus);

        modFPS = new ModFPS();
        api.register(modFPS);

        modCPS = new ModCPS();
        api.register(modCPS);

        modPing = new ModPing();
        modPing.setUsable(false);
        api.register(modPing);

        modKeystrokes = new ModKeystrokes();
        api.register(modKeystrokes);

        modToggleSprintSneak = new ModToggleSprintSneak();
        api.register(modToggleSprintSneak);

        modPotionStatus = new ModPotionStatus();
        api.register(modPotionStatus);

        modCompass = new ModCompass();
        api.register(modCompass);

        ModCopyright modCopyright = new ModCopyright();
        api.register(modCopyright);

        modCapacities = new ModCapacities();
        modCapacities.setEnabled(Config.DEMO_PLAYER_CLASSE);    //si démo, enabled sinon disabled
        api.register(modCapacities);

        modNotifications = new ModNotifications();
        api.register(modNotifications);

        if (Config.ENV == Environnement.DEV) {
            ModTests modTests = new ModTests();
            api.register(modTests);
        }
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

    public static ModCapacities getModCapacities() {
        return modCapacities;
    }

    public static ModNotifications getModNotifications() {
        return modNotifications;
    }

}
