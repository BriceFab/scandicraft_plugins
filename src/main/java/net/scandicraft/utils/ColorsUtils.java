package net.scandicraft.utils;

import java.awt.*;

public class ColorsUtils {

    public static int transparency(final int color, final double alpha) {
        final Color c = new Color(color);
        final float r = 0.003921569f * c.getRed();
        final float g = 0.003921569f * c.getGreen();
        final float b = 0.003921569f * c.getBlue();
        return new Color(r, g, b, (float) alpha).getRGB();
    }

    public static float[] getRGBA(final int color) {
        final float a = (color >> 24 & 0xFF) / 255.0f;
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        return new float[]{r, g, b, a};
    }

    public static int intFromHex(final String hex) {
        try {
            if (hex.equalsIgnoreCase("rainbow")) {
                return rainbow(0L, 1.0f).getRGB();
            }
            return Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String hexFromInt(final int color) {
        return hexFromInt(new Color(color));
    }

    public static String hexFromInt(final Color color) {
        return Integer.toHexString(color.getRGB()).substring(2);
    }

    public static Color blend(final Color color1, final Color color2, final double ratio) {
        final float r = (float) ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
    }

    public static Color blend(final Color color1, final Color color2) {
        return blend(color1, color2, 0.5);
    }

    public static Color darker(final Color color, final double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 - fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 - fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 - fraction));
        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }
        final int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha);
    }

    public static Color lighter(final Color color, final double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 + fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 + fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 + fraction));
        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }
        final int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha);
    }

    public static String getHexName(final Color color) {
        final int r = color.getRed();
        final int g = color.getGreen();
        final int b = color.getBlue();
        final String rHex = Integer.toString(r, 16);
        final String gHex = Integer.toString(g, 16);
        final String bHex = Integer.toString(b, 16);
        return ((rHex.length() == 2) ? rHex : "0" + rHex) + ((gHex.length() == 2) ? gHex : ("0" + gHex)) + ((bHex.length() == 2) ? bHex : ("0" + bHex));
    }

    public static double colorDistance(final double r1, final double g1, final double b1, final double r2, final double g2, final double b2) {
        final double a = r2 - r1;
        final double b3 = g2 - g1;
        final double c = b2 - b1;
        return Math.sqrt(a * a + b3 * b3 + c * c);
    }

    public static double colorDistance(final double[] color1, final double[] color2) {
        return colorDistance(color1[0], color1[1], color1[2], color2[0], color2[1], color2[2]);
    }

    public static double colorDistance(final Color color1, final Color color2) {
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return colorDistance(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
    }

    public static boolean isDark(final double r, final double g, final double b) {
        final double dWhite = colorDistance(r, g, b, 1.0, 1.0, 1.0);
        final double dBlack = colorDistance(r, g, b, 0.0, 0.0, 0.0);
        return dBlack < dWhite;
    }

    public static boolean isDark(final Color color) {
        final float r = color.getRed() / 255.0f;
        final float g = color.getGreen() / 255.0f;
        final float b = color.getBlue() / 255.0f;
        return isDark(r, g, b);
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = ((float) 1 / 255) * c.getRed();
        float g = ((float) 1 / 255) * c.getGreen();
        float b = ((float) 1 / 255) * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
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

    public static Color rainbow(final long offset, final float fade) {
        final float hue = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        final Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, c.getAlpha() / 255.0f);
    }

    public static Color rainbow() {
        Color currentColor = new Color(Color.HSBtoRGB((System.nanoTime() + 400000L) / 10000000000F % 1, 1F, 1F));
        return new Color(currentColor.getRed() / 255F * 1F, currentColor.getGreen() / 255f * 1F, currentColor.getBlue() / 255F * 1F, currentColor.getAlpha() / 255F);
    }

    public static Color rainbow(float fade) {
        float hue = (float) (System.nanoTime() / 5000000000f) % 1;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1f, 1f)), 16);
        Color c = new Color((int) color);
        return new Color((c.getRed() / 255f) * fade, (c.getGreen() / 255f) * fade, (c.getBlue() / 255f) * fade, c.getAlpha() / 255f);
    }

}
