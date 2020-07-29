package net.scandicraft.capacities.impl;

import net.minecraft.util.ResourceLocation;
import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.config.ClassesConfig;

/**
 * speed 3 pendant 5 secondes et 5 coeurs d'absorption
 */
public class GuerrierCapacity3 extends BaseCapacity {
    @Override
    public ResourceLocation getCapacityIcon() {
        return new ResourceLocation("scandicraft/capacities/guerrier_3.png");
    }

    @Override
    public String getName() {
        return "fuyard";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_3;
    }

    @Override
    public String getUniqueIdentifier() {
        return "GuerrierCapacity3";
    }

    @Override
    public void onUse() {
        //TODO
    }
}
