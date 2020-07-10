package net.scandicraft.classes;

import net.scandicraft.capacities.CapacityManager;

/**
 * Stocke la classe actuelle du joueur
 */
public class ClasseManager {

    private static final ClasseManager INSTANCE = new ClasseManager();
    private IClasse playerClasse = null;

    private ClasseManager() {
    }

    public void setPlayerClasse(IClasse playerClasse) {
        //Sauvegarde la classe du joueur
        this.playerClasse = playerClasse;

        //Sélectionne la 1ère capacité du joueur
        CapacityManager.getInstance().setPlayerCurrentCapacity(playerClasse.getCapacities().get(0));
    }

    public IClasse getPlayerClasse() {
        return playerClasse;
    }

    public static ClasseManager getInstance() {
        return INSTANCE;
    }
}
