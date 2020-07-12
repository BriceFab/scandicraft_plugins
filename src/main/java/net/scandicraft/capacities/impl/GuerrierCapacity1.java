package net.scandicraft.capacities.impl;

import net.minecraft.util.ResourceLocation;
import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.config.ClassesConfig;

/**
 * Capacité 1: donne 5 secondes de force III
 */
public class GuerrierCapacity1 extends BaseCapacity {
    @Override
    public ResourceLocation getCapacityIcon() {
        return new ResourceLocation("scandicraft/capacities/guerrier_1.png");
    }

    @Override
    public String getName() {
        return "épée sanglante";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_1;
    }

    @Override
    public String getUniqueIdentifier() {
        return "GuerrierCapacity1";
    }

    @Override
    public void onUse() {
        //TODO
    }

}
