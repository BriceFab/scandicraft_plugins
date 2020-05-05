package net.scandicraft.client;

import net.minecraft.client.Minecraft;
import net.scandicraft.Config;
import net.scandicraft.discord.DiscordRP;
import net.scandicraft.events.EventManager;
import net.scandicraft.events.EventTarget;
import net.scandicraft.events.impl.ClientTickEvent;
import net.scandicraft.fonts.Fonts;
import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.mods.ModInstances;
import net.scandicraft.settings.FileManager;

public class ScandiCraftClient {

    private static final ScandiCraftClient INSTANCE = new ScandiCraftClient();
    private final DiscordRP discordRP = new DiscordRP();
    private net.scandicraft.events.ccbluex.EventManager eventManager;

    private HUDManager hudManager;

    public void init() {
        Config.print_debug("init ScandiCraft Client");
        discordRP.start();
        FileManager.init();
        EventManager.register(this);
    }

    public void start() {
        Config.print_debug("ScandiCraft client start");
        hudManager = HUDManager.getInstance();
        ModInstances.register(hudManager);

        eventManager = new net.scandicraft.events.ccbluex.EventManager();
        Fonts.loadFonts();
    }

    public void shutDown() {
        Config.print_debug("shutDown ScandiCraft Client");

        discordRP.shutdown();
        //TODO remove all refresh_token for user

        EventManager.unregister(this);
    }

    @EventTarget
    public void onTick(ClientTickEvent e) {
        if (Minecraft.getMinecraft().scandiCraftSettings.OPEN_HUD_MANAGER.isPressed()) {
            hudManager.openConfigScreen();
        }
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
