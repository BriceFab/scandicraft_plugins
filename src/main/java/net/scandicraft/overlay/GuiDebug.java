package net.scandicraft.overlay;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.Chunk;
import net.scandicraft.config.Config;

import java.util.ArrayList;
import java.util.List;

public class GuiDebug extends GuiOverlayDebug {
    public GuiDebug(Minecraft mc) {
        super(mc);
    }

    @Override
    protected List call() {
        BlockPos blockpos = new BlockPos(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);

        Entity entity = this.mc.getRenderViewEntity();
        EnumFacing enumfacing = entity.getHorizontalFacing();

        ArrayList<String> arraylist = Lists.newArrayList(
                String.format("%s - %s (%s)", Config.SERVER_NAME, Config.VERSION, Config.ENV),
                "FPS: " + Minecraft.getDebugFPS(),
                String.format("X: %.0f", this.mc.getRenderViewEntity().posX),
                String.format("Y: %.0f", this.mc.getRenderViewEntity().getEntityBoundingBox().minY),
                String.format("Z: %.0f", this.mc.getRenderViewEntity().posZ),
                String.format("Direction: %s", enumfacing)
        );

        if (this.mc.theWorld != null && this.mc.theWorld.isBlockLoaded(blockpos)) {
            Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(blockpos);
            arraylist.add("Biome: " + chunk.getBiome(blockpos, this.mc.theWorld.getWorldChunkManager()).biomeName);
        }

        //TODO à tester avec shaders
//        if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
//            arraylist.add("Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
//        }

        return arraylist;
    }

    @Override
    protected List getDebugInfoRight() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long currentMemory = totalMemory - freeMemory;

        return Lists.newArrayList(
                EnumChatFormatting.GREEN + String.format("Mémoire: % 2d%% %03d/%03dMB", currentMemory * 100L / maxMemory, bytesToMb(currentMemory), bytesToMb(maxMemory))
        );
    }
}
