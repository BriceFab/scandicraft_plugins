package net.scandicraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class PyritePie extends ItemFood {
    public PyritePie(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            //20 ticks = 1 seconde
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 3 * 60 * 20)); //force I: 3mn
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 3 * 60 * 20, 1)); //speed II: 3mn
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 2 * 60 * 20));   //fire resist I: 2mn
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 30 * 20));   //regene I: 30s
        } else {
            super.onFoodEaten(stack, worldIn, player);
        }
    }
}
