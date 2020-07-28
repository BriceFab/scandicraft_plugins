package net.scandicraft.mods.listeners;

import net.scandicraft.events.EventTarget;
import net.scandicraft.events.impl.ConnectServerEvent;
import net.scandicraft.events.impl.DisconnectServerEvent;
import net.scandicraft.logs.LogManagement;
import net.scandicraft.mods.ModInstances;

public class ModEventListeners {

    @EventTarget
    public void onDisconnectServer(DisconnectServerEvent e) {
        LogManagement.info("Event: on disconnect server");

//        ModInstances.getModCapacities().setEnabled(false);
//        ClasseManager.getInstance().removePlayerClasse();
        ModInstances.getModPing().setUsable(false);
    }

    @EventTarget
    public void onConnectServer(ConnectServerEvent e) {
        LogManagement.info("Event: on connect server");

        //TODO check if server is faction
        ModInstances.getModCapacities().setEnabled(true);
        ModInstances.getModPing().setUsable(true);
    }

}
