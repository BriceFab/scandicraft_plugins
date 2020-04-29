package net.scandicraft.anti_cheat.auto_click;

import net.scandicraft.Config;
import net.scandicraft.anti_cheat.CheatScreen;
import net.scandicraft.anti_cheat.CheatType;

public class CheckAutoClick {

    public static void checkCPS(int CPS) {
        if (CPS >= Config.MAX_CPS) {
            CheatScreen.onCheatDetect(CheatType.AUTOCLICK);
        }
    }

}
