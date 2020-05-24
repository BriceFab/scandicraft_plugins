package net.scandicraft;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.net.Proxy;
import java.util.Arrays;
import java.util.List;

public class Config {
    /* Variables */
    public static final String VERSION = "v1.0.0";
    public static final ENVIRONNEMENT ENV = ENVIRONNEMENT.DEV;
    public static final String SERVER_NAME = "ScandiCraft";
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
    public static final String SC_SETTINGS = CONF_FILE_NAME + "\\settings.json";
    public static final String RESOURCE_DOMAIN = SERVER_NAME.toLowerCase() + ":";

    public static class GameConfig {
        public static final String version = SERVER_NAME.toLowerCase();
        public static final String assetsDir = "assets";
        public static final String assetIndex = "1.8";
        public static final Proxy proxy = Proxy.NO_PROXY;
    }

    public enum ENVIRONNEMENT {
        DEV("dev"),
        TEST("test"),
        PROD("prod");

        private final String name;

        ENVIRONNEMENT(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
