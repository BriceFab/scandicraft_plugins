package net.scandicraft.anti_cheat.process;

import net.scandicraft.config.Config;
import net.scandicraft.anti_cheat.CheatConfig;
import net.scandicraft.anti_cheat.CheatType;
import net.scandicraft.gui.cheat.CheatScreen;
import net.scandicraft.logs.LogManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScanProcess implements Runnable {

    private long startAt = 0L;

    @Override
    public void run() {
        if (isDebug()) {
            startAt = System.currentTimeMillis();
            LogManagement.info("Start scanning process");
        }
        try {
            Process tasklist = (new ProcessBuilder("tasklist", "-fo", "csv", "-nh")).redirectErrorStream(true).start();
            BufferedReader in = new BufferedReader(new InputStreamReader(tasklist.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                String name = line.split("\"")[1];
                if (contains(name.toLowerCase())) {
                    if (isDebug()) {
                        LogManagement.warn("Cheat detect: " + name.toLowerCase());
                    } else {
                        CheatScreen.onDetectCheat(CheatType.ILLEGAL_PROCESS);
                    }
                }
            }
        } catch (IOException e) {
            if (isDebug()) {
                LogManagement.error("cannot check process");
            }
            e.printStackTrace();
        }
        if (isDebug()) {
            LogManagement.info("Stop scanning process in " + (System.currentTimeMillis() - startAt) + " ms");
        }
    }

    private boolean contains(String input) {
        for (String entry : CheatConfig.ILLEGAL_PROCESS) {
            boolean inStar = false;
            int n = 0;
            int lastN = 0;
            for (int i = 0; i <= input.length(); i++) {
                if (entry.length() == n) {
                    if (inStar || input.length() == i)
                        return true;
                    break;
                }
                if (input.length() == i) {
                    if (entry.charAt(n) == '*')
                        return true;
                    break;
                }
                if (entry.charAt(n) == '*') {
                    lastN = ++n;
                    inStar = true;
                    i--;
                } else if (entry.charAt(n) == input.charAt(i)) {
                    n++;
                    inStar = false;
                } else if (!inStar) {
                    if (lastN != 0) {
                        n = lastN;
                    } else {
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean isDebug() {
        return Config.ENV == Config.ENVIRONNEMENT.DEV;
    }
}
