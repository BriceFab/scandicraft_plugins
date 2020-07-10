package net.scandicraft.classes.impl;

import net.scandicraft.capacities.ICapacity;
import net.scandicraft.capacities.impl.GuerrierCapacity1;
import net.scandicraft.capacities.impl.GuerrierCapacity2;
import net.scandicraft.capacities.impl.GuerrierCapacity3;
import net.scandicraft.classes.ClasseType;
import net.scandicraft.classes.IClasse;

import java.util.ArrayList;
import java.util.Arrays;

public class Guerrier implements IClasse {
    private final ArrayList<ICapacity> capacities = new ArrayList<>(Arrays.asList(
            new GuerrierCapacity1(),
            new GuerrierCapacity2(),
            new GuerrierCapacity3()
    ));

    @Override
    public ClasseType getClassType() {
        return ClasseType.GUERRIER;
    }

    @Override
    public String getDisplayClasseName() {
        return getClassType().getName();
    }

    @Override
    public ArrayList<ICapacity> getCapacities() {
        return capacities;
    }
}
