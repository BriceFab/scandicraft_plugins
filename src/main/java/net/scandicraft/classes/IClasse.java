package net.scandicraft.classes;

import net.scandicraft.capacities.ICapacity;

import java.util.ArrayList;

public interface IClasse {

    /**
     * @return le type de classe
     */
    ClasseType getClassType();

    /**
     * @return nom de la classe
     */
    String getDisplayClasseName();

    /**
     * Les capacités liées à la classe
     */
    ArrayList<ICapacity> getCapacities();

}
