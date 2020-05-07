package net.scandicraft.mods.impl.compass;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModCompass extends ModDraggable {

    public static ScreenPosition DEFAULT_POSITION = ScreenPosition.fromAbsolutePosition(3, 3);

    private ScreenPosition pos;

    public int details = 1; //Medium

    public int width = 30;

    public int height = 22;

    public int cwidth = 180; //spacing

    public boolean background = true;

    public boolean chroma;

    public boolean shadow;

    public int tintMarker;

    public int tintDirection;

    private int offsetAll;

    private int centerX;

    private int colorMarker;

    private int colorDirection;

    @Override
    public ScreenPosition getDefaultPos() {
        return DEFAULT_POSITION;
    }

    @Override
    public int getWidth() {
        return this.cwidth - this.width * 2;
    }

    @Override
    public int getHeight() {
        return 22;
    }

    @Override
    public void render(ScreenPosition pos) {
        this.pos = pos;
        int direction = normalize((int) this.mc.thePlayer.rotationYaw);
        this.offsetAll = this.cwidth * direction / 360;
        this.centerX = pos.getAbsoluteX() + getWidth() / 2;

        if (this.background)
            Gui.drawRect(
                    this.centerX - this.width * 2,
                    pos.getAbsoluteY(),
                    this.centerX + this.width * 2,
                    pos.getAbsoluteY() + this.height,
                    -1442840576
            );
        if (!this.chroma) {
            if (this.tintMarker != 0) {
                this.colorMarker = Color.HSBtoRGB(this.tintMarker / 100.0F, 1.0F, 1.0F);
            } else {
                this.colorMarker = -1;
            }
            if (this.tintDirection != 0) {
                this.colorDirection = Color.HSBtoRGB(this.tintDirection / 100.0F, 1.0F, 1.0F);
            } else {
                this.colorDirection = -1;
            }
        } else {
            this.colorDirection = this.colorMarker = Color.HSBtoRGB((float) (System.currentTimeMillis() % 3000L) / 3000.0F, 1.0F, 1.0F);
        }
        renderMarker();
        if (this.details >= 0) {
            drawDirection("S", 0, 1.5D);
            drawDirection("W", 90, 1.5D);
            drawDirection("N", 180, 1.5D);
            drawDirection("E", 270, 1.5D);
        }
        if (this.details >= 1) {
            drawDirection("SW", 45, 1.0D);
            drawDirection("NW", 135, 1.0D);
            drawDirection("NE", 225, 1.0D);
            drawDirection("SE", 315, 1.0D);
        }
        if (this.details >= 2) {
            drawDirection("15", 15, 0.75D);
            drawDirection("30", 30, 0.75D);
            drawDirection("60", 60, 0.75D);
            drawDirection("75", 75, 0.75D);
            drawDirection("105", 105, 0.75D);
            drawDirection("120", 120, 0.75D);
            drawDirection("150", 150, 0.75D);
            drawDirection("165", 165, 0.75D);
            drawDirection("195", 195, 0.75D);
            drawDirection("210", 210, 0.75D);
            drawDirection("240", 240, 0.75D);
            drawDirection("255", 255, 0.75D);
            drawDirection("285", 285, 0.75D);
            drawDirection("300", 300, 0.75D);
            drawDirection("330", 330, 0.75D);
            drawDirection("345", 345, 0.75D);
        }
    }

    private void renderMarker() {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color((this.colorMarker >> 16 & 0xFF) / 255.0F, (this.colorMarker >> 8 & 0xFF) / 255.0F, (this.colorMarker & 0xFF) / 255.0F, 1.0F);
        worldrenderer.begin(6, DefaultVertexFormats.POSITION);
        worldrenderer.pos(this.centerX, (pos.getAbsoluteY() + 3), 0.0D).endVertex();
        worldrenderer.pos((this.centerX + 3), pos.getAbsoluteY(), 0.0D).endVertex();
        worldrenderer.pos((this.centerX - 3), pos.getAbsoluteY(), 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private void drawDirection(String dir, int degrees, double scale) {
        int offset = this.cwidth * degrees / 360 - this.offsetAll;
        if (offset > this.cwidth / 2)
            offset -= this.cwidth;
        if (offset < -this.cwidth / 2)
            offset += this.cwidth;

        double opacity = 1.0D - Math.abs((double) offset) / (double) this.width / 2.0D;

        if (opacity > 0.1D) {
            int defcolor = this.colorDirection & 0xFFFFFF;
            int color = defcolor | (int) (opacity * 255.0D) << 24;
            int posX = this.centerX + offset - (int) (font.getStringWidth(dir) * scale / 2.0D);
            int posY = pos.getAbsoluteY() + this.height / 2 - (int) (font.FONT_HEIGHT * scale / 2.0D);
            GL11.glEnable(3042);
            GL11.glPushMatrix();
            GL11.glTranslated(-posX * (scale - 1.0D), -posY * (scale - 1.0D), 0.0D);
            GL11.glScaled(scale, scale, 1.0D);
            if (this.shadow) {
                font.drawStringWithShadow(dir, posX, posY, color);
            } else {
                font.drawString(dir, posX, posY, color);
            }
            GL11.glPopMatrix();
            GL11.glDisable(3042);
        }
    }

    private int normalize(int direction) {
        if (direction > 360)
            direction %= 360;
        while (direction < 0)
            direction += 360;
        return direction;
    }
}
