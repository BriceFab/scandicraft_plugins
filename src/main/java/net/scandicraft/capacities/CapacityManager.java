package net.scandicraft.capacities;

import net.scandicraft.logs.LogManagement;

/**
 * Gère les capacités des joueurs
 */
public class CapacityManager {

    private static final CapacityManager INSTANCE = new CapacityManager();
    private ICapacity playerCurrentCapacity = null;

    private CapacityManager() {
    }

    /**
     * Le joueur a lancé l'action de lancer sa capacité
     */
    public void launchCapacity() {
        LogManagement.warn("lance la capacité");
    }

    public void setPlayerCurrentCapacity(ICapacity playerCurrentCapacity) {
        this.playerCurrentCapacity = playerCurrentCapacity;
    }

    public ICapacity getPlayerCurrentCapacity() {
        return playerCurrentCapacity;
    }

    public static CapacityManager getInstance() {
        return INSTANCE;
    }
}