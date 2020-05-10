package net.scandicraft;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Config {
    /* Variables */
    public static final String VERSION = "v1.0.0";
    public static final String SERVER_NAME = "ScandiCraft";
    public static final ENVIRONNEMENT ENV = ENVIRONNEMENT.DEV;
    public static final String URL_WEBSITE = "https://scandicraft-mc.fr";
    public static final String TITLE = String.format("%s Reborn - %s (%s)", SERVER_NAME, VERSION, ENV);
    private static final boolean DEBUG = true;
    public static final String COPYRIGHT = "Copyright Mojang AB";
    public static final String SKINS_PATH = "https://scandicraft-mc.fr/upload/skins/";    //.png only ; TODO user_id + upload
    public static final String CAPES_PATH = "https://scandicraft-mc.fr/upload/capes/";    //.png only ; TODO user_id + upload
    public static final String DISCORD_CLIENT_ID = "704772787616874617";  //https://discordapp.com/developers
    public static final String CONF_FILE_NAME = "sc_conf";
    public static final File FONT_DIRS = new File(new File(String.valueOf(Minecraft.getMinecraft().mcDataDir), CONF_FILE_NAME), "fonts");
    public static final List<String> SUPPORTED_LANGS = Arrays.asList("fr_FR", "fr_CA", "en_US", "en_GB");
    public static final String SC_SETTINGS = "settings.json";

    /* Anti-Cheat */
    //CPS
    public static final int MAX_CPS = 20;
    public static final int MAX_SUSPECT_AVERAGE = 15;
    public static final int MAX_HISTORY = 14;
    public static final double MIN_DIFF_TIME_BUTTERFLY = 45;            //si plus de 3 clicks sont plus petit que 45 (=butterfly)
    public static final double MIN_DIFF_TIME_AUTOCLICK = 40;            //si plus de 3 clicks sont plus petit que 40 (=autoclick)
    public static final int MAX_SUM_SUSPECT = 3;                        //si plus de x clicks dans la moyenne max
    public static final double MIN_DIFF_TIME_AVERAGE_BUTTERFLY = 65;    //différence minimale de la moyenne des temps = 0.06 seconds en moyenne (=butterfly)
    public static final double MIN_DIFF_TIME_AVERAGE_AUTOCLICK = 55;    //différence minimale de la moyenne des temps = 0.06 seconds en moyenne (=autoclick)
    public static final int MAX_HISTORY_FREQUENCY_AVERAGE = 7;          //Historique max de la fréquence (1 moyenne = chaque 14 clicks [MAX_HISTORY])
    public static final int MAX_FREQUENCY_AVERAGE = 6;                  //Maximum de 5 moyenne les mêmes    //TODO check value
    public static final int MAX_DOWN = 9;                               //Maxium de click LMB false     //TODO check value

    /* Fonctions */
    public static void print_debug(String message) {
        if (DEBUG) {
            LogManager.getLogger().info(String.format("[%s] %s", SERVER_NAME, message));
        }
    }

    public enum ENVIRONNEMENT {
        DEV, TEST, PROD
    }
}
