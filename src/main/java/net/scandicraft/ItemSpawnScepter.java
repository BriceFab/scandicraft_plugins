package net.scandicraft;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemSpawnScepter extends Item {
    private float attackDamage;
    private static final Logger logger = LogManager.getLogger();

    public ItemSpawnScepter(int maxUse) {

        this.maxStackSize = 1;
        this.setMaxDamage(maxUse);
        this.setCreativeTab(CreativeTabs.tabCombat);

    }

    /**
     * Returns the amount of damage this item will deal. One heart of damage is equal to 2 damage points.
     */


    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
        if ((double) blockIn.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, playerIn);
        }

        return true;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D() {
        return true;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack) {

        return EnumAction.BLOCK;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        logger.info("CLICK");
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
        if (movingobjectposition == null)
        {
            return itemStackIn;
        }
        BlockPos blockpos = movingobjectposition.getBlockPos();
        Entity entity = spawnCreature(worldIn, itemStackIn.getMetadata(), (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.5D, (double)blockpos.getZ() + 0.5D);

       return  itemStackIn;
    }
    public static Entity spawnCreature(World worldIn, int entityID, double x, double y, double z)
    {


            Entity entity = null;

        for (int i = 0; i < 1; ++i) {
            entity = EntityList.createEntityByID(50, worldIn);

            if (entity instanceof EntityLivingBase) {
                EntityLiving entityliving = (EntityLiving) entity;
                entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0F), 0.0F);
                entityliving.rotationYawHead = entityliving.rotationYaw;
                entityliving.renderYawOffset = entityliving.rotationYaw;
                entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData) null);
                worldIn.spawnEntityInWorld(entity);
                entityliving.playLivingSound();
                logger.info("SPAWN : " + entity);
            }


        }
            return  entity;
    }


}
