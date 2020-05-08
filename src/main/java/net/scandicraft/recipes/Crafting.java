package net.scandicraft.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.scandicraft.blocks.ScandiCraftBlocks;
import net.scandicraft.items.ScandiCraftItems;

public class Crafting {

    public static void registerCraftings(CraftingManager craftingManager) {
        craftingManager.addRecipe(new ItemStack(ScandiCraftBlocks.pyrite_chest), "###", "# #", "###", '#', ScandiCraftItems.pyrite);
        craftingManager.addRecipe(new ItemStack(ScandiCraftBlocks.lazurite_ladder, 3), "# #", "###", "# #", '#', ScandiCraftItems.lazurite);
    }

}
