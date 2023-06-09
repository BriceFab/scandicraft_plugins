package net.scandicraft.blocks.register;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.scandicraft.blocks.PyriteChest;
import net.scandicraft.blocks.RandomOre;

public class ScandiCraftBlock {

    public static void registerBlocks() {
        //Start ID : 2500
        Block.registerBlock(2500, "lazurite_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreLazurite"));
        Block.registerBlock(2501, "lazurite_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockLazurite").setCreativeTab(CreativeTabs.tabBlock));
        Block.registerBlock(2502, "pyrite_ore", (new BlockOre()).setHardness(5.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setUnlocalizedName("orePyrite"));
        Block.registerBlock(2503, "pyrite_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(8.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockPyrite").setCreativeTab(CreativeTabs.tabBlock));
        Block.registerBlock(2504, "scandium_ore", (new BlockOre()).setHardness(7.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreScandium"));
        Block.registerBlock(2505, "scandium_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(11.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockScandium").setCreativeTab(CreativeTabs.tabBlock));

        Block.registerBlock(2506, "pyrite_chest", (new PyriteChest()).setHardness(10F).setStepSound(Block.soundTypePiston).setUnlocalizedName("chestPyrite").setLightLevel(0.5F));
        Block.registerBlock(2507, "lazurite_ladder", (new BlockLadder()).setHardness(0.4F).setStepSound(Block.soundTypeLadder).setUnlocalizedName("ladderLazurite"));
        Block.registerBlock(2508, "light", (new BlockAir()).setHardness(10.0F).setLightLevel(1.0F).setUnlocalizedName("light").setCreativeTab(CreativeTabs.tabBlock));
        Block.registerBlock(2509, "dungeon_spawner", (new BlockAir()).setHardness(10.0F).setUnlocalizedName("dungeon_spawner").setCreativeTab(CreativeTabs.tabBlock));
        Block.registerBlock(2510, "random_ore", (new RandomOre()).setHardness(7.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreRandom"));
    }

}
