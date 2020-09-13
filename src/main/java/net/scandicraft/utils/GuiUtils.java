package net.scandicraft.utils;

import net.minecraft.client.gui.Gui;

public class GuiUtils {

    public static void drawHollowRect(int x, int y, int w, int h, int color) {
        drawHorizontalLine(x, x + w, y, color);
        drawHorizontalLine(x, x + w, y + h, color);

        drawVerticalLine(x, y + h, y, color);
        drawVerticalLine(x + w, y + h, y, color);
    }

    /**
     * Draw a 1 pixel wide horizontal line. Args: x1, x2, y, color
     */
    public static void drawHorizontalLine(int startX, int endX, int y, int color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }

        Gui.drawRect(startX, y, endX + 1, y + 1, color);
    }

    /**
     * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
     */
    public static void drawVerticalLine(int x, int startY, int endY, int color) {
        drawVerticalLine(x, startY, endY, color, 1);
    }

    public static void drawVerticalLine(int x, int startY, int endY, int color, int width) {
        if (endY < startY) {
            int i = startY;
            startY = endY;
            endY = i;
        }

        Gui.drawRect(x, startY + 1, x + width, endY, color);
    }

    public static float[] getScaledImage(float wi, float hi, float ws, float hs) {
        float ri = wi / hi;

        float rs = ws / hs;

        float dw = 0;
        float dh = 0;

        if (rs <= ri) {
            dw = wi * hs / hi;
            dh = hs;
        } else {
            dw = ws;
            dh = hi * ws / wi;
        }

        float x = (ws - dw) / 2;
        float y = (hs - dh) / 2;

        return new float[]{x, y, dw, dh};
    }

}
