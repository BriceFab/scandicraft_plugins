package net.scandicraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.scandicraft.utils.MathUtils;

public class PyritePie extends ItemFood {
    public PyritePie(int amount, float saturation) {
        super(amount, saturation, false);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            //20 ticks = 1 seconde
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, MathUtils.convertMinutesToTicks(3))); //force I: 3mn
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, MathUtils.convertMinutesToTicks(3), 1)); //speed II: 3mn
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, MathUtils.convertMinutesToTicks(2)));   //fire resist I: 2mn
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, MathUtils.convertSecondsToTicks(30)));   //regene I: 30s
        } else {
            super.onFoodEaten(stack, worldIn, player);
        }
    }
}
