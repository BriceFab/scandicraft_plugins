package net.scandicraft.capacities.impl;

import net.minecraft.util.ResourceLocation;
import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.config.ClassesConfig;

/**
 * se téléporte sur 10 blocs devant lui
 */
public class ArcherCapacity2 extends BaseCapacity {
    @Override
    public ResourceLocation getCapacityIcon() {
        return new ResourceLocation("scandicraft/capacities/archer_2.png");
    }

    @Override
    public String getName() {
        return "téléportation archer";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_2;
    }

    @Override
    public void onUse() {
        //TODO
    }
}
