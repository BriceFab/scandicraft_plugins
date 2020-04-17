package net.scandicraft.client;

import net.minecraft.client.Minecraft;
import net.scandicraft.Config;
import net.scandicraft.event.EventManager;
import net.scandicraft.event.EventTarget;
import net.scandicraft.event.impl.ClientTickEvent;
import net.scandicraft.gui.hud.HUDManager;
import net.scandicraft.mods.ModInstances;
import net.scandicraft.settings.FileManager;

public class ScandiCraftClient {

    private static final ScandiCraftClient INSTANCE = new ScandiCraftClient();

    public static ScandiCraftClient getInstance() {
        return INSTANCE;
    }

    private HUDManager hudManager;

    public void init() {
        Config.print_debug("init ScandiCraft Client");
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
        EventManager.unregister(this);
    }

    @EventTarget
    public void onTick(ClientTickEvent e) {
        if (Minecraft.getMinecraft().scandiCraftSettings.OPEN_HUD_MANAGER.isPressed()) {
            hudManager.openConfigScreen();
        }
    }

}
