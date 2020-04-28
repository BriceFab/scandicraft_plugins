package net.scandicraft.anti_cheat;

import net.minecraft.client.Minecraft;
import net.scandicraft.Config;

import javax.swing.*;

public class CheatScreen {

    public static void onCheatDetect(CheatType type) {
        JOptionPane.showMessageDialog(null, type.getMessage(), String.format("[%s] AntiCheat", Config.SERVER_NAME), JOptionPane.ERROR_MESSAGE);
        Config.print_debug("exit for cheating..");
        Minecraft.getMinecraft().shutdown();
    }

}
