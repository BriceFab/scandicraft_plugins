package net.scandicraft.classes.impl;

import net.scandicraft.capacities.ICapacity;
import net.scandicraft.capacities.impl.MagicienCapacity1;
import net.scandicraft.capacities.impl.MagicienCapacity2;
import net.scandicraft.capacities.impl.MagicienCapacity3;
import net.scandicraft.classes.ClasseType;
import net.scandicraft.classes.IClasse;

import java.util.ArrayList;
import java.util.Arrays;

public class Magicien implements IClasse {
    private final ArrayList<ICapacity> capacities = new ArrayList<>(Arrays.asList(
            new MagicienCapacity1(),
            new MagicienCapacity2(),
            new MagicienCapacity3()
    ));

    @Override
    public ClasseType getClassType() {
        return ClasseType.MAGICIEN;
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
