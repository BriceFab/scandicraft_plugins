package net.scandicraft.capacities.impl;

import net.minecraft.util.ResourceLocation;
import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.config.ClassesConfig;

/**
 * flèche qui enlève la moitié de la vie actuelle du joueur
 */
public class ArcherCapacity3 extends BaseCapacity {
    @Override
    public ResourceLocation getCapacityIcon() {
        return new ResourceLocation("scandicraft/capacities/archer_3.png");
    }

    @Override
    public String getName() {
        return "flèche sanglante";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_3;
    }

    @Override
    public String getUniqueIdentifier() {
        return "ArcherCapacity3";
    }

    @Override
    public void onUse() throws CapacityException {
        //TODO
    }
}
