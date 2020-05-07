package net.scandicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ScandiCraftBlocks {

    public static final Block scandium_ore;
    public static final Block scandium_block;
    public static final Block pyrite_ore;
    public static final Block pyrite_block;
    public static final Block lazurite_ore;
    public static final Block lazurite_block;
    public static final PyriteBlockChest pyrite_chest;

    static {
        scandium_ore = Blocks.getRegisteredBlock("scandium_ore");
        scandium_block = Blocks.getRegisteredBlock("scandium_block");
        pyrite_ore = Blocks.getRegisteredBlock("pyrite_ore");
        pyrite_block = Blocks.getRegisteredBlock("pyrite_block");
        lazurite_ore = Blocks.getRegisteredBlock("lazurite_ore");
        lazurite_block = Blocks.getRegisteredBlock("lazurite_block");
        pyrite_chest = (PyriteBlockChest) Blocks.getRegisteredBlock("pyrite_chest");
    }

}
