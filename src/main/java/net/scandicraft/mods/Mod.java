package net.scandicraft.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.scandicraft.client.ScandiCraftClient;
import net.scandicraft.events.EventManager;

public class Mod {

    private boolean isEnabled = true;       //est actif
    private boolean isUsable = true;        //peut-être utilisé

    protected final Minecraft mc;
    protected final FontRenderer font;
    protected final ScandiCraftClient client;

    public Mod() {
        this.mc = Minecraft.getMinecraft();
        this.font = mc.fontRendererObj;
        this.client = ScandiCraftClient.getInstance();

        setEnabled(true);
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;

//        LogManagement.info("isEnabled: " + isEnabled + " isUsable: " + isUsable() + " enable:" + enabled);

        if (isEnabled && isUsable() && !ModInstances.registered_mods.contains(this)) {
//            LogManagement.info("register " + this);

            ModInstances.registered_mods.add(this);
            EventManager.register(this);
        } else {
            if (canUnregister()) {
//                LogManagement.info("unregister " + this);

                EventManager.unregister(this);
                ModInstances.registered_mods.remove(this);
            }
        }
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    //Peut-être désenregistré
    public boolean canUnregister() {
        return true;
    }

    //Peut-être utilisé
    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;

        setEnabled(isEnabled());
    }

    public String getName() {
        return "default_sc";
    }

}
