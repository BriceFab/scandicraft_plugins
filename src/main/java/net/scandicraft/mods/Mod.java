package net.scandicraft.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.scandicraft.client.ScandiCraftClient;
import net.scandicraft.event.EventManager;

public class Mod {

    private boolean isEnabled = true;

    protected final Minecraft mc;
    protected final FontRenderer font;
    protected final ScandiCraftClient client;

    public Mod() {
        this.mc = Minecraft.getMinecraft();
        this.font = mc.fontRendererObj;
        this.client = ScandiCraftClient.getInstance();

        setEnabled(isEnabled);
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;

        if (isEnabled) {
            EventManager.register(this);
        } else {
            EventManager.unregister(this);
        }
    }

    public boolean isEnabled() {
        return isEnabled;
    }


}
