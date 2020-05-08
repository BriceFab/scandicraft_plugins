package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.scandicraft.blocks.ScandiCraftBlocks;

public class ItemAxe extends ItemTool
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder, ScandiCraftBlocks.lazurite_ladder);

    //ScandiCraft et protected to public
    public ItemAxe(Item.ToolMaterial material)
    {
        super(3.0F, material, EFFECTIVE_ON);
    }

    public float getStrVsBlock(ItemStack stack, Block block)
    {
        return block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine ? super.getStrVsBlock(stack, block) : this.efficiencyOnProperMaterial;
    }
}
