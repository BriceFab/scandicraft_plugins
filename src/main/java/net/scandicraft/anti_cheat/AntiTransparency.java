package net.scandicraft.anti_cheat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AntiTransparency {
    private final static String BLOCKS_PATH = "textures/blocks/";
    private static List<String> texturesCheck = new ArrayList<>(Arrays.asList(
            BLOCKS_PATH + "stone.png",
            BLOCKS_PATH + "cobblestone.png",
            BLOCKS_PATH + "snow.png",
            BLOCKS_PATH + "gravel.png",
            BLOCKS_PATH + "sand.png",
            BLOCKS_PATH + "furnace_top.png",
            BLOCKS_PATH + "furnace_side.png",
            BLOCKS_PATH + "stonebrick.png",
            BLOCKS_PATH + "brick.png",
            BLOCKS_PATH + "dirt.png",
            BLOCKS_PATH + "grass_side.png",
            BLOCKS_PATH + "grass_side_snowed.png",
            BLOCKS_PATH + "netherrack.png",
            BLOCKS_PATH + "red_sand.png",
            BLOCKS_PATH + "bedrock.png"
    ));
    private final static String MODELS_PATH = "models/block/";
    private static List<String> modelsCheck = new ArrayList<>(Arrays.asList(
            MODELS_PATH + "cube.json",
            MODELS_PATH + "grass.json"
    ));
    private final static IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();

    /**
     * Check des resources packs (textures et json)
     * True = hasIllegalTexture
     *
     * @return boolean
     */
    public static boolean checkTexturePack() {
        return checkTextures() || checkModels();
    }

    /**
     * On peut aussi modifier le transparence depuis le dossier models d'un resource pack (json)
     *
     * @return true == hasIllegalModels
     */
    private static boolean checkModels() {
        for (String texture : modelsCheck) {
            try {
                IResource resource = resourceManager.getResource(new ResourceLocation(texture));
                InputStream inputStream = resource.getInputStream();
                JsonObject jsonModel;
                JsonElement jsonElement = new JsonParser().parse(
                        new InputStreamReader(inputStream)
                );
                jsonModel = jsonElement.getAsJsonObject();
                for (JsonElement element : jsonModel.getAsJsonArray("elements")) {
                    for (JsonElement to : element.getAsJsonObject().getAsJsonArray("to")) {
                        int toValue = to.getAsInt();
                        if (toValue < 16) {
                            return true;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Check textures
     *
     * @return true = hasIllegalTexture
     */
    private static boolean checkTextures() {
        for (String texture : texturesCheck) {
            try {
                IResource resource = resourceManager.getResource(new ResourceLocation(texture));
                InputStream inputStream = resource.getInputStream();
                BufferedImage bufferedImage = ImageIO.read(inputStream);

                if (countTransparentPixels(bufferedImage) > 0) {
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
     *
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
