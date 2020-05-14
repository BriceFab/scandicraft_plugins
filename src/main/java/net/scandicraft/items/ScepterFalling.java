package net.scandicraft.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.scandicraft.potions.ScandiPotion;
import net.scandicraft.utils.MathUtils;

public class ScepterFalling extends Item {

    public ScepterFalling() {
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if (!playerIn.capabilities.isCreativeMode) {
            --itemStackIn.stackSize;
        }

        //20 ticks = 1 seconde
        playerIn.addPotionEffect(new PotionEffect(ScandiPotion.feather_falling.id, MathUtils.convertMinutesToTicks(5)));    //5 mn

        return itemStackIn;
    }

}
