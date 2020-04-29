package net.scandicraft.anti_cheat.auto_click;

import net.minecraft.client.Minecraft;
import net.scandicraft.Config;
import net.scandicraft.anti_cheat.CheatScreen;
import net.scandicraft.anti_cheat.CheatType;
import net.scandicraft.mods.ModInstances;

import java.util.ArrayList;

public class CheckAutoClick {

    private static final ArrayList<Integer> clicks_history = new ArrayList<>();
    private static final ArrayList<Boolean> keyDown_history = new ArrayList<>();
    private static final int MAX_HISTORY = 10;

    public static void checkCPS(int CPS) {
        //Si dépasse max CPS
        if (CPS >= Config.MAX_CPS) {
            CheatScreen.onCheatDetect(CheatType.AUTOCLICK);
        }
    }

    public static void checkAsCPS() {
        //Si action du clic (attack) est lancée mais que n'a 0 clics = auto_click
        final int actCPS = ModInstances.getModCPS().getCPS();

        addClickHistory(actCPS);
        boolean attackByMouse = Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode() < 0; //souris = plus petit que 0
        addKeyDownHistory(!attackByMouse || Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown());

        Config.print_debug("act CPS: " + actCPS + " count: " + countHistoryZero() + " size:" + clicks_history.size());
        Config.print_debug("LMB " + Minecraft.getMinecraft().gameSettings.keyBindAttack.isPressed() + " count: " + countHistoryDown() + " size:" + keyDown_history.size() + " attackByMouse: " + attackByMouse);

        if (countHistoryZero() >= MAX_HISTORY / 2) {
            CheatScreen.onCheatDetect(CheatType.AUTOCLICK);
        } else if (countHistoryDown() >= MAX_HISTORY / 2) { //et que le click provient de la souris
            CheatScreen.onCheatDetect(CheatType.AUTOCLICK);
        }
    }

    /*
    Compte le nombre de 0 CPS
     */
    private static int countHistoryZero() {
        int total = 0;
        for (int click : clicks_history) {
            if (click == 0) {
                total++;
            }
        }
        return total;
    }

    /*
    Compte le nombre de false KeyDown (attack)
     */
    private static int countHistoryDown() {
        int total = 0;
        for (Boolean click : keyDown_history) {
            if (!click) {
                total++;
            }
        }
        return total;
    }

    private static void addClickHistory(int actCPS) {
        clicks_history.add(actCPS);
        if (clicks_history.size() >= MAX_HISTORY) {
            clicks_history.clear();
        }
    }

    private static void addKeyDownHistory(boolean isKeyDown) {
        keyDown_history.add(isKeyDown);
        if (keyDown_history.size() >= MAX_HISTORY) {
            keyDown_history.clear();
        }
    }

}
