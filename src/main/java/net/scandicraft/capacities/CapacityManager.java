package net.scandicraft.capacities;

import net.scandicraft.classes.ClasseManager;
import net.scandicraft.classes.IClasse;
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

    /**
     * Trouve la capacité selon un ID de capacité
     *
     * @param capacityID id de capacité
     * @return capacité correspondante
     */
    public ICapacity getCapacityFromCapacityID(String capacityID) {
        final IClasse playerClasse = ClasseManager.getInstance().getPlayerClasse();
        if (playerClasse == null) {
            return null;
        }

        for (ICapacity capacity : playerClasse.getCapacities()) {
            if (capacity.getUniqueIdentifier().equals(capacityID)) {
                return capacity;
            }
        }
        return null;
    }

    public static CapacityManager getInstance() {
        return INSTANCE;
    }
}