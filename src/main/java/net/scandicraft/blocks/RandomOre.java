package net.scandicraft.blocks;

import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.scandicraft.Config;
import net.scandicraft.items.register.ScandiCraftItems;

import java.util.Random;

public class RandomOre extends BlockOre {

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        int chance = rand.nextInt(100);

        if (chance < 30) {  //30%
            return Items.diamond;
        } else if (chance < 60) {   //30%
            return Items.gold_ingot;
        } else if (chance < 95) {   //37%
            return ScandiCraftItems.lazurite;
        } else if (chance < 100) {  //3%
            return ScandiCraftItems.pyrite;
        }

        return null;
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        Config.print_debug("dropBlockAsItemWithChance");
        int nbrXP = MathHelper.getRandomIntegerInRange(worldIn.rand, 1, 3);
        this.dropXpOnBlockBreak(worldIn, pos, nbrXP);

        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }

    @Override
    public int quantityDropped(Random random) {
        Config.print_debug("quantityDropped");
        return MathHelper.getRandomIntegerInRange(random, 1, 2);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        Config.print_debug("quantityDroppedWithBonus");
        return MathHelper.getRandomIntegerInRange(random, 1, 3);
    }

    @Override
    protected void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount) {
        Config.print_debug("drop XP");
        super.dropXpOnBlockBreak(worldIn, pos, amount * 5);    //10x plus d'XP
    }
}
