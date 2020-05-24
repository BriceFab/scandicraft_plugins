package net.scandicraft.client;

import net.scandicraft.discord.DiscordRP;
import net.scandicraft.events.EventManager;
import net.scandicraft.fonts.Fonts;
import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.logs.LogManagement;
import net.scandicraft.mods.ModInstances;
import net.scandicraft.scheduler.Schedulers;
import net.scandicraft.settings.FileManager;

public class ScandiCraftClient {

    private static final ScandiCraftClient INSTANCE = new ScandiCraftClient();
    private final DiscordRP discordRP = new DiscordRP();
    private net.scandicraft.events.ccbluex.EventManager eventManager;
    private final Schedulers schedulers = new Schedulers();

    public void init() {
        LogManagement.info("init ScandiCraft Client");
        discordRP.start();
        FileManager.init();
        EventManager.register(this);
    }

    public void start() {
        LogManagement.info("ScandiCraft client start");
        ModInstances.register(HUDManager.getInstance());

        eventManager = new net.scandicraft.events.ccbluex.EventManager();
        Fonts.loadFonts();

        schedulers.registerAll();
    }

    public void shutDown() {
        LogManagement.info("shutDown ScandiCraft Client");

        discordRP.shutdown();
        //TODO remove all refresh_token for user

        EventManager.unregister(this);
    }

    public static ScandiCraftClient getInstance() {
        return INSTANCE;
    }

    public DiscordRP getDiscordRP() {
        return discordRP;
    }

    public net.scandicraft.events.ccbluex.EventManager getEventManager() {
        return eventManager;
    }
}
