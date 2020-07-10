package net.scandicraft.classes.impl;

import net.scandicraft.capacities.ICapacity;
import net.scandicraft.capacities.impl.ArcherCapacity1;
import net.scandicraft.capacities.impl.ArcherCapacity2;
import net.scandicraft.capacities.impl.ArcherCapacity3;
import net.scandicraft.classes.ClasseType;
import net.scandicraft.classes.IClasse;

import java.util.ArrayList;
import java.util.Arrays;

public class Archer implements IClasse {
    private final ArrayList<ICapacity> capacities = new ArrayList<>(Arrays.asList(
            new ArcherCapacity1(),
            new ArcherCapacity2(),
            new ArcherCapacity3()
    ));

    @Override
    public ClasseType getClassType() {
        return ClasseType.ARCHER;
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
