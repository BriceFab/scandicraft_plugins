package net.scandicraft.blocks;

import net.minecraft.item.Item;
import net.scandicraft.items.ScandiCraftItems;

public class BlockOre {

    public static Item getItemDropped(net.minecraft.block.BlockOre blockOre) {
        if (ScandiCraftBlocks.scandium_ore.equals(blockOre)) {
            return ScandiCraftItems.scandium;
        } else if (ScandiCraftBlocks.pyrite_ore.equals(blockOre)) {
            return ScandiCraftItems.pyrite;
        } else if (ScandiCraftBlocks.lazurite_ore.equals(blockOre)) {
            return ScandiCraftItems.lazurite;
        }
        return null;
    }

}
