package net.scandicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ScandiCraftBlock {

    public static void registerBlocks() {
        //Start ID : 2500
        Block.registerBlock(2500, "scandium_ore", (new BlockOre()).setHardness(7.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreScandium"));
        Block.registerBlock(2501, "scandium_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(11.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockScandium").setCreativeTab(CreativeTabs.tabBlock));
        Block.registerBlock(2502, "pyrite_ore", (new BlockOre()).setHardness(5.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setUnlocalizedName("orePyrite"));
        Block.registerBlock(2503, "pyrite_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(8.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockPyrite").setCreativeTab(CreativeTabs.tabBlock));
        Block.registerBlock(2504, "lazurite_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreLazurite"));
        Block.registerBlock(2505, "lazurite_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockLazurite").setCreativeTab(CreativeTabs.tabBlock));
        Block.registerBlock(2506, "pyrite_chest", (new PyriteBlockChest()).setHardness(10F).setStepSound(Block.soundTypePiston).setUnlocalizedName("chestPyrite").setLightLevel(0.5F));
        Block.registerBlock(2507, "lazurite_ladder", (new BlockLadder()).setHardness(0.4F).setStepSound(Block.soundTypeLadder).setUnlocalizedName("ladderLazurite"));
    }

}
