package net.scandicraft.anti_cheat.process;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ScanProcess implements Runnable {
    private final List<String> CHEAT_PROCESS = Arrays.asList(
            "zenkey.exe*",
            "wonderkeys.exe*",
            "autohotkey.exe*",
            "auto*clic*.exe*",
            "auto*clique*.exe*",
            "auto*klic*.exe*",
            "cheat*engine*.exe*",
            "klick.exe*",
            "click.exe*",
            "clic.exe*",
            "clicker.exe*",
            "cliqueur.exe*",
            "super*rapid*fire*.exe*",
            "speed.exe*",
            "speedhack.exe*",
            "speeder.exe*",
            "speedcheat.exe*",
            "cheatspeed.exe*"
    );

    private Minecraft mc;

    private boolean cheating = false;

//    public void postInit() {
//        (new Thread("Dcac") {
//            public void run() {
//                while (true) {
//                    if (Util.getOSType() == Util.EnumOS.WINDOWS)
//                        try {
//                            Process tasklist = (new ProcessBuilder("tasklist", "-fo", "csv", "-nh")).redirectErrorStream(true).start();
//                            BufferedReader in = new BufferedReader(new InputStreamReader(tasklist.getInputStream()));
//                            String line;
//                            while ((line = in.readLine()) != null) {
//                                String name = line.split("\"")[1];
//                                if (contains(CHEAT_PROCESS, name.toLowerCase())) {
//                                    cheating = true;
//                                }
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    try {
//                        sleep(5000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }

    private boolean contains(List<String> list, String input) {
        for (String entry : list) {
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

    public void handleCheat() {
//        String[] erreur = {"Ok !"};
//        JOptionPane ferreur = new JOptionPane();
//        int rang = JOptionPane.showOptionDialog(null, "Les cheats sont interdit sur SparksMC !", "SparksMC - Anti Cheat!", 0, 0, null, (Object[]) erreur, erreur[0]);
        System.exit(0);
    }

    public boolean isCheating() {
        return this.cheating;
    }

    @Override
    public void run() {
        if (Util.getOSType() == Util.EnumOS.WINDOWS) {
            try {
                Process tasklist = (new ProcessBuilder("tasklist", "-fo", "csv", "-nh")).redirectErrorStream(true).start();
                BufferedReader in = new BufferedReader(new InputStreamReader(tasklist.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    String name = line.split("\"")[1];
                    if (contains(CHEAT_PROCESS, name.toLowerCase())) {
                        cheating = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
