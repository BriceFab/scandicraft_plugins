package net.scandicraft.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.scandicraft.fonts.GameFontRenderer;
import net.scandicraft.gui.ui.inter.utils.Align;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;

import java.awt.*;

public class DrawUtils {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void startClip(float x1, float y1, float x2, float y2) {
        float temp;
        if (y1 > y2) {
            temp = y2;
            y2 = y1;
            y1 = temp;
        }

        GL11.glScissor((int) x1, (int) (Display.getHeight() - y2), (int) (x2 - x1), (int) (y2 - y1));
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
    }

    public static void endClip() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static void triangle(float x1, float y1, float x2, float y2, float x3, float y3, int fill) {
        GlStateManager.color(0, 0, 0);
        GL11.glColor4f(0, 0, 0, 0);

        float var11 = (float) (fill >> 24 & 255) / 255.0F;
        float var6 = (float) (fill >> 16 & 255) / 255.0F;
        float var7 = (float) (fill >> 8 & 255) / 255.0F;
        float var8 = (float) (fill & 255) / 255.0F;

        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x3, y3);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void circle(float x, float y, float radius, int fill) {
        arc(x, y, 0, 360, radius - 1, fill);
    }

    public static void ellipse(float x, float y, float w, float h, int fill) {
        arcEllipse(x, y, 0, 360, w - 1, h - 1, fill);
    }

    public static void point(float x, float y, float size, int fill) {
        GlStateManager.color(0, 0, 0);
        GL11.glColor4f(0, 0, 0, 0);

        float var11 = (float) (fill >> 24 & 255) / 255.0F;
        float var6 = (float) (fill >> 16 & 255) / 255.0F;
        float var7 = (float) (fill >> 8 & 255) / 255.0F;
        float var8 = (float) (fill & 255) / 255.0F;

        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);

        GL11.glEnable(GL11.GL_POINT_SMOOTH);
        GL11.glPointSize(size);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_POINT_SMOOTH);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void arc(float x, float y, float start, float end, float radius, int color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
        GlStateManager.color(0, 0, 0);
        GL11.glColor4f(0, 0, 0, 0);

        float temp = 0;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }

        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var6 = (float) (color >> 16 & 255) / 255.0F;
        float var7 = (float) (color >> 8 & 255) / 255.0F;
        float var8 = (float) (color & 255) / 255.0F;

        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);

        if (var11 > 0.5F) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth(2f);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            for (float i = end; i >= start; i -= (360 / 90)) {
                float ldx = (float) Math.cos(i * Math.PI / 180) * (w * 1.001f);
                float ldy = (float) Math.sin(i * Math.PI / 180) * (h * 1.001f);
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        for (float i = end; i >= start; i -= (360 / 90)) {
            float ldx = (float) Math.cos(i * Math.PI / 180) * w;
            float ldy = (float) Math.sin(i * Math.PI / 180) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void polygon(float x, float y, float[] xPoints, float[] yPoints, int fill) {
        GlStateManager.color(0, 0, 0);
        GL11.glColor4f(0, 0, 0, 0);

        float var11 = (float) (fill >> 24 & 255) / 255.0F;
        float var6 = (float) (fill >> 16 & 255) / 255.0F;
        float var7 = (float) (fill >> 8 & 255) / 255.0F;
        float var8 = (float) (fill & 255) / 255.0F;

        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        for (int i = xPoints.length - 1; i >= 0; i--) {
            GL11.glVertex2f(x + xPoints[i], y + yPoints[i]);
        }
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void rectTexture(float x, float y, float w, float h, Texture texture, int color) {
        if (texture == null) {
            return;
        }
        GlStateManager.color(0, 0, 0);
        GL11.glColor4f(0, 0, 0, 0);

        x = Math.round(x);
        w = Math.round(w);
        y = Math.round(y);
        h = Math.round(h);

        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var6 = (float) (color >> 16 & 255) / 255.0F;
        float var7 = (float) (color >> 8 & 255) / 255.0F;
        float var8 = (float) (color & 255) / 255.0F;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        texture.bind();

        float tw = (w / texture.getTextureWidth()) / (w / texture.getImageWidth());
        float th = (h / texture.getTextureHeight()) / (h / texture.getImageHeight());

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0, th);
        GL11.glVertex2f(x, y + h);
        GL11.glTexCoord2f(tw, th);
        GL11.glVertex2f(x + w, y + h);
        GL11.glTexCoord2f(tw, 0);
        GL11.glVertex2f(x + w, y);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void rectTexture(float x, float y, float w, float h, Texture texture, float opacity) {
        rectTexture(x, y, w, h, texture, ColorsUtils.reAlpha(Color.WHITE.getRGB(), opacity));
    }

    public static void rectTexture(float x, float y, float w, float h, Texture texture) {
        rectTexture(x, y, w, h, texture, -1);
    }

    public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_, int color) {
        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        GlStateManager.color(r, g, b, a);

        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) p_147046_0_, (float) p_147046_1_, 50.0F);
        GlStateManager.scale((float) (-p_147046_2_), (float) p_147046_2_, (float) p_147046_2_);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((double) (0 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        p_147046_5_.renderYawOffset = (float) Math.atan((double) (0 / 40.0F)) * 20.0F;
        p_147046_5_.rotationYaw = (float) Math.atan((double) (p_147046_3_ / 40.0F)) * 40.0F;
        p_147046_5_.rotationPitch = -((float) Math.atan((double) (p_147046_4_ / 40.0F))) * 20.0F;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
        var11.setPlayerViewY(180.0F);
        var11.setRenderShadow(false);
        var11.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        var11.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.disableDepth();
        GlStateManager.disableColorMaterial();
    }

    public static void loader(float x, float y, float gap, int color) {
        float rot = (System.nanoTime() / 5000000f) % 360f;
        for (int i = 0; i < 360; i += 360 / 7f) {
            float xx = (float) Math.cos((i + rot) * Math.PI / 180) * gap;
            float yy = (float) Math.sin((i + rot) * Math.PI / 180) * gap;
            circle(x + xx, y + yy, gap / 4, color);
        }
    }

    public static void string(GameFontRenderer font, float x, float y, String text, int color) {
        string(font, x, y, text, color, Align.L, Align.T, false);
    }

    public static void stringShadow(GameFontRenderer font, float x, float y, String text, int color) {
        stringShadow(font, x, y, text, color, Align.L, Align.T);
    }

    public static void stringShadow(GameFontRenderer font, float x, float y, String text, int color, Align hAlign, Align vAlign) {
        float opacity = Math.max((color >> 24 & 255) / 255f, 0.05f);
        string(font, x + 1, y + 1, text, ColorsUtils.reAlpha(0xFF000000, opacity), hAlign, vAlign, true);
        string(font, x, y, text, ColorsUtils.reAlpha(color, opacity), hAlign, vAlign, false);
    }

    public static void string(GameFontRenderer font, float x, float y, String text, int color, Align hAlign, Align vAlign) {
        string(font, x, y, text, color, hAlign, vAlign, false);
    }

    public static void string(FontRenderer font, float x, float y, String text, int color, Align hAlign, Align vAlign, boolean shadow) {
        GlStateManager.color(0, 0, 0);
        GL11.glColor4f(0, 0, 0, 0);

        int offsetX = 0;
        int offsetY = 0;

        switch (hAlign) {
            case C:
                offsetX = -font.getStringWidth(text) / 2;
                break;
            case R:
                offsetX = -font.getStringWidth(text);
                break;
            default:
                break;
        }

        switch (vAlign) {
            case C:
                offsetY = -font.FONT_HEIGHT / 2;
                break;
            case B:
                offsetY = -font.FONT_HEIGHT;
                break;
            default:
                break;
        }

        mc.getTextureManager().bindTexture(mc.fontRendererObj.locationFontTextureBase);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        TextureImpl.bindNone();
        font.drawString(text, Math.round(x + offsetX), Math.round(y + offsetY), color, shadow);
        GlStateManager.disableBlend();
    }
}
