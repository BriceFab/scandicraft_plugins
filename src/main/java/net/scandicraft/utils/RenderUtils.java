package net.scandicraft.utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scandicraft.MinecraftInstance;
import net.scandicraft.blocks.BlockUtils;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector3d;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;
import static net.minecraft.client.renderer.RenderGlobal.drawSelectionBoundingBox;
import static org.lwjgl.opengl.GL11.*;

@SideOnly(Side.CLIENT)
public final class RenderUtils extends MinecraftInstance {

    private static final Map<Integer, Boolean> glCapMap = new HashMap<>();

    public static int deltaTime;

    public static void drawBlockBox(final BlockPos blockPos, final Color color, final boolean outline) {
        final RenderManager renderManager = mc.getRenderManager();
        final Timer timer = mc.timer;

        final double x = blockPos.getX() - renderManager.renderPosX;
        final double y = blockPos.getY() - renderManager.renderPosY;
        final double z = blockPos.getZ() - renderManager.renderPosZ;

        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        final Block block = BlockUtils.getBlock(blockPos);

        if (block != null) {
            final EntityPlayer player = mc.thePlayer;

            final double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) timer.renderPartialTicks;
            final double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) timer.renderPartialTicks;
            final double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) timer.renderPartialTicks;
            axisAlignedBB = block.getSelectedBoundingBox(mc.theWorld, blockPos)
                    .expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D)
                    .offset(-posX, -posY, -posZ);
        }

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        enableGlCap(GL_BLEND);
        disableGlCap(GL_TEXTURE_2D, GL_DEPTH_TEST);
        glDepthMask(false);

        glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : outline ? 26 : 35);
        drawFilledBox(axisAlignedBB);

        if (outline) {
            glLineWidth(1F);
            enableGlCap(GL_LINE_SMOOTH);
            glColor(color);
            drawSelectionBoundingBox(axisAlignedBB);
        }

        GlStateManager.resetColor();
        glDepthMask(true);
        resetCaps();
    }

    public static void drawEntityBox(final Entity entity, final Color color, final boolean outline) {
        final RenderManager renderManager = mc.getRenderManager();
        final Timer timer = mc.timer;

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        enableGlCap(GL_BLEND);
        disableGlCap(GL_TEXTURE_2D, GL_DEPTH_TEST);
        glDepthMask(false);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks
                - renderManager.renderPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks
                - renderManager.renderPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks
                - renderManager.renderPosZ;

        final AxisAlignedBB entityBox = entity.getEntityBoundingBox();
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
                entityBox.minX - entity.posX + x - 0.05D,
                entityBox.minY - entity.posY + y,
                entityBox.minZ - entity.posZ + z - 0.05D,
                entityBox.maxX - entity.posX + x + 0.05D,
                entityBox.maxY - entity.posY + y + 0.15D,
                entityBox.maxZ - entity.posZ + z + 0.05D
        );

        if (outline) {
            glLineWidth(1F);
            enableGlCap(GL_LINE_SMOOTH);
            glColor(color.getRed(), color.getGreen(), color.getBlue(), 95);
            drawSelectionBoundingBox(axisAlignedBB);
        }

        glColor(color.getRed(), color.getGreen(), color.getBlue(), outline ? 26 : 35);
        drawFilledBox(axisAlignedBB);
        GlStateManager.resetColor();
        glDepthMask(true);
        resetCaps();
    }

    public static void drawAxisAlignedBB(final AxisAlignedBB axisAlignedBB, final Color color) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glLineWidth(2F);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glColor(color);
        drawFilledBox(axisAlignedBB);
        GlStateManager.resetColor();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public static void drawPlatform(final double y, final Color color, final double size) {
        final RenderManager renderManager = mc.getRenderManager();
        final double renderY = y - renderManager.renderPosY;

        drawAxisAlignedBB(new AxisAlignedBB(size, renderY + 0.02D, size, -size, renderY, -size), color);
    }

    public static void drawPlatform(final Entity entity, final Color color) {
        final RenderManager renderManager = mc.getRenderManager();
        final Timer timer = mc.timer;

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks
                - renderManager.renderPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks
                - renderManager.renderPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks
                - renderManager.renderPosZ;

        final AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox()
                .offset(-entity.posX, -entity.posY, -entity.posZ)
                .offset(x, y, z);

        drawAxisAlignedBB(
                new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.maxY + 0.2, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY + 0.26, axisAlignedBB.maxZ),
                color
        );
    }

    public static void drawFilledBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawRect(final float x, final float y, final float x2, final float y2, final int color) {
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glPushMatrix();
        glColor(color);
        glBegin(GL_QUADS);
        glVertex2d(x2, y);
        glVertex2d(x, y);
        glVertex2d(x, y2);
        glVertex2d(x2, y2);
        glEnd();
        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
    }

    public static void drawRect(final float x, final float y, final float x2, final float y2, final Color color) {
        drawRect(x, y, x2, y2, color.getRGB());
    }

    public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float width,
                                        final int color1, final int color2) {
        drawRect(x, y, x2, y2, color2);
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glPushMatrix();
        glColor(color1);
        glLineWidth(width);
        glBegin(1);
        glVertex2d(x, y);
        glVertex2d(x, y2);
        glVertex2d(x2, y2);
        glVertex2d(x2, y);
        glVertex2d(x, y);
        glVertex2d(x2, y);
        glVertex2d(x, y2);
        glVertex2d(x2, y2);
        glEnd();
        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
    }

    public static void drawLoadingCircle(float x, float y) {
        for (int i = 0; i < 4; i++) {
            int rot = (int) ((System.nanoTime() / 5000000 * i) % 360);
            drawCircle(x, y, i * 10, rot - 180, rot);
        }
    }

    public static void drawCircle(float x, float y, float radius, int start, int end) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
        glColor(Color.WHITE);

        glEnable(GL_LINE_SMOOTH);
        glLineWidth(2F);
        glBegin(GL_LINE_STRIP);
        for (float i = end; i >= start; i -= (360 / 90))
            glVertex2f((float) (x + (cos(i * PI / 180) * (radius * 1.001F))), (float) (y + (sin(i * PI / 180) * (radius * 1.001F))));
        glEnd();
        glDisable(GL_LINE_SMOOTH);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawFilledCircle(final int xx, final int yy, final float radius, final Color color) {
        int sections = 50;
        double dAngle = 2 * Math.PI / sections;
        float x, y;

        glPushMatrix();
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glBegin(GL_TRIANGLE_FAN);

        for (int i = 0; i < sections; i++) {
            x = (float) (radius * Math.sin((i * dAngle)));
            y = (float) (radius * Math.cos((i * dAngle)));

            glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
            glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0, 0, 0);

        glEnd();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glPopMatrix();
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glDepthMask(false);
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
    }

    public static void glColor(final int red, final int green, final int blue, final int alpha) {
        GlStateManager.color(red / 255F, green / 255F, blue / 255F, alpha / 255F);
    }

    public static void glColor(final Color color) {
        final float red = color.getRed() / 255F;
        final float green = color.getGreen() / 255F;
        final float blue = color.getBlue() / 255F;
        final float alpha = color.getAlpha() / 255F;

        GlStateManager.color(red, green, blue, alpha);
    }

    private static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255F;
        final float red = (hex >> 16 & 0xFF) / 255F;
        final float green = (hex >> 8 & 0xFF) / 255F;
        final float blue = (hex & 0xFF) / 255F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void draw2D(final EntityLivingBase entity, final double posX, final double posY, final double posZ, final int color, final int backgroundColor) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        glNormal3f(0F, 0F, 0F);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0F, 1F, 0F);
        GlStateManager.scale(-0.1D, -0.1D, 0.1D);
        glDisable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);

        drawRect(-7F, 2F, -4F, 3F, color);
        drawRect(4F, 2F, 7F, 3F, color);
        drawRect(-7F, 0.5F, -6F, 3F, color);
        drawRect(6F, 0.5F, 7F, 3F, color);

        drawRect(-7F, 3F, -4F, 3.3F, backgroundColor);
        drawRect(4F, 3F, 7F, 3.3F, backgroundColor);
        drawRect(-7.3F, 0.5F, -7F, 3.3F, backgroundColor);
        drawRect(7F, 0.5F, 7.3F, 3.3F, backgroundColor);

        GlStateManager.translate(0, 21 + -(entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) * 12, 0);

        drawRect(4F, -20F, 7F, -19F, color);
        drawRect(-7F, -20F, -4F, -19F, color);
        drawRect(6F, -20F, 7F, -17.5F, color);
        drawRect(-7F, -20F, -6F, -17.5F, color);

        drawRect(7F, -20F, 7.3F, -17.5F, backgroundColor);
        drawRect(-7.3F, -20F, -7F, -17.5F, backgroundColor);
        drawRect(4F, -20.3F, 7.3F, -20F, backgroundColor);
        drawRect(-7.3F, -20.3F, -4F, -20F, backgroundColor);

        // Stop render
        glEnable(GL_DEPTH_TEST);
        GlStateManager.popMatrix();
    }

    public static void draw2D(final BlockPos blockPos, final int color, final int backgroundColor) {
        final RenderManager renderManager = mc.getRenderManager();

        final double posX = (blockPos.getX() + 0.5) - renderManager.renderPosX;
        final double posY = blockPos.getY() - renderManager.renderPosY;
        final double posZ = (blockPos.getZ() + 0.5) - renderManager.renderPosZ;

        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        glNormal3f(0F, 0F, 0F);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0F, 1F, 0F);
        GlStateManager.scale(-0.1D, -0.1D, 0.1D);
        setGlCap(GL_DEPTH_TEST, false);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);

        drawRect(-7F, 2F, -4F, 3F, color);
        drawRect(4F, 2F, 7F, 3F, color);
        drawRect(-7F, 0.5F, -6F, 3F, color);
        drawRect(6F, 0.5F, 7F, 3F, color);

        drawRect(-7F, 3F, -4F, 3.3F, backgroundColor);
        drawRect(4F, 3F, 7F, 3.3F, backgroundColor);
        drawRect(-7.3F, 0.5F, -7F, 3.3F, backgroundColor);
        drawRect(7F, 0.5F, 7.3F, 3.3F, backgroundColor);

        GlStateManager.translate(0, 9, 0);

        drawRect(4F, -20F, 7F, -19F, color);
        drawRect(-7F, -20F, -4F, -19F, color);
        drawRect(6F, -20F, 7F, -17.5F, color);
        drawRect(-7F, -20F, -6F, -17.5F, color);

        drawRect(7F, -20F, 7.3F, -17.5F, backgroundColor);
        drawRect(-7.3F, -20F, -7F, -17.5F, backgroundColor);
        drawRect(4F, -20.3F, 7.3F, -20F, backgroundColor);
        drawRect(-7.3F, -20.3F, -4F, -20F, backgroundColor);

        // Stop render
        resetCaps();
        GlStateManager.popMatrix();
    }

    public static void renderNameTag(final String string, final double x, final double y, final double z) {
        final RenderManager renderManager = mc.getRenderManager();

        glPushMatrix();
        glTranslated(x - renderManager.renderPosX, y - renderManager.renderPosY, z - renderManager.renderPosZ);
        glNormal3f(0F, 1F, 0F);
        glRotatef(-mc.getRenderManager().playerViewY, 0F, 1F, 0F);
        glRotatef(mc.getRenderManager().playerViewX, 1F, 0F, 0F);
        glScalef(-0.05F, -0.05F, 0.05F);
        setGlCap(GL_LIGHTING, false);
        setGlCap(GL_DEPTH_TEST, false);
        setGlCap(GL_BLEND, true);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        final int width = mc.fontRendererObj.getStringWidth(string) / 2;

        Gui.drawRect(-width - 1, -1, width + 1, mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);
        mc.fontRendererObj.drawString(string, -width, 1.5F, Color.WHITE.getRGB(), true);

        resetCaps();
        glColor4f(1F, 1F, 1F, 1F);
        glPopMatrix();
    }

    public static void drawLine(final double x, final double y, final double x1, final double y1, final float width) {
        glDisable(GL_TEXTURE_2D);
        glLineWidth(width);
        glBegin(GL_LINES);
        glVertex2d(x, y);
        glVertex2d(x1, y1);
        glEnd();
        glEnable(GL_TEXTURE_2D);
    }

    public static void makeScissorBox(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        final int factor = scaledResolution.getScaleFactor();
        glScissor((int) (x * factor), (int) ((scaledResolution.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

    /**
     * GL CAP MANAGER
     * <p>
     * TODO: Remove gl cap manager and replace by something better
     */

    public static void resetCaps() {
        glCapMap.forEach(RenderUtils::setGlState);
    }

    public static void enableGlCap(final int cap) {
        setGlCap(cap, true);
    }

    public static void enableGlCap(final int... caps) {
        for (final int cap : caps)
            setGlCap(cap, true);
    }

    public static void disableGlCap(final int cap) {
        setGlCap(cap, true);
    }

    public static void disableGlCap(final int... caps) {
        for (final int cap : caps)
            setGlCap(cap, false);
    }

    public static void setGlCap(final int cap, final boolean state) {
        glCapMap.put(cap, glGetBoolean(cap));
        setGlState(cap, state);
    }

    public static void setGlState(final int cap, final boolean state) {
        if (state)
            glEnable(cap);
        else
            glDisable(cap);
    }

    public static void box(Entity e, int color) {
        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var6 = (float) (color >> 16 & 255) / 255.0F;
        float var7 = (float) (color >> 8 & 255) / 255.0F;
        float var8 = (float) (color & 255) / 255.0F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer t = var9.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GlStateManager.color(var6, var7, var8, var11);

        double[] pos = entityRenderPos(e);

        AxisAlignedBB bb = e.getEntityBoundingBox();
        AxisAlignedBB aa = new AxisAlignedBB(bb.minX - e.posX + pos[0], bb.minY - e.posY + pos[1], bb.minZ - e.posZ + pos[2], bb.maxX - e.posX + pos[0], bb.maxY - e.posY + pos[1], bb.maxZ - e.posZ + pos[2]);

        int draw = 7;
        cubeFill(draw, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[]{0, -e.rotationYaw, 0}, new boolean[]{true, true, true, true, true, true});

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void boxOutline(Entity e, int color) {
        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var6 = (float) (color >> 16 & 255) / 255.0F;
        float var7 = (float) (color >> 8 & 255) / 255.0F;
        float var8 = (float) (color & 255) / 255.0F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer t = var9.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1f);

        GlStateManager.color(var6, var7, var8, var11);

        double[] pos = entityRenderPos(e);

        AxisAlignedBB bb = e.getEntityBoundingBox();
        AxisAlignedBB aa = new AxisAlignedBB(bb.minX - e.posX + pos[0], bb.minY - e.posY + pos[1], bb.minZ - e.posZ + pos[2], bb.maxX - e.posX + pos[0], bb.maxY - e.posY + pos[1], bb.maxZ - e.posZ + pos[2]);

        int draw = 1;
        cubeOutline(draw, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[]{0, -e.rotationYaw, 0}, new boolean[]{true, true, true, true, true, true});

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void boxFilled(Entity e, int outline, int fill) {
        box(e, outline);
        boxOutline(e, fill);
    }

    public static void block(BlockPos bp, int color, int drawMode, boolean[] faces) {
        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var6 = (float) (color >> 16 & 255) / 255.0F;
        float var7 = (float) (color >> 8 & 255) / 255.0F;
        float var8 = (float) (color & 255) / 255.0F;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1f);

        GlStateManager.color(var6, var7, var8, var11);

        AxisAlignedBB aa = new AxisAlignedBB(bp, bp.offset(EnumFacing.SOUTH).offset(EnumFacing.EAST).offset(EnumFacing.UP)).offset(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);

        if (drawMode == 7) {
            cubeFill(drawMode, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[]{0, 0, 0}, faces);
        } else {
            cubeOutline(drawMode, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[]{0, 0, 0}, faces);
        }

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void blockBB(Block b, BlockPos bp, double expand, int color, int drawMode, boolean[] faces) {
        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var6 = (float) (color >> 16 & 255) / 255.0F;
        float var7 = (float) (color >> 8 & 255) / 255.0F;
        float var8 = (float) (color & 255) / 255.0F;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1f);

        GlStateManager.color(var6, var7, var8, var11);

        AxisAlignedBB aa = b.getSelectedBoundingBox(mc.theWorld, bp).offset(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ).expand(expand, expand, expand);

        if (drawMode == 7) {
            cubeFill(drawMode, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[]{0, 0, 0}, faces);
        } else {
            cubeOutline(drawMode, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[]{0, 0, 0}, faces);
        }

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void blockBox(BlockPos bp, int outline, int fill, boolean[] faces) {
        block(bp, fill, 1, faces);
        block(bp, outline, 7, faces);
    }

    public static void blockBoxBB(Block b, BlockPos bp, double expand, int outline, int fill, boolean[] faces) {
        blockBB(b, bp, expand, fill, 1, faces);
        blockBB(b, bp, expand, outline, 7, faces);
    }

    public static void bbox(Vec3 pos1, Vec3 pos2, int outline, int fill) {
        float ovar11 = (float) (outline >> 24 & 255) / 255.0F;
        float ovar6 = (float) (outline >> 16 & 255) / 255.0F;
        float ovar7 = (float) (outline >> 8 & 255) / 255.0F;
        float ovar8 = (float) (outline & 255) / 255.0F;

        float fvar11 = (float) (fill >> 24 & 255) / 255.0F;
        float fvar6 = (float) (fill >> 16 & 255) / 255.0F;
        float fvar7 = (float) (fill >> 8 & 255) / 255.0F;
        float fvar8 = (float) (fill & 255) / 255.0F;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1f);

        double[] p1 = renderPos(pos1);
        double[] p2 = renderPos(pos2);

        GlStateManager.color(fvar6, fvar7, fvar8, fvar11);
        cubeFill(7, p1[0], p1[1], p1[2], p2[0] + 1, p2[1] + 1, p2[2] + 1, new float[]{0, 0, 0}, new boolean[]{true, true, true, true, true, true});

        GlStateManager.color(ovar6, ovar7, ovar8, ovar11);
        cubeOutline(1, p1[0], p1[1], p1[2], p2[0] + 1, p2[1] + 1, p2[2] + 1, new float[]{0, 0, 0}, new boolean[]{true, true, true, true, true, true});

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void tracer(Entity from, Entity to, int color) {
        GlStateManager.loadIdentity();
        mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);

        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var6 = (float) (color >> 16 & 255) / 255.0F;
        float var7 = (float) (color >> 8 & 255) / 255.0F;
        float var8 = (float) (color & 255) / 255.0F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer t = var9.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1f);

        GlStateManager.color(var6, var7, var8, var11);

        double[] pos = entityRenderPos(to);

        double x = pos[0];
        double y = pos[1] + to.getEyeHeight();
        double z = pos[2];

        int draw = 1;
        t.begin(draw, DefaultVertexFormats.POSITION);
        t.pos(0, mc.thePlayer.getEyeHeight(), 0);
        t.pos(x, y, z);
        var9.draw();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void line(Vec3 from, Vec3 to, int color, float width) {
        GlStateManager.loadIdentity();
        mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);

        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var6 = (float) (color >> 16 & 255) / 255.0F;
        float var7 = (float) (color >> 8 & 255) / 255.0F;
        float var8 = (float) (color & 255) / 255.0F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer t = var9.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(width);

        GlStateManager.color(var6, var7, var8, var11);

        Minecraft mc = Minecraft.getMinecraft();

        double[] pf = renderPos(from);
        double[] pt = renderPos(to);

        int draw = 1;
        t.begin(draw, DefaultVertexFormats.POSITION);
        t.pos(pf[0], pf[1], pf[2]);
        t.pos(pt[0], pt[1], pt[2]);
        var9.draw();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void renderTrail(double x, double y, double z, double mx, double my, double mz, float drag, float waterDrag, float grav, int color, boolean renderBlockHit) {
        for (int j = 0; j < 200; j++) {
            double oldX = x;
            double oldY = y;
            double oldZ = z;

            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            line(new Vec3(x, y, z), new Vec3(x + mx, y + my, z + mz), color, 2f);
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);

            x += mx;
            y += my;
            z += mz;

            Vec3 op = new Vec3(oldX, oldY, oldZ);
            Vec3 np = new Vec3(x, y, z);

            MovingObjectPosition mop = mc.theWorld.rayTraceBlocks(op, np, false, true, true);
            if (mop != null) {
                if (mop.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK) && renderBlockHit) {
                    float a = (float) (color >> 24 & 255) / 255.0F;
                    blockBox(mop.getBlockPos(), ColorsUtils.reAlpha(color, a / 2), ColorsUtils.reAlpha(color, a), new boolean[]{true, true, true, true, true, true});
                    break;
                }
            }

            float var25 = drag;
            float var13 = grav;

            Vector3d pos = new Vector3d(x, y, z);
            float size = 0.25f;
            if (mc.theWorld.isAABBInMaterial(new AxisAlignedBB(pos.x + size, pos.y + size, pos.z + size, pos.x - size, pos.y - size, pos.z - size), Material.water)) {
                if (waterDrag == 0f) {
                    break;
                }
                var25 = waterDrag;
            }

            mx *= var25;
            my *= var25;
            mz *= var25;

            my -= var13;
        }
    }

    public static void cubeFill(int draw, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float[] rotation, boolean[] faces) {
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer t = var9.getWorldRenderer();

        GlStateManager.pushMatrix();

        GlStateManager.translate(minX + ((maxX - minX) / 2), minY, minZ + ((maxZ - minZ) / 2));
        GlStateManager.rotate(rotation[0], 1, 0, 0);
        GlStateManager.rotate(rotation[1], 0, 1, 0);
        GlStateManager.rotate(rotation[2], 0, 0, 1);

        maxX -= minX;
        maxY -= minY;
        maxZ -= minZ;

        maxX /= 2;
        maxZ /= 2;

        minX = -maxX;
        minY = 0;
        minZ = -maxZ;

        double[][][] array = new double[][][]{
                {{minX, minY, minZ}, {minX, maxY, minZ}, {maxX, maxY, minZ}, {maxX, minY, minZ}},//north
                {{minX, minY, maxZ}, {maxX, minY, maxZ}, {maxX, maxY, maxZ}, {minX, maxY, maxZ}},//south
                {{maxX, minY, minZ}, {maxX, maxY, minZ}, {maxX, maxY, maxZ}, {maxX, minY, maxZ}},//east
                {{minX, minY, minZ}, {minX, minY, maxZ}, {minX, maxY, maxZ}, {minX, maxY, minZ}},//west
                {{minX, maxY, minZ}, {minX, maxY, maxZ}, {maxX, maxY, maxZ}, {maxX, maxY, minZ}},//up
                {{minX, minY, minZ}, {maxX, minY, minZ}, {maxX, minY, maxZ}, {minX, minY, maxZ}},//down
        };

        t.begin(draw, DefaultVertexFormats.POSITION);
        for (int i = 0; i < array.length; i++) {
            if (faces[i]) {
                for (int j = 0; j < array[i].length; j++) {
                    t.pos(array[i][j][0], array[i][j][1], array[i][j][2]);
                }
                for (int j = array[i].length - 1; j >= 0; j--) {
                    t.pos(array[i][j][0], array[i][j][1], array[i][j][2]);
                }
            }
        }
        var9.draw();

        GlStateManager.popMatrix();
    }

    public static void cubeOutline(int draw, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float[] rotation, boolean[] faces) {
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer t = var9.getWorldRenderer();

        GlStateManager.pushMatrix();

        GlStateManager.translate(minX + ((maxX - minX) / 2), minY, minZ + ((maxZ - minZ) / 2));
        GlStateManager.rotate(rotation[0], 1, 0, 0);
        GlStateManager.rotate(rotation[1], 0, 1, 0);
        GlStateManager.rotate(rotation[2], 0, 0, 1);

        maxX -= minX;
        maxY -= minY;
        maxZ -= minZ;

        maxX /= 2;
        maxZ /= 2;

        minX = -maxX;
        minY = 0;
        minZ = -maxZ;

        //n s e w t b
        boolean[] vertices = new boolean[]{
                (!faces[0] || !faces[2] ? false : true),
                (!faces[0] || !faces[3] ? false : true),
                (!faces[1] || !faces[2] ? false : true),
                (!faces[1] || !faces[3] ? false : true),
                (!faces[0] || !faces[5] ? false : true),
                (!faces[1] || !faces[5] ? false : true),
                (!faces[2] || !faces[5] ? false : true),
                (!faces[3] || !faces[5] ? false : true),
                (!faces[0] || !faces[4] ? false : true),
                (!faces[1] || !faces[4] ? false : true),
                (!faces[2] || !faces[4] ? false : true),
                (!faces[3] || !faces[4] ? false : true),
        };

        double[][][] array = new double[][][]{
                {{maxX, minY, minZ}, {maxX, maxY, minZ}},//northeast bottom->top
                {{minX, minY, minZ}, {minX, maxY, minZ}},//northwest bottom->top
                {{maxX, minY, maxZ}, {maxX, maxY, maxZ}},//southeast bottom->top
                {{minX, minY, maxZ}, {minX, maxY, maxZ}},//southwest bottom->top
                {{minX, minY, minZ}, {maxX, minY, minZ}},//bottom north
                {{minX, minY, maxZ}, {maxX, minY, maxZ}},//bottom south
                {{maxX, minY, minZ}, {maxX, minY, maxZ}},//bottom east
                {{minX, minY, minZ}, {minX, minY, maxZ}},//bottom west
                {{minX, maxY, minZ}, {maxX, maxY, minZ}},//top north
                {{minX, maxY, maxZ}, {maxX, maxY, maxZ}},//top south
                {{maxX, maxY, minZ}, {maxX, maxY, maxZ}},//top east
                {{minX, maxY, minZ}, {minX, maxY, maxZ}},//top west
        };

        t.begin(draw, DefaultVertexFormats.POSITION);
        for (int i = 0; i < array.length; i++) {
            if (vertices[i]) {
                for (int j = 0; j < array[i].length; j++) {
                    t.pos(array[i][j][0], array[i][j][1], array[i][j][2]);
                }
            }
        }
        var9.draw();

        GlStateManager.popMatrix();
    }

    public static double[] entityRenderPos(Entity e) {
        float p_147936_2_ = mc.timer.renderPartialTicks;

        double x = (e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) p_147936_2_) - mc.getRenderManager().viewerPosX;
        double y = (e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) p_147936_2_) - mc.getRenderManager().viewerPosY;
        double z = (e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) p_147936_2_) - mc.getRenderManager().viewerPosZ;

        return new double[]{x, y, z};
    }

    public static double[] entityWorldPos(Entity e) {
        float p_147936_2_ = mc.timer.renderPartialTicks;

        double x = (e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) p_147936_2_);
        double y = (e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) p_147936_2_);
        double z = (e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) p_147936_2_);

        return new double[]{x, y, z};
    }

    public static double[] renderPos(Vec3 vec) {
        float p_147936_2_ = mc.timer.renderPartialTicks;

        double x = vec.xCoord - mc.getRenderManager().viewerPosX;
        double y = vec.yCoord - mc.getRenderManager().viewerPosY;
        double z = vec.zCoord - mc.getRenderManager().viewerPosZ;

        return new double[]{x, y, z};
    }

    public static boolean transparentEntity(int id) {
        return id == -1 || id == -2;
    }

    public static String hsvToRgb(float hue, float saturation, float value) {
        int h = (int) (hue * 6);
        float f = hue * 6 - h;
        float p = value * (1 - saturation);
        float q = value * (1 - f * saturation);
        float t = value * (1 - (1 - f) * saturation);

        switch (h) {
            case 0:
                return rgbToString(value, t, p);
            case 1:
                return rgbToString(q, value, p);
            case 2:
                return rgbToString(p, value, t);
            case 3:
                return rgbToString(p, q, value);
            case 4:
                return rgbToString(t, p, value);
            case 5:
                return rgbToString(value, p, q);
            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }
    }

    public static String rgbToString(float r, float g, float b) {
        String rs = Integer.toHexString((int) (r * 256));
        String gs = Integer.toHexString((int) (g * 256));
        String bs = Integer.toHexString((int) (b * 256));
        return rs + gs + bs;
    }

    public static Color getRainbow(float fade) {
        float hue = (float) (System.nanoTime() / 5000000000f) % 1;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1f, 1f)), 16);
        Color c = new Color((int) color);
        return new Color((c.getRed() / 255f) * fade, (c.getGreen() / 255f) * fade, (c.getBlue() / 255f) * fade, c.getAlpha() / 255f);
    }

    public static void enableLighting() {
        GlStateManager.enableLight(0);
        GlStateManager.enableLight(1);
    }

    public static void disableLighting() {
        GlStateManager.disableLight(0);
        GlStateManager.disableLight(1);
    }
}