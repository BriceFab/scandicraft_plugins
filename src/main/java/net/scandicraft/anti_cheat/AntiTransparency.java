package net.scandicraft.anti_cheat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.Config;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AntiTransparency {
    static List<String> allow = new ArrayList<>();

    static void init() {
        allow.add("textures/blocks/stone.png");
        allow.add("textures/blocks/cobblestone.png");
        allow.add("textures/blocks/snow.png");
        allow.add("textures/blocks/gravel.png");
        allow.add("textures/blocks/sand.png");
        allow.add("textures/blocks/obsidian.png");
        allow.add("textures/blocks/furnace_top.png");
        allow.add("textures/blocks/furnace_side.png");
        allow.add("textures/blocks/stonebrick.png");
        allow.add("textures/blocks/brick.png");
        allow.add("textures/blocks/dirt.png");
        allow.add("textures/blocks/grass_side.png");
        allow.add("textures/blocks/grass_side_snowed.png");
        allow.add("textures/blocks/netherrack.png");
        allow.add("textures/blocks/bedrock.png");
    }

    /**
     * True = hasIllegalTexture
     * @return boolean
     */
    public static boolean checkTexturePack() {
        IResourceManager par1ResourceManager = Minecraft.getMinecraft().getResourceManager();
        init();

        for (String texture : allow) {
            try {
                IResource e = par1ResourceManager.getResource(new ResourceLocation(texture));
                InputStream var7 = e.getInputStream();
                BufferedImage var9 = ImageIO.read(var7);
                int pixnb = countTransparentPixels(var9);
                Config.print_debug("debug transparency " + pixnb);

                if (pixnb > 0) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Compte le nombre de pixels transparent dans une image
     * @param par1BufferedImage image
     * @return nombre de pixels
     */
    private static int countTransparentPixels(BufferedImage par1BufferedImage) {
        int pixnb = 0;
        int w = par1BufferedImage.getWidth();
        int h = par1BufferedImage.getHeight();

        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y) {
                int rgb = par1BufferedImage.getRGB(x, y);
                int alpha = rgb >> 24 & 255;

                if (alpha != 255) {
                    ++pixnb;
                }
            }
        }

        return pixnb;
    }
}
