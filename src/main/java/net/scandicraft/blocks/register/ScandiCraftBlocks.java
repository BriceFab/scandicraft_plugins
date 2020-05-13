package net.scandicraft.blocks.register;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.scandicraft.blocks.PyriteChest;
import net.scandicraft.blocks.RandomOre;

public class ScandiCraftBlocks {

    public static final Block scandium_ore;
    public static final Block scandium_block;
    public static final Block pyrite_ore;
    public static final Block pyrite_block;
    public static final Block lazurite_ore;
    public static final Block lazurite_block;
    public static final PyriteChest pyrite_chest;
    public static final Block lazurite_ladder;
    public static final Block light;
    public static final Block dungeon_spawner;
    public static final RandomOre random_ore;

    static {
        scandium_ore = Blocks.getRegisteredBlock("scandium_ore");
        scandium_block = Blocks.getRegisteredBlock("scandium_block");
        pyrite_ore = Blocks.getRegisteredBlock("pyrite_ore");
        pyrite_block = Blocks.getRegisteredBlock("pyrite_block");
        lazurite_ore = Blocks.getRegisteredBlock("lazurite_ore");
        lazurite_block = Blocks.getRegisteredBlock("lazurite_block");
        pyrite_chest = (PyriteChest) Blocks.getRegisteredBlock("pyrite_chest");
        lazurite_ladder = Blocks.getRegisteredBlock("lazurite_ladder");
        light = Blocks.getRegisteredBlock("light");
        dungeon_spawner = Blocks.getRegisteredBlock("dungeon_spawner");
        random_ore = (RandomOre) Blocks.getRegisteredBlock("random_ore");
    }

}
