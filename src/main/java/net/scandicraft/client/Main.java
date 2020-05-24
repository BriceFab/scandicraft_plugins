package net.scandicraft.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.util.Session;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        OptionParser optionparser = new OptionParser();
        optionparser.allowsUnrecognizedOptions();
        optionparser.accepts("fullscreen");
        optionparser.accepts("checkGlErrors");
        OptionSpec<String> optionServer = optionparser.accepts("server").withRequiredArg();
        OptionSpec<Integer> optionPort = optionparser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565);
        OptionSpec<File> optionGameDir = optionparser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
        OptionSpec<File> optionAssetsDir = optionparser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        OptionSpec<File> optionResourcepackDir = optionparser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        OptionSpec<String> optionUsername = optionparser.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L);
        OptionSpec<String> optionVersion = optionparser.accepts("version").withRequiredArg().required();
        OptionSpec<Integer> optionWidth = optionparser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
        OptionSpec<Integer> optionHeight = optionparser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
        OptionSpec<String> optionAssetIndex = optionparser.accepts("assetIndex").withRequiredArg();
        OptionSpec<String> optionUserType = optionparser.accepts("userType").withRequiredArg().defaultsTo("legacy");

        OptionSpec<String> optionspec5 = optionparser.accepts("proxyHost").withRequiredArg();
        OptionSpec<Integer> optionspec6 = optionparser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
        OptionSpec<String> optionspec7 = optionparser.accepts("proxyUser").withRequiredArg();
        OptionSpec<String> optionspec8 = optionparser.accepts("proxyPass").withRequiredArg();

        OptionSpec<String> nonOptions = optionparser.nonOptions();
        OptionSet optionset = optionparser.parse(args);
        List<String> list = optionset.valuesOf(nonOptions);

        if (!list.isEmpty()) {
            System.out.println("Completely ignored arguments: " + list);
        }

        int i = optionset.valueOf(optionWidth);
        int j = optionset.valueOf(optionHeight);
        boolean isFullscreen = optionset.has("fullscreen");
        boolean checkGlErrors = optionset.has("checkGlErrors");
        String s3 = optionset.valueOf(optionVersion);
        Gson gson = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new Serializer()).create();
        PropertyMap propertymap = gson.fromJson("{}", PropertyMap.class);
        PropertyMap propertymap1 = gson.fromJson("{}", PropertyMap.class);
        File gameDir = optionset.valueOf(optionGameDir);
        File assetsDir = optionset.has(optionAssetsDir) ? optionset.valueOf(optionAssetsDir) : new File(gameDir, "assets/");
        File resourcepacksDir = optionset.has(optionResourcepackDir) ? optionset.valueOf(optionResourcepackDir) : new File(gameDir, "resourcepacks/");
        String username = optionUsername.value(optionset);
        String assetIndex = optionset.has(optionAssetIndex) ? optionAssetIndex.value(optionset) : null;
        String s6 = optionset.valueOf(optionServer);
        Integer integer = optionset.valueOf(optionPort);
        Session session = new Session(optionUsername.value(optionset), username, optionUserType.value(optionset));
        GameConfiguration gameconfiguration = new GameConfiguration(new GameConfiguration.UserInformation(session, propertymap, propertymap1), new GameConfiguration.DisplayInformation(i, j, isFullscreen, checkGlErrors), new GameConfiguration.FolderInformation(gameDir, resourcepacksDir, assetsDir, assetIndex), new GameConfiguration.GameInformation(s3), new GameConfiguration.ServerInformation(s6, integer));
        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread") {
            public void run() {
                Minecraft.stopIntegratedServer();
            }
        });
        Thread.currentThread().setName("Client thread");
        (new Minecraft(gameconfiguration)).run();
    }

}
