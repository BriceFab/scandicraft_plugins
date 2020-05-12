package net.scandicraft.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ScepterFalling extends Item {

    public ScepterFalling() {
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setMaxStackSize(1);
        this.setMaxDamage(1);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {

        itemStackIn.damageItem(1, playerIn);

        //20 ticks = 1 seconde
        playerIn.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5 * 60 * 20));    //5 mn
        //TODO potion no fall

        return itemStackIn;
    }

}
