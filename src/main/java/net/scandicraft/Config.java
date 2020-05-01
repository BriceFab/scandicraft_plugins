package net.scandicraft;

import org.apache.logging.log4j.LogManager;

public class Config {
    /* Variables */
    public static String VERSION = "v1.0.0";
    public static String SERVER_NAME = "ScandiCraft";
    public static String URL_WEBSITE = "https://scandicraft-mc.fr";
    public static String TITLE = String.format("%s Reborn - %s", SERVER_NAME, VERSION);
    private static boolean DEBUG = true;
    public static String COPYRIGHT = "Copyright Mojang AB";
    public static String SKINS_PATH = "https://scandicraft-mc.fr/upload/skins/";    //.png only ; TODO user_id + upload
    public static String CAPES_PATH = "https://scandicraft-mc.fr/upload/capes/";    //.png only ; TODO user_id + upload
    public static String DISCORD_CLIENT_ID = "704772787616874617";  //https://discordapp.com/developers

    /* Anti-Cheat */
    //CPS
    public static int MAX_CPS = 20;
    public static int MAX_SUSPECT_AVERAGE = 8;
    public static final int MAX_HISTORY = 14;
    public static final double MIN_DIFF_TIME = 30;            //différence minimale entre 2 clicks = 0.03 secondes
    public static final double MIN_DIFF_TIME_AVERAGE = 50;    //différence minimale de la sommes des temps = 0.05 seconds en moyenne

    /* Fonctions */
    public static void print_debug(String message) {
        if (DEBUG) {
            LogManager.getLogger().info(String.format("[%s] %s", SERVER_NAME, message));
        }
    }
}
