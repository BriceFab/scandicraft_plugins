package net.scandicraft.anti_cheat.auto_click;

import net.minecraft.client.Minecraft;
import net.scandicraft.anti_cheat.CheatConfig;
import net.scandicraft.anti_cheat.CheatType;
import net.scandicraft.gui.cheat.CheatScreen;
import net.scandicraft.logs.LogManagement;
import net.scandicraft.mods.ModInstances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.OptionalDouble;

public class CheckAutoClick implements Runnable {

    private final ArrayList<Integer> clicks_history = new ArrayList<>();         //historique des CPS
    private final ArrayList<Boolean> keyDown_history = new ArrayList<>();        //historique des keyDown de l'attaque
    private final ArrayList<Integer> suspectClick_history = new ArrayList<>();   //historique des clicks suspects
    private final ArrayList<Long> time_history = new ArrayList<>();              //historique du délais entre chaque clicks
    private final ArrayList<Integer> average_history = new ArrayList<>();        //historique des moyennes de délais entre chaque clicks

    private int CURRENT_CPS = 0;
    private boolean isButterfly = false;

    @Override
    public void run() {
        if (CheatConfig.CHECK_AUTOCLICK) {
            long startAt = System.currentTimeMillis();
            LogManagement.info("Start check autoclick..");

            final int CPS = ModInstances.getModCPS().getCPS();
            final int FPS = Minecraft.getDebugFPS();

            this.CURRENT_CPS = CPS;

            this.minimalCPSCheck();
            if (FPS >= 20) {
                this.allCPSCheck();
            }

            LogManagement.info("Stop check autoclick in " + (System.currentTimeMillis() - startAt) + " ms");
        }
    }

    /**
     * Minimal check seulement quand le client a moins de 20 FPS
     */
    private void minimalCPSCheck() {
        LogManagement.info("autoclick minimal check");

        //Check si le MAX CPS a été atteint
        if (this.CURRENT_CPS >= CheatConfig.MAX_CPS) {
            new CheatScreen(CheatType.AUTOCLICK);
        }
    }

    /**
     * All check quand le client a >= 20 FPS
     * + minimal check
     */
    private void allCPSCheck() {
        LogManagement.info("autoclick all check");

        //TODO test analyse CPS que à partir de 8 CPS && FPS > 20

        addClickHistory(this.CURRENT_CPS);
        boolean attackByMouse = Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode() < 0; //souris = plus petit que 0
        boolean isSuspectClick = isSuspectClick();
        boolean isKeyDown = !attackByMouse || Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown() || !isSuspectClick;
        addSuspectHistory(isSuspectClick);
        addKeyDownHistory(isKeyDown);
        boolean hasIllegalDelta = addTimeHistory(System.currentTimeMillis());

        //debug
        LogManagement.info("act CPS: " + this.CURRENT_CPS + " count: " + countHistoryZero() + " size:" + clicks_history.size());
        LogManagement.info("LMB " + Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown() + " count: " + countHistoryDown() + " size:" + keyDown_history.size() + " currentHistory: " + (isKeyDown) + " attackByMouse: " + attackByMouse);

        //check if cheating
        if (countHistoryZero() >= CheatConfig.MAX_HISTORY / 2) {    //si trop de 0 CPS alors que clické
            LogManagement.info("reason: max 0");
            CheatScreen.onDetectCheat(CheatType.AUTOCLICK);
        }
        if (suspectClick_history.size() >= CheatConfig.MAX_HISTORY / 2) {   //si trop de click suspect
            //moyen des clicks suspects
            OptionalDouble average = suspectClick_history.stream().mapToInt(a -> a).average();
            double result_average = average.isPresent() ? average.getAsDouble() : 0;

            //clicks le plus élevé
            int max = Collections.max(suspectClick_history);

            LogManagement.info("suspectClicks result: average: " + result_average + " max: " + max);

            if (max == 0 || result_average >= CheatConfig.MAX_SUSPECT_AVERAGE) {
                LogManagement.info("reason: suspectClicks");
                CheatScreen.onDetectCheat(CheatType.AUTOCLICK);
            }
        }
        if (countHistoryDown() >= CheatConfig.MAX_DOWN) { //et que le click provient de la souris
            LogManagement.info("reason: max down " + countHistoryDown());
            CheatScreen.onDetectCheat(CheatType.AUTOCLICK);
        }
        if (hasIllegalDelta) {
            LogManagement.info("reason: illegal time delta");
            CheatScreen.onDetectCheat(CheatType.AUTOCLICK);
        }
    }

    /*
    Compte le nombre de 0 CPS
     */
    private int countHistoryZero() {
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
    private int countHistoryDown() {
        int total = 0;
        for (Boolean click : keyDown_history) {
            if (!click) {
                total++;
            }
        }
        return total;
    }

    private void addClickHistory(int actCPS) {
        clicks_history.add(actCPS);
        if (clicks_history.size() >= CheatConfig.MAX_HISTORY) {
            clicks_history.clear();
        }
    }

    private void addKeyDownHistory(boolean isKeyDown) {
        //false = detect fake click
        keyDown_history.add(isKeyDown);
        if (keyDown_history.size() >= CheatConfig.MAX_HISTORY) {
            int count = 0;
            for (boolean down : keyDown_history) {
                if (!down) count++;
            }
            LogManagement.info("Result max_down " + count);
            keyDown_history.clear();
        }
    }

    private void addSuspectHistory(boolean isSuspectClick) {
        if (isSuspectClick) {
            suspectClick_history.add(this.CURRENT_CPS);

            LogManagement.info("click suspect CPS:" + this.CURRENT_CPS + " total:" + suspectClick_history.size());
        } else {
            LogManagement.info("not suspect");
        }

        if (suspectClick_history.size() >= CheatConfig.MAX_HISTORY) {
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
    private boolean addTimeHistory(long time) {
        if (Minecraft.getDebugFPS() >= 20) { //Sinon enregistre ms trop bas
            time_history.add(time);
        }
        if (time_history.size() >= CheatConfig.MAX_HISTORY) {

            //Après MAX_HISTORY, on analyse les times historique
            //converti en tableau de différence entre chaque temps
            ArrayList<Long> diffs = new ArrayList<>();

            ArrayList<Boolean> time_under_min = new ArrayList<>();  //true= butterfly; false= autoclick
            for (int i = 1; i < time_history.size(); i++) {
                long last = time_history.get(i - 1);
                long current = time_history.get(i);

                long diff = current - last;

                LogManagement.info(String.format("TIME last: %d current: %d diff: %d", last, current, diff));

                if (diff != 1) {
                    double min_diff = Math.max(CheatConfig.MIN_DIFF_TIME_BUTTERFLY, CheatConfig.MIN_DIFF_TIME_AUTOCLICK);
                    if (diff <= min_diff) {
                        isButterfly = diff > CheatConfig.MIN_DIFF_TIME_AUTOCLICK;
                        time_under_min.add(isButterfly);
                        LogManagement.info("diff under " + min_diff + " ms [" + (isButterfly ? "butterfly" : "autoclick") + "]");
                    }
                }

                if (diff <= 0 && Minecraft.getDebugFPS() >= 5) {
                    LogManagement.info("reason: diff 0 => double click --- FPS: " + Minecraft.getDebugFPS());
                    CheatScreen.onDetectCheat(CheatType.AUTOCLICK);
                }

                diffs.add(diff);
            }

            time_history.clear();

            //Vérifications
            if (time_under_min.size() >= CheatConfig.MAX_SUM_SUSPECT) {
                LogManagement.info("max 3 moyenne under " + Math.max(CheatConfig.MIN_DIFF_TIME_BUTTERFLY, CheatConfig.MIN_DIFF_TIME_AUTOCLICK) + " ms");

                //true= butterfly; false= autoclick
                int count_buttefly = 0;
                for (Boolean isButterFly : time_under_min) {
                    if (isButterFly) count_buttefly++;
                }
                isButterfly = count_buttefly >= CheatConfig.MAX_SUM_SUSPECT;

                return true;
            }

            OptionalDouble average = diffs.stream().mapToLong(a -> a).average();
            double result_average = average.isPresent() ? average.getAsDouble() : 0;

            LogManagement.info("moyenne " + result_average);
            addAverageHistory(result_average);

            //Si en dessous de la moyenne autoclick et buttefly
            double min_average = Math.max(CheatConfig.MIN_DIFF_TIME_AVERAGE_BUTTERFLY, CheatConfig.MIN_DIFF_TIME_AVERAGE_AUTOCLICK);
            if (result_average <= min_average) {
                isButterfly = result_average > CheatConfig.MIN_DIFF_TIME_AVERAGE_AUTOCLICK;
                LogManagement.info("moyenne under " + min_average + " ms [" + (isButterfly ? "butterfly" : "autoclick") + "]");

                return true;
            }
        }
        return false;
    }

    private void addAverageHistory(double average) {
        average_history.add(new Double(average).intValue());

        if (average_history.size() >= CheatConfig.MAX_HISTORY_FREQUENCY_AVERAGE) {

            average_history.forEach(avg -> {
                int frequency = Collections.frequency(average_history, avg);
                LogManagement.info("average " + avg + " freq: " + frequency);

                if (frequency >= CheatConfig.MAX_FREQUENCY_AVERAGE) {
                    LogManagement.info("reason: max " + CheatConfig.MAX_FREQUENCY_AVERAGE + " frequency history");
                    CheatScreen.onDetectCheat(CheatType.AUTOCLICK);
                }
            });

            average_history.clear();
        }
    }

    private boolean isSuspectClick() {
        if (keyDown_history.size() > 0 && clicks_history.size() > 1) {
            boolean current_down = Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown();
            int last_cps = clicks_history.get(clicks_history.size() - 2);

            LogManagement.info("current_down: " + current_down + " last_cps: " + last_cps + " current CPS: " + this.CURRENT_CPS);

            //Si l'ancien CPS est le même et que l'actuel et que CPS == 0
            if (!current_down && (last_cps != this.CURRENT_CPS || this.CURRENT_CPS == 0)) { //if (last_down == false && last_cps = current)
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
