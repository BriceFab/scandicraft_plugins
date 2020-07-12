package net.scandicraft.capacities.impl;

import net.minecraft.util.ResourceLocation;
import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.config.ClassesConfig;

/**
 * se téléporte sur le joueur visé
 */
public class GuerrierCapacity2 extends BaseCapacity {
    @Override
    public ResourceLocation getCapacityIcon() {
        return new ResourceLocation("scandicraft/capacities/guerrier_2.png");
    }

    @Override
    public String getName() {
        return "téléportation guerrière";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_2;
    }

    @Override
    public String getUniqueIdentifier() {
        return "GuerrierCapacity2";
    }

    @Override
    public void onUse() throws CapacityException {
        //TODO
    }
}
