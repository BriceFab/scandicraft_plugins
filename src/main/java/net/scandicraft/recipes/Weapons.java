package net.scandicraft.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.scandicraft.items.register.ScandiCraftItems;

public class Weapons {

    public static void addRecipes(CraftingManager craftingManager) {
        craftingManager.addRecipe(new ItemStack(ScandiCraftItems.scandium_bow, 1), " #X", "# X", " #X", 'X', Items.string, '#', ScandiCraftItems.scandium);
    }

}
