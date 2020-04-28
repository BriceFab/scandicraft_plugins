package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.scandicraft.items.ScandiCraftItems;

public class RecipesTools
{
    private String[][] recipePatterns = new String[][] {{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}};
    private Object[][] recipeItems = new Object[][] {{Blocks.planks, Blocks.cobblestone, Items.iron_ingot, Items.diamond, Items.gold_ingot, ScandiCraftItems.scandium, ScandiCraftItems.pyrite, ScandiCraftItems.lazurite},
            {Items.wooden_pickaxe, Items.stone_pickaxe, Items.iron_pickaxe, Items.diamond_pickaxe, Items.golden_pickaxe, ScandiCraftItems.scandium_pickaxe, ScandiCraftItems.pyrite_pickaxe, ScandiCraftItems.lazurite_pickaxe},
            {Items.wooden_shovel, Items.stone_shovel, Items.iron_shovel, Items.diamond_shovel, Items.golden_shovel, ScandiCraftItems.scandium_shovel, ScandiCraftItems.pyrite_shovel, ScandiCraftItems.lazurite_shovel},
            {Items.wooden_axe, Items.stone_axe, Items.iron_axe, Items.diamond_axe, Items.golden_axe, ScandiCraftItems.scandium_axe, ScandiCraftItems.pyrite_axe, ScandiCraftItems.lazurite_axe},
            {Items.wooden_hoe, Items.stone_hoe, Items.iron_hoe, Items.diamond_hoe, Items.golden_hoe, ScandiCraftItems.scandium_hoe, ScandiCraftItems.pyrite_hoe, ScandiCraftItems.lazurite_hoe}};

    /**
     * Adds the tool recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager p_77586_1_)
    {
        for (int i = 0; i < this.recipeItems[0].length; ++i)
        {
            Object object = this.recipeItems[0][i];

            for (int j = 0; j < this.recipeItems.length - 1; ++j)
            {
                Item item = (Item)this.recipeItems[j + 1][i];
                p_77586_1_.addRecipe(new ItemStack(item), new Object[] {this.recipePatterns[j], '#', Items.stick, 'X', object});
            }
        }

        p_77586_1_.addRecipe(new ItemStack(Items.shears), new Object[] {" #", "# ", '#', Items.iron_ingot});
    }
}
