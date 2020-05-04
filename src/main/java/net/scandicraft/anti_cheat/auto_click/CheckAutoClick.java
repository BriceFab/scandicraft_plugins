package net.scandicraft.anti_cheat.auto_click;

import net.minecraft.client.Minecraft;
import net.scandicraft.Config;
import net.scandicraft.anti_cheat.CheatScreen;
import net.scandicraft.anti_cheat.CheatType;
import net.scandicraft.mods.ModInstances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.OptionalDouble;

public class CheckAutoClick {

    private static final ArrayList<Integer> clicks_history = new ArrayList<>();         //historique des CPS
    private static final ArrayList<Boolean> keyDown_history = new ArrayList<>();        //historique des keyDown de l'attaque
    private static final ArrayList<Integer> suspectClick_history = new ArrayList<>();   //historique des clicks suspects
    private static final ArrayList<Long> time_history = new ArrayList<>();              //historique du délais entre chaque clicks

    private static int ACT_CPS = 0;

    public static void checkCPS(int CPS) {
        //Si dépasse max CPS
        if (CPS >= Config.MAX_CPS) {
            CheatScreen.onCheatDetect(CheatType.AUTOCLICK);
        }
    }

    public static void checkAsCPS() {
        //Si action du clic (attack) est lancée mais que n'a 0 clics = auto_click
        ACT_CPS = ModInstances.getModCPS().getCPS();

        addClickHistory(ACT_CPS);
        boolean attackByMouse = Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode() < 0; //souris = plus petit que 0
        boolean isSuspectClick = isSuspectClick();
        boolean isKeyDown = !attackByMouse || Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown() || !isSuspectClick;
        addSuspectHistory(isSuspectClick);
        addKeyDownHistory(isKeyDown);
        boolean hasIllegalDelta = addTimeHistory(System.currentTimeMillis());

        //debug
        Config.print_debug("act CPS: " + ACT_CPS + " count: " + countHistoryZero() + " size:" + clicks_history.size());
        Config.print_debug("LMB " + Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown() + " count: " + countHistoryDown() + " size:" + keyDown_history.size() + " currentHistory: " + (isKeyDown) + " attackByMouse: " + attackByMouse);

        //check if cheating
        if (countHistoryZero() >= Config.MAX_HISTORY / 2) {    //si trop de 0 CPS alors que clické
            Config.print_debug("reason: max 0");
            CheatScreen.onCheatDetect(CheatType.AUTOCLICK);
        }
        if (suspectClick_history.size() >= Config.MAX_HISTORY / 2) {   //si trop de click suspect
            //moyen des clicks suspects
            OptionalDouble average = suspectClick_history.stream().mapToInt(a -> a).average();
            double result_average = average.isPresent() ? average.getAsDouble() : 0;

            //clicks le plus élevé
            int max = Collections.max(suspectClick_history);

            Config.print_debug("suspectClicks result: average: " + result_average + " max: " + max);

            if (max == 0 || result_average >= Config.MAX_SUSPECT_AVERAGE) {
                Config.print_debug("reason: suspectClicks");
                CheatScreen.onCheatDetect(CheatType.AUTOCLICK);
            }
        }
        if (countHistoryDown() >= Config.MAX_HISTORY / 2) { //et que le click provient de la souris
            Config.print_debug("reason: max down");
            CheatScreen.onCheatDetect(CheatType.AUTOCLICK);
        }
        if (hasIllegalDelta) {
            Config.print_debug("reason: illegal time delta");
            CheatScreen.onCheatDetect(CheatType.BUTTERFLY);
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
        if (clicks_history.size() >= Config.MAX_HISTORY) {
            clicks_history.clear();
        }
    }

    private static void addKeyDownHistory(boolean isKeyDown) {
        //false = detect fake click
        keyDown_history.add(isKeyDown);
        if (keyDown_history.size() >= Config.MAX_HISTORY) {
            keyDown_history.clear();
        }
    }

    private static void addSuspectHistory(boolean isSuspectClick) {
        if (isSuspectClick) {
            suspectClick_history.add(ACT_CPS);

            Config.print_debug("click suspect CPS:" + ACT_CPS + " total:" + suspectClick_history.size());
        } else {
            Config.print_debug("not suspect");
        }

        if (suspectClick_history.size() >= Config.MAX_HISTORY) {
            suspectClick_history.clear();
        }
    }

    /**
     * Ajoute à l'historique du temps entre chaque clicks +
     * Quand arrive au max vérifie les différences de temps entre les clicks
     *
     * @param time temps
     * @return boolean (true = cheat | false = no cheat)
     */
    private static boolean addTimeHistory(long time) {
        if (Minecraft.getDebugFPS() >= 20) { //Sinon enregistre ms trop bas
            time_history.add(time);
        }
        if (time_history.size() >= Config.MAX_HISTORY) {

            //Après MAX_HISTORY, on analyse les times historique
            //converti en tableau de différence entre chaque temps
            ArrayList<Long> diffs = new ArrayList<>();

            int countTimeUnderMin = 0;
            for (int i = 1; i < time_history.size(); i++) {
                long last = time_history.get(i - 1);
                long current = time_history.get(i);

                long diff = current - last;

                Config.print_debug(String.format("TIME last: %d current: %d diff: %d", last, current, diff));

                if (diff <= Config.MIN_DIFF_TIME && diff != 1) { //TODO test diff de 1 = bug
                    Config.print_debug("diff under " + Config.MIN_DIFF_TIME + " ms");
                    countTimeUnderMin++;
                }

                diffs.add(diff);
            }

            if (countTimeUnderMin >= Config.MAX_SUM_SUSPECT) {
                Config.print_debug("max 3 moyenne under " + Config.MIN_DIFF_TIME + " ms");
                return true;
            }

            OptionalDouble average = diffs.stream().mapToLong(a -> a).average();
            double result_average = average.isPresent() ? average.getAsDouble() : 0;

            Config.print_debug("moyenne " + result_average);

            if (result_average <= Config.MIN_DIFF_TIME_AVERAGE) {
                Config.print_debug("moyenne under " + Config.MIN_DIFF_TIME_AVERAGE + " ms");
                return true;
            }

            time_history.clear();
        }
        return false;
    }

    private static boolean isSuspectClick() {
        if (keyDown_history.size() > 0 && clicks_history.size() > 1) {
            boolean current_down = Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown();
            int last_cps = clicks_history.get(clicks_history.size() - 2);

            Config.print_debug("current_down: " + current_down + " last_cps: " + last_cps + " current CPS: " + ACT_CPS);

            //Si l'ancien CPS est le même et que l'actuel et que CPS == 0
            if (!current_down && (last_cps != ACT_CPS || ACT_CPS == 0)) { //if (last_down == false && last_cps = current)
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
