package net.scandicraft.capacities.impl;

import net.minecraft.util.ResourceLocation;
import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.config.ClassesConfig;

/**
 * téléporte lui et le joueur visé dans une arène PvP pendant 1 minute
 */
public class MagicienCapacity3 extends BaseCapacity {
    @Override
    public ResourceLocation getCapacityIcon() {
        return null;
    }

    @Override
    public String getName() {
        return "combattant";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_3;
    }

    @Override
    public void onUse() {
        //TODO
    }
}
