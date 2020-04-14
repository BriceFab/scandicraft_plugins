package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class RenderEntityItem extends Render<EntityItem> {
    private final RenderItem itemRenderer;
    private Random random = new Random();

    public RenderEntityItem(RenderManager renderManagerIn, RenderItem itemRendererIn) {
        super(renderManagerIn);
        this.itemRenderer = itemRendererIn;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) {
        ItemStack itemstack = itemIn.getEntityItem();
        Item item = itemstack.getItem();
        Block block = Block.getBlockFromItem(item);
        Random ran = new Random();
        float ranF = ran.nextFloat() * (5 - 1) + 1;

        if (item == null) {
            return 0;
        } else {
            boolean flag = p_177077_9_.isGui3d();
            int i = this.getModelCount(itemstack);
            float f = 0.25F;
            // float f1 = MathHelper.sin(((float)itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
            // float f2 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
            if (block != null && (block != Blocks.torch || block != Blocks.redstone_torch)) {
                GlStateManager.translate((float) p_177077_2_, (float) p_177077_4_ + 0.15F, (float) p_177077_6_);
            } else {
                GlStateManager.translate((float) p_177077_2_, (float) p_177077_4_ + 0.02F, (float) p_177077_6_);
                GlStateManager.rotate(-90F, 1f, 0f, 0f);
                GlStateManager.rotate(-90F, 0f, 0f, 1f);
                GlStateManager.scale(1.5f, 1.5f, 1.5f);
            }
            if (item == Items.scandium || item == Items.pyrite || item == Items.lazurite || item == Items.dye ||
                    item == Items.diamond || item == Items.gold_ingot || item == Items.iron_ingot || item == Items.emerald || item == Items.coal || item == Items.redstone) {
                GlStateManager.scale(0.8f, 0.8f, 0.8f);
            }
            if (item == Items.enchanted_book) {
                GlStateManager.scale(0.7f, 0.7f, 0.7f);
            }
            if (item == Items.book) {
                GlStateManager.scale(0.7f, 0.7f, 0.7f);
            }
            if (item == Items.bloody_sword || item == Items.scandium_sword || item == Items.pyrite_sword || item == Items.lazurite_sword || item == Items.wooden_sword || item == Items.stone_sword || item == Items.iron_sword || item == Items.golden_sword || item == Items.diamond_sword) {
                GlStateManager.translate(0f, 0f, 0.15f);
                GlStateManager.rotate(-90f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(45f, 0.0f, 1.0f, 0.0f);
            }

           /* if (flag || this.renderManager.options != null)
            {
                float f3 = (((float)itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float)Math.PI);
                GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
            }*/

            if (!flag) {
                float f6 = -0.0F * (float) (i - 1) * 0.5F;
                float f4 = -0.0F * (float) (i - 1) * 0.5F;
                float f5 = -0.046875F * (float) (i - 1) * 0.5F;
                GlStateManager.translate(f6, f4, f5);
            }

            if (!itemIn.onGround) {
                float angle = System.currentTimeMillis() % (360 * 20) / 2F;
                GlStateManager.rotate(angle, 1F, 1F, 1F);
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return i;
        }
    }

    private int getModelCount(ItemStack stack) {
        int i = 1;

        if (stack.stackSize > 48) {
            i = 5;
        } else if (stack.stackSize > 32) {
            i = 4;
        } else if (stack.stackSize > 16) {
            i = 3;
        } else if (stack.stackSize > 1) {
            i = 2;
        }

        return i;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
        ItemStack itemstack = entity.getEntityItem();
        this.random.setSeed(187L);
        boolean flag = false;

        if (this.bindEntityTexture(entity)) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
            flag = true;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
        int i = this.func_177077_a(entity, x, y, z, partialTicks, ibakedmodel);

        for (int j = 0; j < i; ++j) {
            if (ibakedmodel.isGui3d()) {
                GlStateManager.pushMatrix();

                if (j > 0) {
                    float f = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    GlStateManager.translate(f, f1, f2);
                }

                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                this.itemRenderer.renderItem(itemstack, ibakedmodel);
                GlStateManager.popMatrix();
            } else {
                GlStateManager.pushMatrix();
                ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                this.itemRenderer.renderItem(itemstack, ibakedmodel);
                GlStateManager.popMatrix();
                float f3 = ibakedmodel.getItemCameraTransforms().ground.scale.x;
                float f4 = ibakedmodel.getItemCameraTransforms().ground.scale.y;
                float f5 = ibakedmodel.getItemCameraTransforms().ground.scale.z;
                GlStateManager.translate(0.0F * f3, 0.0F * f4, 0.046875F * f5);
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entity);

        if (flag) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityItem entity) {
        return TextureMap.locationBlocksTexture;
    }
}
