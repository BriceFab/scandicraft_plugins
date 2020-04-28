package net.scandicraft.client;

import net.minecraft.client.Minecraft;
import net.scandicraft.Config;
import net.scandicraft.discord.DiscordRP;
import net.scandicraft.event.EventManager;
import net.scandicraft.event.EventTarget;
import net.scandicraft.event.impl.ClientTickEvent;
import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.mods.ModInstances;
import net.scandicraft.settings.FileManager;

public class ScandiCraftClient {

    private static final ScandiCraftClient INSTANCE = new ScandiCraftClient();
    private DiscordRP discordRP = new DiscordRP();

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
}
