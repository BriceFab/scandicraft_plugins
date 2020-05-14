package net.scandicraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.scandicraft.utils.MathUtils;

public class LazuriteBread extends ItemFood {
    public LazuriteBread(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            int two_minutes = MathUtils.convertMinutesToTicks(2);
            player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, two_minutes)); //haste I: 2mn
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, two_minutes)); //fire resit I: 2mn
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, two_minutes)); //speed I: 2mn
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, two_minutes)); //night vision I: 2mn
        } else {
            super.onFoodEaten(stack, worldIn, player);
        }
    }
}
