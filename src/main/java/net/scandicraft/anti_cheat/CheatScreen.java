package net.scandicraft.anti_cheat;

import net.minecraft.client.Minecraft;
import net.scandicraft.Config;

import javax.swing.*;

public class CheatScreen {

    private static CheatType currentCheat;

    public static void onCheatDetect(CheatType type) {
        Config.print_debug(String.format("cheat detected for %s..", Minecraft.getMinecraft().thePlayer.getName()));
        if (currentCheat == null) {
            JOptionPane.showMessageDialog(null, type.getMessage(), String.format("[%s] AntiCheat", Config.SERVER_NAME), JOptionPane.ERROR_MESSAGE);
            currentCheat = type;
        }
        Config.print_debug("exit for cheating..");
        Minecraft.getMinecraft().shutdown();
    }

}
