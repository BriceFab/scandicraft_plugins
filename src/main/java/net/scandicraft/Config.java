package net.scandicraft;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;

import java.io.File;

public class Config {
    /* Variables */
    public static String VERSION = "v1.0.0";
    public static String SERVER_NAME = "ScandiCraft";
    public static String URL_WEBSITE = "https://scandicraft-mc.fr";
    public static String TITLE = String.format("%s Reborn - %s", SERVER_NAME, VERSION);
    private static final boolean DEBUG = true;
    public static String COPYRIGHT = "Copyright Mojang AB";
    public static String SKINS_PATH = "https://scandicraft-mc.fr/upload/skins/";    //.png only ; TODO user_id + upload
    public static String CAPES_PATH = "https://scandicraft-mc.fr/upload/capes/";    //.png only ; TODO user_id + upload
    public static String DISCORD_CLIENT_ID = "704772787616874617";  //https://discordapp.com/developers
    public static final File FONT_DIRS = new File(new File(String.valueOf(Minecraft.getMinecraft().mcDataDir), "scandicraft"), "fonts");

    /* Anti-Cheat */
    //CPS
    public static final int MAX_CPS = 20;
    public static final int MAX_SUSPECT_AVERAGE = 15;
    public static final int MAX_HISTORY = 14;
    public static final double MIN_DIFF_TIME_BUTTERFLY = 45;            //si plus de 3 clicks sont plus petit que 55 (=butterfly)
    public static final double MIN_DIFF_TIME_AUTOCLICK = 40;            //si plus de 3 clicks sont plus petit que 40 (=autoclick)
    public static final int MAX_SUM_SUSPECT = 3;              //si plus de x clicks dans la moyenne max
    public static final double MIN_DIFF_TIME_AVERAGE_BUTTERFLY = 65;    //différence minimale de la moyenne des temps = 0.06 seconds en moyenne (=butterfly)
    public static final double MIN_DIFF_TIME_AVERAGE_AUTOCLICK = 55;    //différence minimale de la moyenne des temps = 0.06 seconds en moyenne (=autoclick)
    public static final int MAX_HISTORY_FREQUENCY_AVERAGE = 5;          //Historique max de la fréquence (1 moyenne = chaque 14 clicks [MAX_HISTORY])
    public static final int MAX_FREQUENCY_AVERAGE = 4;                  //Maximum de 5 moyenne les mêmes

    /* Fonctions */
    public static void print_debug(String message) {
        if (DEBUG) {
            LogManager.getLogger().info(String.format("[%s] %s", SERVER_NAME, message));
        }
    }
}
