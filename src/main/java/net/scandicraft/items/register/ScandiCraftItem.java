package net.scandicraft.items.register;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.scandicraft.blocks.register.ScandiCraftBlocks;
import net.scandicraft.items.*;

public class ScandiCraftItem {

    public static void registerItems() {
        splitRegisterBlocks();
        splitRegisterItems();
    }

    private static void splitRegisterItems() {
        //ScandiCraft : new Items
        //Start ID : 3500
        Item.registerItem(3500, "lazurite", (new Item()).setUnlocalizedName("lazurite").setCreativeTab(CreativeTabs.tabMaterials));
        Item.registerItem(3501, "lazurite_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.LAZURITE, 9, 0)).setUnlocalizedName("helmetLazurite"));
        Item.registerItem(3502, "lazurite_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.LAZURITE, 9, 1)).setUnlocalizedName("chestplateLazurite"));
        Item.registerItem(3503, "lazurite_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.LAZURITE, 9, 2)).setUnlocalizedName("leggingsLazurite"));
        Item.registerItem(3504, "lazurite_boots", (new ItemArmor(ItemArmor.ArmorMaterial.LAZURITE, 9, 3)).setUnlocalizedName("bootsLazurite"));
        Item.registerItem(3505, "lazurite_sword", (new ItemSword(Item.ToolMaterial.LAZURITE)).setUnlocalizedName("swordLazurite"));
        Item.registerItem(3506, "lazurite_shovel", (new ItemSpade(Item.ToolMaterial.LAZURITE)).setUnlocalizedName("shovelLazurite"));
        Item.registerItem(3507, "lazurite_pickaxe", (new ItemPickaxe(Item.ToolMaterial.LAZURITE)).setUnlocalizedName("pickaxeLazurite"));
        Item.registerItem(3508, "lazurite_axe", (new ItemAxe(Item.ToolMaterial.LAZURITE)).setUnlocalizedName("hatchetLazurite"));
        Item.registerItem(3509, "lazurite_hoe", (new ItemHoe(Item.ToolMaterial.LAZURITE)).setUnlocalizedName("hoeLazurite"));

        Item.registerItem(3510, "pyrite", (new Item()).setUnlocalizedName("pyrite").setCreativeTab(CreativeTabs.tabMaterials));
        Item.registerItem(3511, "pyrite_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.PYRITE, 8, 0)).setUnlocalizedName("helmetPyrite"));
        Item.registerItem(3512, "pyrite_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.PYRITE, 8, 1)).setUnlocalizedName("chestplatePyrite"));
        Item.registerItem(3513, "pyrite_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.PYRITE, 8, 2)).setUnlocalizedName("leggingsPyrite"));
        Item.registerItem(3514, "pyrite_boots", (new ItemArmor(ItemArmor.ArmorMaterial.PYRITE, 8, 3)).setUnlocalizedName("bootsPyrite"));
        Item.registerItem(3515, "pyrite_sword", (new ItemSword(Item.ToolMaterial.PYRITE)).setUnlocalizedName("swordPyrite"));
        Item.registerItem(3516, "pyrite_shovel", (new ItemSpade(Item.ToolMaterial.PYRITE)).setUnlocalizedName("shovelPyrite"));
        Item.registerItem(3517, "pyrite_pickaxe", (new ItemPickaxe(Item.ToolMaterial.PYRITE)).setUnlocalizedName("pickaxePyrite"));
        Item.registerItem(3518, "pyrite_axe", (new ItemAxe(Item.ToolMaterial.PYRITE)).setUnlocalizedName("hatchetPyrite"));
        Item.registerItem(3519, "pyrite_hoe", (new ItemHoe(Item.ToolMaterial.PYRITE)).setUnlocalizedName("hoePyrite"));

        Item.registerItem(3520, "scandium", (new Item()).setUnlocalizedName("scandium").setCreativeTab(CreativeTabs.tabMaterials));
        Item.registerItem(3521, "scandium_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.SCANDIUM, 7, 0)).setUnlocalizedName("helmetScandium"));
        Item.registerItem(3522, "scandium_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.SCANDIUM, 7, 1)).setUnlocalizedName("chestplateScandium"));
        Item.registerItem(3523, "scandium_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.SCANDIUM, 7, 2)).setUnlocalizedName("leggingsScandium"));
        Item.registerItem(3524, "scandium_boots", (new ItemArmor(ItemArmor.ArmorMaterial.SCANDIUM, 7, 3)).setUnlocalizedName("bootsScandium"));
        Item.registerItem(3525, "scandium_sword", (new ItemSword(Item.ToolMaterial.SCANDIUM)).setUnlocalizedName("swordScandium"));
        Item.registerItem(3526, "scandium_shovel", (new ItemSpade(Item.ToolMaterial.SCANDIUM)).setUnlocalizedName("shovelScandium"));
        Item.registerItem(3527, "scandium_pickaxe", (new ItemPickaxe(Item.ToolMaterial.SCANDIUM)).setUnlocalizedName("pickaxeScandium"));
        Item.registerItem(3528, "scandium_axe", (new ItemAxe(Item.ToolMaterial.SCANDIUM)).setUnlocalizedName("hatchetScandium"));
        Item.registerItem(3529, "scandium_hoe", (new ItemHoe(Item.ToolMaterial.SCANDIUM)).setUnlocalizedName("hoeScandium"));
        Item.registerItem(3530, "scandium_bow", (new ScandiumBow()).setUnlocalizedName("scandiumBow"));

        /*TODO */
        Item.registerItem(3531, "bloody", (new ItemSword(Item.ToolMaterial.BLOODY)).setUnlocalizedName("bloody"));
        Item.registerItem(3532, "bloody_sword", (new ItemSword(Item.ToolMaterial.BLOODY)).setUnlocalizedName("swordBloody"));
        Item.registerItem(3533, "bloody_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.BLOODY, 6, 0)).setUnlocalizedName("helmetBloody"));
        Item.registerItem(3534, "bloody_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.BLOODY, 6, 1)).setUnlocalizedName("chestplateBloody"));
        Item.registerItem(3535, "bloody_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.BLOODY, 6, 2)).setUnlocalizedName("leggingsBloody"));
        Item.registerItem(3536, "bloody_boots", (new ItemArmor(ItemArmor.ArmorMaterial.BLOODY, 6, 3)).setUnlocalizedName("bootsBloody"));

        Item.registerItem(3537, "scepter_creeper", (new ScepterSpawnCreeper(3).setUnlocalizedName("scepterCreeper")));
        Item.registerItem(3538, "scepter_repair", (new ScepterRepair().setUnlocalizedName("scepterRepair")));
        Item.registerItem(3539, "pyrite_pie", (new PyritePie(8, 2F, false)).setAlwaysEdible().setUnlocalizedName("piePyrite").setCreativeTab(CreativeTabs.tabFood));
        Item.registerItem(3540, "lazurite_bread", (new LazuriteBread(4, 1.2F, false)).setAlwaysEdible().setUnlocalizedName("breadLazurite").setCreativeTab(CreativeTabs.tabFood));

        /*TODO */
        Item.registerItem(3541, "scepter_falling", (new ScepterFalling().setUnlocalizedName("scepterFalling")));
    }

    private static void splitRegisterBlocks() {
        //ScandiCraft : new Blocks
        Item.registerItemBlock(ScandiCraftBlocks.scandium_ore);
        Item.registerItemBlock(ScandiCraftBlocks.scandium_block);
        Item.registerItemBlock(ScandiCraftBlocks.pyrite_ore);
        Item.registerItemBlock(ScandiCraftBlocks.pyrite_block);
        Item.registerItemBlock(ScandiCraftBlocks.lazurite_ore);
        Item.registerItemBlock(ScandiCraftBlocks.lazurite_block);
        Item.registerItemBlock(ScandiCraftBlocks.pyrite_chest);
        Item.registerItemBlock(ScandiCraftBlocks.lazurite_ladder);
    }

}
