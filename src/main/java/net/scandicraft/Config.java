package net.scandicraft;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.io.File;
import java.net.Proxy;
import java.util.Arrays;
import java.util.List;

public class Config {
    /* Variables */
    public static final String VERSION = "v1.0.0";
    public static final ENVIRONNEMENT ENV = ENVIRONNEMENT.DEV;
    public static final String SERVER_NAME = "ScandiCraft";
    public static final String SC_COPYRIGHT = "ScandiCraft-mc.fr";
    public static final String URL_WEBSITE = "https://scandicraft-mc.fr";
    public static final String TITLE = String.format("%s Reborn - %s (%s)", SERVER_NAME, VERSION, ENV);
    public static final String COPYRIGHT = "Copyright Mojang AB";
    public static final String SKINS_PATH = "https://scandicraft-mc.fr/uploads/skins/";
    public static final String CAPES_PATH = "https://scandicraft-mc.fr/uploads/capes/";
    public static final String DISCORD_CLIENT_ID = "704772787616874617";  //https://discordapp.com/developers
    public static final String CONF_FILE_NAME = "sc_config";
    public static final File FONT_DIRS = new File(new File(String.valueOf(Minecraft.getMinecraft().mcDataDir), CONF_FILE_NAME), "fonts");
    public static final List<String> SUPPORTED_LANGS = Arrays.asList("fr_FR", "fr_CA", "en_US", "en_GB");
    public static final String SC_SETTINGS = CONF_FILE_NAME + "\\settings.json";
    public static final String RESOURCE_DOMAIN = SERVER_NAME.toLowerCase() + ":";
    public static final String AUTH_KEY = "U;!?M!Zw#8P!B4vU&#j^53;A<sAv\"qQMbuxb\"[,SB*+`*)[Z69a9`]W{Wfa)afFq";
    public static final Integer SERVER_PORT = 25565;
    public static final String SERVER_IP = "217.182.205.69";
    public static final String SERVER_IP_AND_PORT = String.format("%s:%d", SERVER_IP, SERVER_PORT);
    //    public static final Integer HANDSHAKE = -20062020;  //real = 47
    public static final Integer HANDSHAKE = 47;  //real = 47
    public static final String OUTDATED_MESSAGE = EnumChatFormatting.RED + "Télécharge le launcher sur https://scandicraft-mc.fr/";

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
