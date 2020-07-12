package net.scandicraft.capacities.impl;

import net.minecraft.util.ResourceLocation;
import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.config.ClassesConfig;

/**
 * éclair sur la personne ou le bloc qu’il vise
 */
public class MagicienCapacity1 extends BaseCapacity {
    @Override
    public ResourceLocation getCapacityIcon() {
        return null;
    }

    @Override
    public String getName() {
        return "éclair";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_1;
    }

    @Override
    public String getUniqueIdentifier() {
        return "MagicienCapacity1";
    }

    @Override
    public void onUse() throws CapacityException {
        //TODO
    }

}
