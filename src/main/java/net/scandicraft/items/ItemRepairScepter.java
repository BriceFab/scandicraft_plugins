package net.scandicraft.items;

import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmorStand;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.scandicraft.Config;

public class ItemRepairScepter extends Item {


    public ItemRepairScepter(int max_uses) {
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setMaxDamage(max_uses - 1); //car 0 est pris en compte
        this.setMaxStackSize(1);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    /**
     * Called when a Block is right-clicked with this Item
     */

    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        itemStackIn.damageItem(1,playerIn);
        ItemStack[] inv = playerIn.inventory.mainInventory;
        ItemStack[] armor = playerIn.getInventory();

        for (ItemStack item : inv)
        {

            if (item != null && item.getItem() != Items.scepter_repair)
            {
                item.setItemDamage(0);
            }

        }
        for (ItemStack item : armor) {
            if (item != null) {
                item.setItemDamage(0);
            }
        }


        return itemStackIn;
    }


}
