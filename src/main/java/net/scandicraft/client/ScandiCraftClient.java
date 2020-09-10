package net.scandicraft.client;

import com.google.gson.JsonObject;
import net.scandicraft.Environnement;
import net.scandicraft.MinecraftInstance;
import net.scandicraft.ScandiCraftSession;
import net.scandicraft.capacities.listeners.CapacitiesListener;
import net.scandicraft.classes.ClasseManager;
import net.scandicraft.classes.impl.Archer;
import net.scandicraft.config.Config;
import net.scandicraft.discord.DiscordRP;
import net.scandicraft.events.EventManager;
import net.scandicraft.fonts.Fonts;
import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.http.HTTPClient;
import net.scandicraft.http.HTTPEndpoints;
import net.scandicraft.http.HTTPReply;
import net.scandicraft.http.HttpStatus;
import net.scandicraft.http.entity.LoginEntity;
import net.scandicraft.logs.LogManagement;
import net.scandicraft.mods.ModInstances;
import net.scandicraft.mods.listeners.ModEventListeners;
import net.scandicraft.radio.TestRadio;
import net.scandicraft.scheduler.Schedulers;
import net.scandicraft.security.reflexion.ReflexionSecurity;
import net.scandicraft.settings.FileManager;

public class ScandiCraftClient extends MinecraftInstance {

    private static final ScandiCraftClient INSTANCE = new ScandiCraftClient();
    private final DiscordRP discordRP = new DiscordRP();
    private net.scandicraft.events.ccbluex.EventManager eventManager;
    private final Schedulers schedulers = new Schedulers();

    public void init() {
        LogManagement.info("init ScandiCraft Client");

        //Reflexion security
        if (Config.ENV == Environnement.TEST) {
            System.setSecurityManager(new ReflexionSecurity());
        }

        discordRP.start();
        FileManager.init();

        //Register des événements
        EventManager.register(this);
        if (Config.DEMO_PLAYER_CLASSE) {
            EventManager.register(new CapacitiesListener());
        }
        EventManager.register(new ModEventListeners());

        if (Config.ENV == Environnement.DEV) {
            //Get Auth token
            ScandiCraftSession scandiCraftSession = mc.getScandiCraftSession();
            LoginEntity entity = new LoginEntity(scandiCraftSession.getUsername(), scandiCraftSession.getPassword());

            HTTPClient httpClient = new HTTPClient();
            HTTPReply httpReply = httpClient.post(HTTPEndpoints.LOGIN, entity);
            if (httpReply.getStatusCode() == HttpStatus.HTTP_OK) {
                JsonObject response = httpReply.getJsonResponse();
                String api_token = response.get("token").getAsString();
                LogManagement.info("API LOGIN: success - token: " + api_token);
                scandiCraftSession.setToken(api_token);
            } else {
                LogManagement.error("API LOGIN: bad credentials");
            }
        }
    }

    public void start() {
        LogManagement.info("ScandiCraft client start");

        //Load custom fonts
        Fonts.loadFonts();

        //Change MC Fonts
        mc.fontRendererObj = Fonts.fontNormal;

        ModInstances.register(HUDManager.getInstance());

        eventManager = new net.scandicraft.events.ccbluex.EventManager();

        schedulers.registerAll();

        if (Config.DEMO_PLAYER_CLASSE) {
            //Temp set classe
            ClasseManager.getInstance().setPlayerClasse(new Archer());
        }

        TestRadio.test();
    }

    public void shutDown() {
        LogManagement.info("shutDown ScandiCraft Client");

        discordRP.shutdown();

        //TODO remove all refresh_token for user (from server)

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
