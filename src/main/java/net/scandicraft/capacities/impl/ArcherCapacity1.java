package net.scandicraft.capacities.impl;

import net.minecraft.util.ResourceLocation;
import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.config.ClassesConfig;

/**
 * tire 3 flèches spectrales devant lui
 */
public class ArcherCapacity1 extends BaseCapacity {
    @Override
    public ResourceLocation getCapacityIcon() {
        return null;
    }

    @Override
    public String getName() {
        return "ArcherCapacity1";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_1;
    }

    @Override
    public void onUse() throws CapacityException {
        //TODO
    }
}
