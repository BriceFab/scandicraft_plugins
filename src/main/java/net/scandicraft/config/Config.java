package net.scandicraft.config;

import net.minecraft.client.Minecraft;
import net.scandicraft.Environnement;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Config {

    /* Variables */
    public static final String VERSION = "v1.0.2";
    public static final Environnement ENV = Environnement.DEV;
    public static final String SERVER_NAME = "ScandiCraft";
    public static final String SC_COPYRIGHT = "ScandiCraft-mc.fr";
    public static final String URL_WEBSITE = "https://scandicraft-mc.fr";
    public static final String TITLE = String.format("%s Reborn - %s (%s)", SERVER_NAME, VERSION, ENV);
    public static final String MOJANG_COPYRIGHT = "Copyright Mojang AB";
    public static final String SCANDICRAFT_COPYRIGHT = "Développé par ScandiCraft";
    public static final String SKINS_PATH = "https://scandicraft-mc.fr/uploads/skins/";
    public static final String CAPES_PATH = "https://scandicraft-mc.fr/uploads/capes/";
    public static final String CONF_FILE_NAME = "sc_config";
    public static final File FONT_DIRS = new File(new File(String.valueOf(Minecraft.getMinecraft().mcDataDir), CONF_FILE_NAME), "fonts");
    public static final List<String> SUPPORTED_LANGS = Arrays.asList("fr_FR", "fr_CA", "en_US", "en_GB");
    public static final String SC_SETTINGS = CONF_FILE_NAME + "\\settings.json";
    public static final String RESOURCE_DOMAIN = SERVER_NAME.toLowerCase() + ":";
    public static final Integer SERVER_PORT = 25565;
    public static final String SERVER_IP = "217.182.205.69";
    public static final String SERVER_IP_AND_PORT = String.format("%s:%d", SERVER_IP, SERVER_PORT);
    public static final String BASE_SC_BUTTONS_RESSOURCES = "scandicraft/buttons/";
    public static final boolean DEMO_PLAYER_CLASSE = false;   //Si capacity and classe demo debug

}
