package net.scandicraft.recipes;

import net.minecraft.item.ItemStack;
import net.scandicraft.blocks.register.ScandiCraftBlocks;
import net.scandicraft.items.register.ScandiCraftItems;

public class Ingots {

    public static final Object[][] recipeItems = new Object[][]{
            {ScandiCraftBlocks.scandium_block, new ItemStack(ScandiCraftItems.scandium, 9)},
            {ScandiCraftBlocks.pyrite_block, new ItemStack(ScandiCraftItems.pyrite, 9)},
            {ScandiCraftBlocks.lazurite_block, new ItemStack(ScandiCraftItems.lazurite, 9)}
    };

}
