package net.scandicraft.capacities.listeners;

import net.minecraft.client.Minecraft;
import net.scandicraft.capacities.CapacityManager;
import net.scandicraft.capacities.impl.ArcherCapacity1;
import net.scandicraft.config.KeyCodes;
import net.scandicraft.events.EventTarget;
import net.scandicraft.events.impl.KeybordEvent;
import net.scandicraft.logs.LogManagement;
import net.scandicraft.mods.ModInstances;
import net.scandicraft.packets.client.play.CPacketChangeCapacity;
import org.lwjgl.input.Keyboard;

public class CapacitiesListener {

    @EventTarget
    public void onKeybord(KeybordEvent e) {
        if (Keyboard.getEventKeyState() && ModInstances.getModCapacities().isEnabled()) {
            switch (Keyboard.getEventKey()) {
                case KeyCodes.KEY_R: {  //R
                    //Lance la capacité
                    CapacityManager.getInstance().launchCapacity();
                    break;
                }
                case KeyCodes.KEY_Z: {  //Z
                    //Change la capacité sélectionnée du joueur
                    this.changeNextCapacity();
                    break;
                }
            }
        }
    }

    /**
     * On envoie un packet au serveur pour sélectionner la prochaine capacité de la liste
     */
    private void changeNextCapacity() {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new CPacketChangeCapacity());
    }

    /**
     * On envoie un packet au serveur pour sélectionne une capacité de notre classe en particulier
     */
    private void changeSpecificCapacity() {
        LogManagement.warn("change la capacité sélectionnée");
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new CPacketChangeCapacity(new ArcherCapacity1()));
    }

}
