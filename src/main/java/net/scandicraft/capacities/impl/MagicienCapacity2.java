package net.scandicraft.capacities.impl;

import net.minecraft.util.ResourceLocation;
import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.config.ClassesConfig;

/**
 * soigne lui + la personne qui vise
 */
public class MagicienCapacity2 extends BaseCapacity {
    @Override
    public ResourceLocation getCapacityIcon() {
        return null;
    }

    @Override
    public String getName() {
        return "soigneur";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_2;
    }

    @Override
    public String getUniqueIdentifier() {
        return "MagicienCapacity2";
    }

    @Override
    public void onUse() throws CapacityException {
        //TODO
    }
}
