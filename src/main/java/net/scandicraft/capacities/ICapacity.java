package net.scandicraft.capacities;

import net.minecraft.util.ResourceLocation;
import net.scandicraft.capacities.exception.CapacityException;

public interface ICapacity {

    /**
     * Nom de la capacité
     *
     * @return nom
     */
    String getName();

    /**
     * Identifiant unique d'une capacités
     * @return id
     */
    String getUniqueIdentifier();

    /**
     * Temps entre chaque lancement de la capacité (SECONDES)
     *
     * @return temps
     */
    int getCooldownTime();

    /**
     * Utilise la capacité
     */
    void onUse() throws CapacityException;

    /**
     * @return l'icône de la capacité
     */
    ResourceLocation getCapacityIcon();
}
