package net.scandicraft.render;

import net.minecraft.client.renderer.entity.RenderItem;
import net.scandicraft.blocks.register.ScandiCraftBlocks;
import net.scandicraft.items.register.ScandiCraftItems;

public class Item {

    //ScandiCraft call by RenderItem.java
    public static void registerItems(RenderItem renderItem) {
        splitRegisterBlocks(renderItem);
        splitRegisterItems(renderItem);
    }

    private static void splitRegisterItems(RenderItem renderItem) {
        renderItem.registerItem(ScandiCraftItems.scandium, "scandium");
        renderItem.registerItem(ScandiCraftItems.pyrite, "pyrite");
        renderItem.registerItem(ScandiCraftItems.lazurite, "lazurite");
        renderItem.registerItem(ScandiCraftItems.scandium_sword, "scandium_sword");
        renderItem.registerItem(ScandiCraftItems.scandium_shovel, "scandium_shovel");
        renderItem.registerItem(ScandiCraftItems.scandium_pickaxe, "scandium_pickaxe");
        renderItem.registerItem(ScandiCraftItems.scandium_axe, "scandium_axe");
        renderItem.registerItem(ScandiCraftItems.scandium_hoe, "scandium_hoe");
        renderItem.registerItem(ScandiCraftItems.pyrite_sword, "pyrite_sword");
        renderItem.registerItem(ScandiCraftItems.pyrite_shovel, "pyrite_shovel");
        renderItem.registerItem(ScandiCraftItems.pyrite_pickaxe, "pyrite_pickaxe");
        renderItem.registerItem(ScandiCraftItems.pyrite_axe, "pyrite_axe");
        renderItem.registerItem(ScandiCraftItems.pyrite_hoe, "pyrite_hoe");
        renderItem.registerItem(ScandiCraftItems.lazurite_sword, "lazurite_sword");
        renderItem.registerItem(ScandiCraftItems.lazurite_shovel, "lazurite_shovel");
        renderItem.registerItem(ScandiCraftItems.lazurite_pickaxe, "lazurite_pickaxe");
        renderItem.registerItem(ScandiCraftItems.lazurite_axe, "lazurite_axe");
        renderItem.registerItem(ScandiCraftItems.lazurite_hoe, "lazurite_hoe");
        renderItem.registerItem(ScandiCraftItems.scandium_helmet, "scandium_helmet");
        renderItem.registerItem(ScandiCraftItems.scandium_chestplate, "scandium_chestplate");
        renderItem.registerItem(ScandiCraftItems.scandium_leggings, "scandium_leggings");
        renderItem.registerItem(ScandiCraftItems.scandium_boots, "scandium_boots");
        renderItem.registerItem(ScandiCraftItems.pyrite_helmet, "pyrite_helmet");
        renderItem.registerItem(ScandiCraftItems.pyrite_chestplate, "pyrite_chestplate");
        renderItem.registerItem(ScandiCraftItems.pyrite_leggings, "pyrite_leggings");
        renderItem.registerItem(ScandiCraftItems.pyrite_boots, "pyrite_boots");
        renderItem.registerItem(ScandiCraftItems.lazurite_helmet, "lazurite_helmet");
        renderItem.registerItem(ScandiCraftItems.lazurite_chestplate, "lazurite_chestplate");
        renderItem.registerItem(ScandiCraftItems.lazurite_leggings, "lazurite_leggings");
        renderItem.registerItem(ScandiCraftItems.lazurite_boots, "lazurite_boots");
        renderItem.registerItem(ScandiCraftItems.bloody_helmet, "bloody_helmet");
        renderItem.registerItem(ScandiCraftItems.bloody_chestplate, "bloody_chestplate");
        renderItem.registerItem(ScandiCraftItems.bloody_leggings, "bloody_leggings");
        renderItem.registerItem(ScandiCraftItems.bloody_boots, "bloody_boots");
        renderItem.registerItem(ScandiCraftItems.bloody_sword, "bloody_sword");
        renderItem.registerItem(ScandiCraftItems.scepter_creeper, "scepter_creeper");
        renderItem.registerItem(ScandiCraftItems.scepter_repair, "scepter_repair");
        renderItem.registerItem(ScandiCraftItems.lazurite_bread, "lazurite_bread");
        renderItem.registerItem(ScandiCraftItems.pyrite_pie, "pyrite_pie");
        renderItem.registerItem(ScandiCraftItems.scandium_bow, 0, "scandium_bow");
        renderItem.registerItem(ScandiCraftItems.scandium_bow, 1, "scandium_bow_pulling_0");
        renderItem.registerItem(ScandiCraftItems.scandium_bow, 2, "scandium_bow_pulling_1");
        renderItem.registerItem(ScandiCraftItems.scandium_bow, 3, "scandium_bow_pulling_2");
        renderItem.registerItem(ScandiCraftItems.scandivote, "scandivote");
        renderItem.registerItem(ScandiCraftItems.scandibox, "scandibox");
    }

    private static void splitRegisterBlocks(RenderItem renderItem) {
        renderItem.registerBlock(ScandiCraftBlocks.scandium_ore, "scandium_ore");
        renderItem.registerBlock(ScandiCraftBlocks.scandium_block, "scandium_block");
        renderItem.registerBlock(ScandiCraftBlocks.pyrite_ore, "pyrite_ore");
        renderItem.registerBlock(ScandiCraftBlocks.pyrite_block, "pyrite_block");
        renderItem.registerBlock(ScandiCraftBlocks.lazurite_ore, "lazurite_ore");
        renderItem.registerBlock(ScandiCraftBlocks.lazurite_block, "lazurite_block");
        renderItem.registerBlock(ScandiCraftBlocks.pyrite_chest, "pyrite_chest");
        renderItem.registerBlock(ScandiCraftBlocks.lazurite_ladder, "lazurite_ladder");
        renderItem.registerBlock(ScandiCraftBlocks.light, "light");
        renderItem.registerBlock(ScandiCraftBlocks.dungeon_spawner, "dungeon_spawner");
    }

}
