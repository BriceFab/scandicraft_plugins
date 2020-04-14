package net.scandicraft.items;

import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSpawnScepter extends Item {
    private final int CREEPER_METADATA = 50;

    public ItemSpawnScepter(int max_uses) {
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
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
            return false;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(pos);

            pos = pos.offset(side);
            double d0 = 0.0D;

            if (side == EnumFacing.UP && iblockstate instanceof BlockFence) {
                d0 = 0.5D;
            }

            Entity entity = spawnCreature(worldIn, CREEPER_METADATA, (double) pos.getX() + 0.5D, (double) pos.getY() + d0, (double) pos.getZ() + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && stack.hasDisplayName()) {
                    entity.setCustomNameTag(stack.getDisplayName());
                }
            }

            stack.damageItem(1, playerIn);

            return true;
        }
    }

    /**
     * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
     * Parameters: world, entityID, x, y, z.
     */
    public static Entity spawnCreature(World worldIn, int entityID, double x, double y, double z) {
        Entity entity = null;

        for (int i = 0; i < 1; ++i) {
            entity = EntityList.createEntityByID(entityID, worldIn);

            if (entity != null && entity instanceof EntityLivingBase) {
                EntityLiving entityliving = (EntityLiving) entity;
                entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0F), 0.0F);
                entityliving.rotationYawHead = entityliving.rotationYaw;
                entityliving.renderYawOffset = entityliving.rotationYaw;
                entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData) null);
                worldIn.spawnEntityInWorld(entity);
                entityliving.playLivingSound();
            }
        }

        return entity;
    }
}
