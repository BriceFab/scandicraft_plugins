package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import optifine.*;

import java.util.BitSet;
import java.util.List;

public class BlockModelRenderer {
    private static float aoLightValueOpaque = 0.2F;

    public static void updateAoLightValue() {
        aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
    }

    public BlockModelRenderer() {
        if (Reflector.ForgeModContainer_forgeLightPipelineEnabled.exists()) {
            Reflector.setFieldValue(Reflector.ForgeModContainer_forgeLightPipelineEnabled, Boolean.FALSE);
        }
    }

    public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn) {
        Block block = blockStateIn.getBlock();
        block.setBlockBoundsBasedOnState(blockAccessIn, blockPosIn);
        return this.renderModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, true);
    }

    public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides) {
        boolean flag = Minecraft.isAmbientOcclusionEnabled() && blockStateIn.getBlock().getLightValue() == 0 && modelIn.isAmbientOcclusion();

        try {
            Block block = blockStateIn.getBlock();

            if (Config.isTreesSmart() && blockStateIn.getBlock() instanceof BlockLeavesBase) {
                modelIn = SmartLeaves.getLeavesModel(modelIn);
            }

            return flag ? this.renderModelAmbientOcclusion(blockAccessIn, modelIn, block, blockStateIn, blockPosIn, worldRendererIn, checkSides) : this.renderModelStandard(blockAccessIn, modelIn, block, blockStateIn, blockPosIn, worldRendererIn, checkSides);
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block model");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block model being tesselated");
            CrashReportCategory.addBlockInfo(crashreportcategory, blockPosIn, blockStateIn);
            crashreportcategory.addCrashSection("Using AO", flag);
            throw new ReportedException(crashreport);
        }
    }

    public boolean renderModelAmbientOcclusion(IBlockAccess p_renderModelAmbientOcclusion_1_, IBakedModel p_renderModelAmbientOcclusion_2_, Block p_renderModelAmbientOcclusion_3_, IBlockState p_renderModelAmbientOcclusion_4_, BlockPos p_renderModelAmbientOcclusion_5_, WorldRenderer p_renderModelAmbientOcclusion_6_, boolean p_renderModelAmbientOcclusion_7_) {
        boolean flag = false;
        RenderEnv renderenv = null;

        for (EnumFacing enumfacing : EnumFacing.VALUES) {
            List list = p_renderModelAmbientOcclusion_2_.getFaceQuads(enumfacing);

            if (!list.isEmpty()) {
                BlockPos blockpos = p_renderModelAmbientOcclusion_5_.offset(enumfacing);

                if (!p_renderModelAmbientOcclusion_7_ || p_renderModelAmbientOcclusion_3_.shouldSideBeRendered(p_renderModelAmbientOcclusion_1_, blockpos, enumfacing)) {
                    if (renderenv == null) {
                        renderenv = RenderEnv.getInstance(p_renderModelAmbientOcclusion_1_, p_renderModelAmbientOcclusion_4_, p_renderModelAmbientOcclusion_5_);
                    }

                    if (!renderenv.isBreakingAnimation(list) && Config.isBetterGrass()) {
                        list = BetterGrass.getFaceQuads(p_renderModelAmbientOcclusion_1_, p_renderModelAmbientOcclusion_3_, p_renderModelAmbientOcclusion_5_, enumfacing, list);
                    }

                    this.renderModelAmbientOcclusionQuads(p_renderModelAmbientOcclusion_1_, p_renderModelAmbientOcclusion_3_, p_renderModelAmbientOcclusion_5_, p_renderModelAmbientOcclusion_6_, list, renderenv);
                    flag = true;
                }
            }
        }

        List list1 = p_renderModelAmbientOcclusion_2_.getGeneralQuads();

        if (list1.size() > 0) {
            if (renderenv == null) {
                renderenv = RenderEnv.getInstance(p_renderModelAmbientOcclusion_1_, p_renderModelAmbientOcclusion_4_, p_renderModelAmbientOcclusion_5_);
            }

            this.renderModelAmbientOcclusionQuads(p_renderModelAmbientOcclusion_1_, p_renderModelAmbientOcclusion_3_, p_renderModelAmbientOcclusion_5_, p_renderModelAmbientOcclusion_6_, list1, renderenv);
            flag = true;
        }

        if (renderenv != null && Config.isBetterSnow() && !renderenv.isBreakingAnimation() && BetterSnow.shouldRender(p_renderModelAmbientOcclusion_1_, p_renderModelAmbientOcclusion_3_, p_renderModelAmbientOcclusion_4_, p_renderModelAmbientOcclusion_5_)) {
            IBakedModel ibakedmodel = BetterSnow.getModelSnowLayer();
            IBlockState iblockstate = BetterSnow.getStateSnowLayer();
            this.renderModelAmbientOcclusion(p_renderModelAmbientOcclusion_1_, ibakedmodel, iblockstate.getBlock(), iblockstate, p_renderModelAmbientOcclusion_5_, p_renderModelAmbientOcclusion_6_, true);
        }

        return flag;
    }

    public boolean renderModelStandard(IBlockAccess p_renderModelStandard_1_, IBakedModel p_renderModelStandard_2_, Block p_renderModelStandard_3_, IBlockState p_renderModelStandard_4_, BlockPos p_renderModelStandard_5_, WorldRenderer p_renderModelStandard_6_, boolean p_renderModelStandard_7_) {
        boolean flag = false;
        RenderEnv renderenv = null;

        for (EnumFacing enumfacing : EnumFacing.VALUES) {
            List list = p_renderModelStandard_2_.getFaceQuads(enumfacing);

            if (!list.isEmpty()) {
                BlockPos blockpos = p_renderModelStandard_5_.offset(enumfacing);

                if (!p_renderModelStandard_7_ || p_renderModelStandard_3_.shouldSideBeRendered(p_renderModelStandard_1_, blockpos, enumfacing)) {
                    if (renderenv == null) {
                        renderenv = RenderEnv.getInstance(p_renderModelStandard_1_, p_renderModelStandard_4_, p_renderModelStandard_5_);
                    }

                    if (!renderenv.isBreakingAnimation(list) && Config.isBetterGrass()) {
                        list = BetterGrass.getFaceQuads(p_renderModelStandard_1_, p_renderModelStandard_3_, p_renderModelStandard_5_, enumfacing, list);
                    }

                    int i = p_renderModelStandard_3_.getMixedBrightnessForBlock(p_renderModelStandard_1_, blockpos);
                    this.renderModelStandardQuads(p_renderModelStandard_1_, p_renderModelStandard_3_, p_renderModelStandard_5_, i, false, p_renderModelStandard_6_, list, renderenv);
                    flag = true;
                }
            }
        }

        List list1 = p_renderModelStandard_2_.getGeneralQuads();

        if (list1.size() > 0) {
            if (renderenv == null) {
                renderenv = RenderEnv.getInstance(p_renderModelStandard_1_, p_renderModelStandard_4_, p_renderModelStandard_5_);
            }

            this.renderModelStandardQuads(p_renderModelStandard_1_, p_renderModelStandard_3_, p_renderModelStandard_5_, -1, true, p_renderModelStandard_6_, list1, renderenv);
            flag = true;
        }

        if (renderenv != null && Config.isBetterSnow() && !renderenv.isBreakingAnimation() && BetterSnow.shouldRender(p_renderModelStandard_1_, p_renderModelStandard_3_, p_renderModelStandard_4_, p_renderModelStandard_5_) && BetterSnow.shouldRender(p_renderModelStandard_1_, p_renderModelStandard_3_, p_renderModelStandard_4_, p_renderModelStandard_5_)) {
            IBakedModel ibakedmodel = BetterSnow.getModelSnowLayer();
            IBlockState iblockstate = BetterSnow.getStateSnowLayer();
            this.renderModelStandard(p_renderModelStandard_1_, ibakedmodel, iblockstate.getBlock(), iblockstate, p_renderModelStandard_5_, p_renderModelStandard_6_, true);
        }

        return flag;
    }

    private void renderModelAmbientOcclusionQuads(IBlockAccess p_renderModelAmbientOcclusionQuads_1_, Block p_renderModelAmbientOcclusionQuads_2_, BlockPos p_renderModelAmbientOcclusionQuads_3_, WorldRenderer p_renderModelAmbientOcclusionQuads_4_, List p_renderModelAmbientOcclusionQuads_5_, RenderEnv p_renderModelAmbientOcclusionQuads_6_) {
        float[] afloat = p_renderModelAmbientOcclusionQuads_6_.getQuadBounds();
        BitSet bitset = p_renderModelAmbientOcclusionQuads_6_.getBoundsFlags();
        BlockModelRenderer.AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = p_renderModelAmbientOcclusionQuads_6_.getAoFace();
        IBlockState iblockstate = p_renderModelAmbientOcclusionQuads_6_.getBlockState();
        double d0 = p_renderModelAmbientOcclusionQuads_3_.getX();
        double d1 = p_renderModelAmbientOcclusionQuads_3_.getY();
        double d2 = p_renderModelAmbientOcclusionQuads_3_.getZ();
        Block.EnumOffsetType block$enumoffsettype = p_renderModelAmbientOcclusionQuads_2_.getOffsetType();

        if (block$enumoffsettype != Block.EnumOffsetType.NONE) {
            long i = MathHelper.getPositionRandom(p_renderModelAmbientOcclusionQuads_3_);
            d0 += ((double) ((float) (i >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            d2 += ((double) ((float) (i >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;

            if (block$enumoffsettype == Block.EnumOffsetType.XYZ) {
                d1 += ((double) ((float) (i >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
            }
        }

        for (Object bakedquad0 : p_renderModelAmbientOcclusionQuads_5_) {
            BakedQuad bakedquad = (BakedQuad) bakedquad0;

            if (!p_renderModelAmbientOcclusionQuads_6_.isBreakingAnimation(bakedquad)) {
                BakedQuad bakedquad1 = bakedquad;

                if (Config.isConnectedTextures()) {
                    bakedquad = ConnectedTextures.getConnectedTexture(p_renderModelAmbientOcclusionQuads_1_, iblockstate, p_renderModelAmbientOcclusionQuads_3_, bakedquad, p_renderModelAmbientOcclusionQuads_6_);
                }

                if (bakedquad == bakedquad1 && Config.isNaturalTextures()) {
                    bakedquad = NaturalTextures.getNaturalTexture(p_renderModelAmbientOcclusionQuads_3_, bakedquad);
                }
            }

            this.fillQuadBounds(p_renderModelAmbientOcclusionQuads_2_, bakedquad.getVertexData(), bakedquad.getFace(), afloat, bitset);
            blockmodelrenderer$ambientocclusionface.updateVertexBrightness(p_renderModelAmbientOcclusionQuads_1_, p_renderModelAmbientOcclusionQuads_2_, p_renderModelAmbientOcclusionQuads_3_, bakedquad.getFace(), afloat, bitset);

            if (p_renderModelAmbientOcclusionQuads_4_.isMultiTexture()) {
                p_renderModelAmbientOcclusionQuads_4_.addVertexData(bakedquad.getVertexDataSingle());
                p_renderModelAmbientOcclusionQuads_4_.putSprite(bakedquad.getSprite());
            } else {
                p_renderModelAmbientOcclusionQuads_4_.addVertexData(bakedquad.getVertexData());
            }

            p_renderModelAmbientOcclusionQuads_4_.putBrightness4(blockmodelrenderer$ambientocclusionface.vertexBrightness[0], blockmodelrenderer$ambientocclusionface.vertexBrightness[1], blockmodelrenderer$ambientocclusionface.vertexBrightness[2], blockmodelrenderer$ambientocclusionface.vertexBrightness[3]);
            int k = CustomColors.getColorMultiplier(bakedquad, p_renderModelAmbientOcclusionQuads_2_, p_renderModelAmbientOcclusionQuads_1_, p_renderModelAmbientOcclusionQuads_3_, p_renderModelAmbientOcclusionQuads_6_);

            if (!bakedquad.hasTintIndex() && k == -1) {
                p_renderModelAmbientOcclusionQuads_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
                p_renderModelAmbientOcclusionQuads_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
                p_renderModelAmbientOcclusionQuads_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
                p_renderModelAmbientOcclusionQuads_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
            } else {
                int j;

                if (k != -1) {
                    j = k;
                } else {
                    j = p_renderModelAmbientOcclusionQuads_2_.colorMultiplier(p_renderModelAmbientOcclusionQuads_1_, p_renderModelAmbientOcclusionQuads_3_, bakedquad.getTintIndex());
                }

                if (EntityRenderer.anaglyphEnable) {
                    j = TextureUtil.anaglyphColor(j);
                }

                float f = (float) (j >> 16 & 255) / 255.0F;
                float f1 = (float) (j >> 8 & 255) / 255.0F;
                float f2 = (float) (j & 255) / 255.0F;
                p_renderModelAmbientOcclusionQuads_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f2, 4);
                p_renderModelAmbientOcclusionQuads_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f2, 3);
                p_renderModelAmbientOcclusionQuads_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f2, 2);
                p_renderModelAmbientOcclusionQuads_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f2, 1);
            }

            p_renderModelAmbientOcclusionQuads_4_.putPosition(d0, d1, d2);
        }
    }

    private void fillQuadBounds(Block blockIn, int[] vertexData, EnumFacing facingIn, float[] quadBounds, BitSet boundsFlags) {
        float f = 32.0F;
        float f1 = 32.0F;
        float f2 = 32.0F;
        float f3 = -32.0F;
        float f4 = -32.0F;
        float f5 = -32.0F;
        int i = vertexData.length / 4;

        for (int j = 0; j < 4; ++j) {
            float f6 = Float.intBitsToFloat(vertexData[j * i]);
            float f7 = Float.intBitsToFloat(vertexData[j * i + 1]);
            float f8 = Float.intBitsToFloat(vertexData[j * i + 2]);
            f = Math.min(f, f6);
            f1 = Math.min(f1, f7);
            f2 = Math.min(f2, f8);
            f3 = Math.max(f3, f6);
            f4 = Math.max(f4, f7);
            f5 = Math.max(f5, f8);
        }

        if (quadBounds != null) {
            quadBounds[EnumFacing.WEST.getIndex()] = f;
            quadBounds[EnumFacing.EAST.getIndex()] = f3;
            quadBounds[EnumFacing.DOWN.getIndex()] = f1;
            quadBounds[EnumFacing.UP.getIndex()] = f4;
            quadBounds[EnumFacing.NORTH.getIndex()] = f2;
            quadBounds[EnumFacing.SOUTH.getIndex()] = f5;
            quadBounds[EnumFacing.WEST.getIndex() + EnumFacing.VALUES.length] = 1.0F - f;
            quadBounds[EnumFacing.EAST.getIndex() + EnumFacing.VALUES.length] = 1.0F - f3;
            quadBounds[EnumFacing.DOWN.getIndex() + EnumFacing.VALUES.length] = 1.0F - f1;
            quadBounds[EnumFacing.UP.getIndex() + EnumFacing.VALUES.length] = 1.0F - f4;
            quadBounds[EnumFacing.NORTH.getIndex() + EnumFacing.VALUES.length] = 1.0F - f2;
            quadBounds[EnumFacing.SOUTH.getIndex() + EnumFacing.VALUES.length] = 1.0F - f5;
        }

        switch (BlockModelRenderer.BlockModelRenderer$1.field_178290_a[facingIn.ordinal()]) {
            case 1:
                boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
                boundsFlags.set(0, (f1 < 1.0E-4F || blockIn.isFullCube()) && f1 == f4);
                break;

            case 2:
                boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
                boundsFlags.set(0, (f4 > 0.9999F || blockIn.isFullCube()) && f1 == f4);
                break;

            case 3:
                boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
                boundsFlags.set(0, (f2 < 1.0E-4F || blockIn.isFullCube()) && f2 == f5);
                break;

            case 4:
                boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
                boundsFlags.set(0, (f5 > 0.9999F || blockIn.isFullCube()) && f2 == f5);
                break;

            case 5:
                boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
                boundsFlags.set(0, (f < 1.0E-4F || blockIn.isFullCube()) && f == f3);
                break;

            case 6:
                boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
                boundsFlags.set(0, (f3 > 0.9999F || blockIn.isFullCube()) && f == f3);
        }
    }

    private void renderModelStandardQuads(IBlockAccess p_renderModelStandardQuads_1_, Block p_renderModelStandardQuads_2_, BlockPos p_renderModelStandardQuads_3_, int p_renderModelStandardQuads_5_, boolean p_renderModelStandardQuads_6_, WorldRenderer p_renderModelStandardQuads_7_, List p_renderModelStandardQuads_8_, RenderEnv p_renderModelStandardQuads_9_) {
        BitSet bitset = p_renderModelStandardQuads_9_.getBoundsFlags();
        IBlockState iblockstate = p_renderModelStandardQuads_9_.getBlockState();
        double d0 = p_renderModelStandardQuads_3_.getX();
        double d1 = p_renderModelStandardQuads_3_.getY();
        double d2 = p_renderModelStandardQuads_3_.getZ();
        Block.EnumOffsetType block$enumoffsettype = p_renderModelStandardQuads_2_.getOffsetType();

        if (block$enumoffsettype != Block.EnumOffsetType.NONE) {
            int i = p_renderModelStandardQuads_3_.getX();
            int j = p_renderModelStandardQuads_3_.getZ();
            long k = (long) (i * 3129871) ^ (long) j * 116129781L;
            k = k * k * 42317861L + k * 11L;
            d0 += ((double) ((float) (k >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            d2 += ((double) ((float) (k >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;

            if (block$enumoffsettype == Block.EnumOffsetType.XYZ) {
                d1 += ((double) ((float) (k >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
            }
        }

        for (Object bakedquad0 : p_renderModelStandardQuads_8_) {
            BakedQuad bakedquad = (BakedQuad) bakedquad0;

            if (!p_renderModelStandardQuads_9_.isBreakingAnimation(bakedquad)) {
                BakedQuad bakedquad1 = bakedquad;

                if (Config.isConnectedTextures()) {
                    bakedquad = ConnectedTextures.getConnectedTexture(p_renderModelStandardQuads_1_, iblockstate, p_renderModelStandardQuads_3_, bakedquad, p_renderModelStandardQuads_9_);
                }

                if (bakedquad == bakedquad1 && Config.isNaturalTextures()) {
                    bakedquad = NaturalTextures.getNaturalTexture(p_renderModelStandardQuads_3_, bakedquad);
                }
            }

            if (p_renderModelStandardQuads_6_) {
                this.fillQuadBounds(p_renderModelStandardQuads_2_, bakedquad.getVertexData(), bakedquad.getFace(), null, bitset);
                p_renderModelStandardQuads_5_ = bitset.get(0) ? p_renderModelStandardQuads_2_.getMixedBrightnessForBlock(p_renderModelStandardQuads_1_, p_renderModelStandardQuads_3_.offset(bakedquad.getFace())) : p_renderModelStandardQuads_2_.getMixedBrightnessForBlock(p_renderModelStandardQuads_1_, p_renderModelStandardQuads_3_);
            }

            if (p_renderModelStandardQuads_7_.isMultiTexture()) {
                p_renderModelStandardQuads_7_.addVertexData(bakedquad.getVertexDataSingle());
                p_renderModelStandardQuads_7_.putSprite(bakedquad.getSprite());
            } else {
                p_renderModelStandardQuads_7_.addVertexData(bakedquad.getVertexData());
            }

            p_renderModelStandardQuads_7_.putBrightness4(p_renderModelStandardQuads_5_, p_renderModelStandardQuads_5_, p_renderModelStandardQuads_5_, p_renderModelStandardQuads_5_);
            int i1 = CustomColors.getColorMultiplier(bakedquad, p_renderModelStandardQuads_2_, p_renderModelStandardQuads_1_, p_renderModelStandardQuads_3_, p_renderModelStandardQuads_9_);

            if (bakedquad.hasTintIndex() || i1 != -1) {
                int l;

                if (i1 != -1) {
                    l = i1;
                } else {
                    l = p_renderModelStandardQuads_2_.colorMultiplier(p_renderModelStandardQuads_1_, p_renderModelStandardQuads_3_, bakedquad.getTintIndex());
                }

                if (EntityRenderer.anaglyphEnable) {
                    l = TextureUtil.anaglyphColor(l);
                }

                float f = (float) (l >> 16 & 255) / 255.0F;
                float f1 = (float) (l >> 8 & 255) / 255.0F;
                float f2 = (float) (l & 255) / 255.0F;
                p_renderModelStandardQuads_7_.putColorMultiplier(f, f1, f2, 4);
                p_renderModelStandardQuads_7_.putColorMultiplier(f, f1, f2, 3);
                p_renderModelStandardQuads_7_.putColorMultiplier(f, f1, f2, 2);
                p_renderModelStandardQuads_7_.putColorMultiplier(f, f1, f2, 1);
            }

            p_renderModelStandardQuads_7_.putPosition(d0, d1, d2);
        }
    }

    public void renderModelBrightnessColor(IBakedModel bakedModel, float p_178262_2_, float p_178262_3_, float p_178262_4_, float p_178262_5_) {
        for (EnumFacing enumfacing : EnumFacing.VALUES) {
            this.renderModelBrightnessColorQuads(p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_, bakedModel.getFaceQuads(enumfacing));
        }

        this.renderModelBrightnessColorQuads(p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_, bakedModel.getGeneralQuads());
    }

    public void renderModelBrightness(IBakedModel p_178266_1_, IBlockState p_178266_2_, float p_178266_3_, boolean p_178266_4_) {
        Block block = p_178266_2_.getBlock();
        block.setBlockBoundsForItemRender();
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        int i = block.getRenderColor(block.getStateForEntityRender(p_178266_2_));

        if (EntityRenderer.anaglyphEnable) {
            i = TextureUtil.anaglyphColor(i);
        }

        float f = (float) (i >> 16 & 255) / 255.0F;
        float f1 = (float) (i >> 8 & 255) / 255.0F;
        float f2 = (float) (i & 255) / 255.0F;

        if (!p_178266_4_) {
            GlStateManager.color(p_178266_3_, p_178266_3_, p_178266_3_, 1.0F);
        }

        this.renderModelBrightnessColor(p_178266_1_, p_178266_3_, f, f1, f2);
    }

    private void renderModelBrightnessColorQuads(float p_178264_1_, float p_178264_2_, float p_178264_3_, float p_178264_4_, List p_178264_5_) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        for (Object bakedquad0 : p_178264_5_) {
            BakedQuad bakedquad = (BakedQuad) bakedquad0;
            worldrenderer.begin(7, DefaultVertexFormats.ITEM);
            worldrenderer.addVertexData(bakedquad.getVertexData());

            if (bakedquad.hasTintIndex()) {
                worldrenderer.putColorRGB_F4(p_178264_2_ * p_178264_1_, p_178264_3_ * p_178264_1_, p_178264_4_ * p_178264_1_);
            } else {
                worldrenderer.putColorRGB_F4(p_178264_1_, p_178264_1_, p_178264_1_);
            }

            Vec3i vec3i = bakedquad.getFace().getDirectionVec();
            worldrenderer.putNormal((float) vec3i.getX(), (float) vec3i.getY(), (float) vec3i.getZ());
            tessellator.draw();
        }
    }

    public static float fixAoLightValue(float p_fixAoLightValue_0_) {
        return p_fixAoLightValue_0_ == 0.2F ? aoLightValueOpaque : p_fixAoLightValue_0_;
    }

    static final class BlockModelRenderer$1 {
        static final int[] field_178290_a = new int[EnumFacing.values().length];

        static {
            try {
                field_178290_a[EnumFacing.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_178290_a[EnumFacing.UP.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_178290_a[EnumFacing.NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_178290_a[EnumFacing.SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_178290_a[EnumFacing.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_178290_a[EnumFacing.EAST.ordinal()] = 6;
            } catch (NoSuchFieldError ignored) {
            }
        }
    }

    public static class AmbientOcclusionFace {
        private final float[] vertexColorMultiplier = new float[4];
        private final int[] vertexBrightness = new int[4];

        public AmbientOcclusionFace() {
        }

        public void updateVertexBrightness(IBlockAccess blockAccessIn, Block blockIn, BlockPos blockPosIn, EnumFacing facingIn, float[] quadBounds, BitSet boundsFlags) {
            BlockPos blockpos = boundsFlags.get(0) ? blockPosIn.offset(facingIn) : blockPosIn;
            BlockModelRenderer.EnumNeighborInfo blockmodelrenderer$enumneighborinfo = BlockModelRenderer.EnumNeighborInfo.getNeighbourInfo(facingIn);
            BlockPos blockpos1 = blockpos.offset(blockmodelrenderer$enumneighborinfo.corners[0]);
            BlockPos blockpos2 = blockpos.offset(blockmodelrenderer$enumneighborinfo.corners[1]);
            BlockPos blockpos3 = blockpos.offset(blockmodelrenderer$enumneighborinfo.corners[2]);
            BlockPos blockpos4 = blockpos.offset(blockmodelrenderer$enumneighborinfo.corners[3]);
            int i = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos1);
            int j = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos2);
            int k = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos3);
            int l = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos4);
            float f = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos1).getBlock().getAmbientOcclusionLightValue());
            float f1 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos2).getBlock().getAmbientOcclusionLightValue());
            float f2 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos3).getBlock().getAmbientOcclusionLightValue());
            float f3 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos4).getBlock().getAmbientOcclusionLightValue());
            boolean flag = blockAccessIn.getBlockState(blockpos1.offset(facingIn)).getBlock().isTranslucent();
            boolean flag1 = blockAccessIn.getBlockState(blockpos2.offset(facingIn)).getBlock().isTranslucent();
            boolean flag2 = blockAccessIn.getBlockState(blockpos3.offset(facingIn)).getBlock().isTranslucent();
            boolean flag3 = blockAccessIn.getBlockState(blockpos4.offset(facingIn)).getBlock().isTranslucent();
            float f4;
            int i1;

            if (!flag2 && !flag) {
                f4 = f;
                i1 = i;
            } else {
                BlockPos blockpos5 = blockpos1.offset(blockmodelrenderer$enumneighborinfo.corners[2]);
                f4 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos5).getBlock().getAmbientOcclusionLightValue());
                i1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos5);
            }

            float f5;
            int j1;

            if (!flag3 && !flag) {
                f5 = f;
                j1 = i;
            } else {
                BlockPos blockpos6 = blockpos1.offset(blockmodelrenderer$enumneighborinfo.corners[3]);
                f5 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos6).getBlock().getAmbientOcclusionLightValue());
                j1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos6);
            }

            float f6;
            int k1;

            if (!flag2 && !flag1) {
                f6 = f1;
                k1 = j;
            } else {
                BlockPos blockpos7 = blockpos2.offset(blockmodelrenderer$enumneighborinfo.corners[2]);
                f6 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos7).getBlock().getAmbientOcclusionLightValue());
                k1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos7);
            }

            float f7;
            int l1;

            if (!flag3 && !flag1) {
                f7 = f1;
                l1 = j;
            } else {
                BlockPos blockpos8 = blockpos2.offset(blockmodelrenderer$enumneighborinfo.corners[3]);
                f7 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos8).getBlock().getAmbientOcclusionLightValue());
                l1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos8);
            }

            int i2 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn);

            if (boundsFlags.get(0) || !blockAccessIn.getBlockState(blockPosIn.offset(facingIn)).getBlock().isOpaqueCube()) {
                i2 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn.offset(facingIn));
            }

            float f8 = boundsFlags.get(0) ? blockAccessIn.getBlockState(blockpos).getBlock().getAmbientOcclusionLightValue() : blockAccessIn.getBlockState(blockPosIn).getBlock().getAmbientOcclusionLightValue();
            f8 = BlockModelRenderer.fixAoLightValue(f8);
            BlockModelRenderer.VertexTranslations blockmodelrenderer$vertextranslations = BlockModelRenderer.VertexTranslations.getVertexTranslations(facingIn);

            if (boundsFlags.get(1) && blockmodelrenderer$enumneighborinfo.doNonCubicWeight) {
                float f29 = (f3 + f + f5 + f8) * 0.25F;
                float f30 = (f2 + f + f4 + f8) * 0.25F;
                float f31 = (f2 + f1 + f6 + f8) * 0.25F;
                float f32 = (f3 + f1 + f7 + f8) * 0.25F;
                float f13 = quadBounds[blockmodelrenderer$enumneighborinfo.vert0Weights[0].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert0Weights[1].shape];
                float f14 = quadBounds[blockmodelrenderer$enumneighborinfo.vert0Weights[2].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert0Weights[3].shape];
                float f15 = quadBounds[blockmodelrenderer$enumneighborinfo.vert0Weights[4].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert0Weights[5].shape];
                float f16 = quadBounds[blockmodelrenderer$enumneighborinfo.vert0Weights[6].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert0Weights[7].shape];
                float f17 = quadBounds[blockmodelrenderer$enumneighborinfo.vert1Weights[0].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert1Weights[1].shape];
                float f18 = quadBounds[blockmodelrenderer$enumneighborinfo.vert1Weights[2].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert1Weights[3].shape];
                float f19 = quadBounds[blockmodelrenderer$enumneighborinfo.vert1Weights[4].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert1Weights[5].shape];
                float f20 = quadBounds[blockmodelrenderer$enumneighborinfo.vert1Weights[6].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert1Weights[7].shape];
                float f21 = quadBounds[blockmodelrenderer$enumneighborinfo.vert2Weights[0].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert2Weights[1].shape];
                float f22 = quadBounds[blockmodelrenderer$enumneighborinfo.vert2Weights[2].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert2Weights[3].shape];
                float f23 = quadBounds[blockmodelrenderer$enumneighborinfo.vert2Weights[4].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert2Weights[5].shape];
                float f24 = quadBounds[blockmodelrenderer$enumneighborinfo.vert2Weights[6].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert2Weights[7].shape];
                float f25 = quadBounds[blockmodelrenderer$enumneighborinfo.vert3Weights[0].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert3Weights[1].shape];
                float f26 = quadBounds[blockmodelrenderer$enumneighborinfo.vert3Weights[2].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert3Weights[3].shape];
                float f27 = quadBounds[blockmodelrenderer$enumneighborinfo.vert3Weights[4].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert3Weights[5].shape];
                float f28 = quadBounds[blockmodelrenderer$enumneighborinfo.vert3Weights[6].shape] * quadBounds[blockmodelrenderer$enumneighborinfo.vert3Weights[7].shape];
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert0] = f29 * f13 + f30 * f14 + f31 * f15 + f32 * f16;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert1] = f29 * f17 + f30 * f18 + f31 * f19 + f32 * f20;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert2] = f29 * f21 + f30 * f22 + f31 * f23 + f32 * f24;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert3] = f29 * f25 + f30 * f26 + f31 * f27 + f32 * f28;
                int j2 = this.getAoBrightness(l, i, j1, i2);
                int k2 = this.getAoBrightness(k, i, i1, i2);
                int l2 = this.getAoBrightness(k, j, k1, i2);
                int i3 = this.getAoBrightness(l, j, l1, i2);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert0] = this.getVertexBrightness(j2, k2, l2, i3, f13, f14, f15, f16);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert1] = this.getVertexBrightness(j2, k2, l2, i3, f17, f18, f19, f20);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert2] = this.getVertexBrightness(j2, k2, l2, i3, f21, f22, f23, f24);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert3] = this.getVertexBrightness(j2, k2, l2, i3, f25, f26, f27, f28);
            } else {
                float f9 = (f3 + f + f5 + f8) * 0.25F;
                float f10 = (f2 + f + f4 + f8) * 0.25F;
                float f11 = (f2 + f1 + f6 + f8) * 0.25F;
                float f12 = (f3 + f1 + f7 + f8) * 0.25F;
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert0] = this.getAoBrightness(l, i, j1, i2);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert1] = this.getAoBrightness(k, i, i1, i2);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert2] = this.getAoBrightness(k, j, k1, i2);
                this.vertexBrightness[blockmodelrenderer$vertextranslations.vert3] = this.getAoBrightness(l, j, l1, i2);
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert0] = f9;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert1] = f10;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert2] = f11;
                this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert3] = f12;
            }
        }

        private int getAoBrightness(int br1, int br2, int br3, int br4) {
            if (br1 == 0) {
                br1 = br4;
            }

            if (br2 == 0) {
                br2 = br4;
            }

            if (br3 == 0) {
                br3 = br4;
            }

            return br1 + br2 + br3 + br4 >> 2 & 16711935;
        }

        private int getVertexBrightness(int b1, int b2, int b3, int b4, float w1, float w2, float w3, float w4) {
            int i = (int) ((float) (b1 >> 16 & 255) * w1 + (float) (b2 >> 16 & 255) * w2 + (float) (b3 >> 16 & 255) * w3 + (float) (b4 >> 16 & 255) * w4) & 255;
            int j = (int) ((float) (b1 & 255) * w1 + (float) (b2 & 255) * w2 + (float) (b3 & 255) * w3 + (float) (b4 & 255) * w4) & 255;
            return i << 16 | j;
        }
    }

    public enum EnumNeighborInfo {
        DOWN(new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.5F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.SOUTH}),
        UP(new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH}, 1.0F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.SOUTH}),
        NORTH(new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST}, 0.8F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST}),
        SOUTH(new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP}, 0.8F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST}),
        WEST(new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.6F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH}),
        EAST(new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.6F, true, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH}, new BlockModelRenderer.Orientation[]{BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH});

        protected final EnumFacing[] corners;
        protected final float field_178288_h;
        protected final boolean doNonCubicWeight;
        protected final BlockModelRenderer.Orientation[] vert0Weights;
        protected final BlockModelRenderer.Orientation[] vert1Weights;
        protected final BlockModelRenderer.Orientation[] vert2Weights;
        protected final BlockModelRenderer.Orientation[] vert3Weights;
        private static final BlockModelRenderer.EnumNeighborInfo[] VALUES = new BlockModelRenderer.EnumNeighborInfo[6];

        EnumNeighborInfo(EnumFacing[] p_i6_5_, float p_i6_6_, boolean p_i6_7_, BlockModelRenderer.Orientation[] p_i6_8_, BlockModelRenderer.Orientation[] p_i6_9_, BlockModelRenderer.Orientation[] p_i6_10_, BlockModelRenderer.Orientation[] p_i6_11_) {
            this.corners = p_i6_5_;
            this.field_178288_h = p_i6_6_;
            this.doNonCubicWeight = p_i6_7_;
            this.vert0Weights = p_i6_8_;
            this.vert1Weights = p_i6_9_;
            this.vert2Weights = p_i6_10_;
            this.vert3Weights = p_i6_11_;
        }

        public static BlockModelRenderer.EnumNeighborInfo getNeighbourInfo(EnumFacing facing) {
            return VALUES[facing.getIndex()];
        }

        static {
            VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
            VALUES[EnumFacing.UP.getIndex()] = UP;
            VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
            VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
            VALUES[EnumFacing.WEST.getIndex()] = WEST;
            VALUES[EnumFacing.EAST.getIndex()] = EAST;
        }
    }

    public enum Orientation {
        DOWN(EnumFacing.DOWN, false),
        UP(EnumFacing.UP, false),
        NORTH(EnumFacing.NORTH, false),
        SOUTH(EnumFacing.SOUTH, false),
        WEST(EnumFacing.WEST, false),
        EAST(EnumFacing.EAST, false),
        FLIP_DOWN(EnumFacing.DOWN, true),
        FLIP_UP(EnumFacing.UP, true),
        FLIP_NORTH(EnumFacing.NORTH, true),
        FLIP_SOUTH(EnumFacing.SOUTH, true),
        FLIP_WEST(EnumFacing.WEST, true),
        FLIP_EAST(EnumFacing.EAST, true);

        protected final int shape;

        Orientation(EnumFacing p_i8_5_, boolean p_i8_6_) {
            this.shape = p_i8_5_.getIndex() + (p_i8_6_ ? EnumFacing.values().length : 0);
        }
    }

    enum VertexTranslations {
        DOWN(0, 1, 2, 3),
        UP(2, 3, 0, 1),
        NORTH(3, 0, 1, 2),
        SOUTH(0, 1, 2, 3),
        WEST(3, 0, 1, 2),
        EAST(1, 2, 3, 0);

        private final int vert0;
        private final int vert1;
        private final int vert2;
        private final int vert3;
        private static final BlockModelRenderer.VertexTranslations[] VALUES = new BlockModelRenderer.VertexTranslations[6];

        VertexTranslations(int p_i7_5_, int p_i7_6_, int p_i7_7_, int p_i7_8_) {
            this.vert0 = p_i7_5_;
            this.vert1 = p_i7_6_;
            this.vert2 = p_i7_7_;
            this.vert3 = p_i7_8_;
        }

        public static BlockModelRenderer.VertexTranslations getVertexTranslations(EnumFacing facingIn) {
            return VALUES[facingIn.getIndex()];
        }

        static {
            VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
            VALUES[EnumFacing.UP.getIndex()] = UP;
            VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
            VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
            VALUES[EnumFacing.WEST.getIndex()] = WEST;
            VALUES[EnumFacing.EAST.getIndex()] = EAST;
        }
    }
}
