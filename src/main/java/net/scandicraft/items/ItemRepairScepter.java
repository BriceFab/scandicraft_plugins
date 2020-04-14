package net.scandicraft.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRepairScepter extends Item {

    public ItemRepairScepter() {
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
        repairStuff(playerIn.inventory.mainInventory);
        repairStuff(playerIn.getInventory());

        if (!playerIn.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }

        return itemStackIn;
    }

    private void repairStuff(ItemStack[] items) {
        for (ItemStack item : items) {
            if (item != null) {
                item.setItemDamage(0);
            }
        }
    }

}
