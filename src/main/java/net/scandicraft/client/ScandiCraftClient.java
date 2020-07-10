package net.scandicraft.client;

import net.scandicraft.capacities.listeners.CapacitiesListener;
import net.scandicraft.classes.ClasseManager;
import net.scandicraft.classes.ClasseType;
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

        //Register des événements
        EventManager.register(this);
        EventManager.register(new CapacitiesListener());
    }

    public void start() {
        LogManagement.info("ScandiCraft client start");

        ModInstances.register(HUDManager.getInstance());

        eventManager = new net.scandicraft.events.ccbluex.EventManager();
        Fonts.loadFonts();

        schedulers.registerAll();

        //On enregistre la classe du joueur
        final int playerClasse = 0; //TODO from API
        ClasseType classeType = ClasseType.getClasseTypeFromId(playerClasse);
        if (classeType != null) {
            ClasseManager.getInstance().setPlayerClasse(classeType.getIClasse());
        }
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
