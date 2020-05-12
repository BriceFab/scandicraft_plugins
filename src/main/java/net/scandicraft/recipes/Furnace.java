package net.scandicraft.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.scandicraft.blocks.register.ScandiCraftBlocks;
import net.scandicraft.items.register.ScandiCraftItems;

public class Furnace {

    public static void registerFurnaceRecipes(FurnaceRecipes furnaceRecipes) {
        furnaceRecipes.addSmeltingRecipeForBlock(ScandiCraftBlocks.scandium_ore, new ItemStack(ScandiCraftItems.scandium), 3.0F);
        furnaceRecipes.addSmeltingRecipeForBlock(ScandiCraftBlocks.pyrite_ore, new ItemStack(ScandiCraftItems.pyrite), 2.0F);
        furnaceRecipes.addSmeltingRecipeForBlock(ScandiCraftBlocks.lazurite_ore, new ItemStack(ScandiCraftItems.lazurite), 1.5F);
    }

}
