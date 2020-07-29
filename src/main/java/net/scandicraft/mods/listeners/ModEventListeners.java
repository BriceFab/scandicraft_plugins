package net.scandicraft.mods.listeners;

import net.scandicraft.classes.ClasseManager;
import net.scandicraft.config.Config;
import net.scandicraft.events.EventTarget;
import net.scandicraft.events.impl.ConnectServerEvent;
import net.scandicraft.events.impl.DisconnectServerEvent;
import net.scandicraft.mods.ModInstances;

public class ModEventListeners {

    @EventTarget
    public void onDisconnectServer(DisconnectServerEvent e) {
        //capacities
        if (!Config.DEMO_PLAYER_CLASSE) {
            ModInstances.getModCapacities().setEnabled(false);
            ClasseManager.getInstance().removePlayerClasse();
        }

        //ping
        ModInstances.getModPing().setUsable(false);
    }

    @EventTarget
    public void onConnectServer(ConnectServerEvent e) {
        //capacities
        //TODO check if server is faction
        ModInstances.getModCapacities().setEnabled(true);

        //ping
        ModInstances.getModPing().setUsable(true);
    }

}
