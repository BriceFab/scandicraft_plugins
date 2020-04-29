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
    public static int MAX_CPS = 20;

    /* Fonctions */
    public static void print_debug(String message) {
        if (DEBUG) {
            LogManager.getLogger().info(String.format("[%s] %s", SERVER_NAME, message));
        }
    }
}
