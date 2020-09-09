package net.scandicraft.anti_cheat.x_ray;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.anti_cheat.CheatConfig;
import net.scandicraft.anti_cheat.CheatType;
import net.scandicraft.gui.cheat.CheatScreen;
import net.scandicraft.logs.LogManagement;
import net.scandicraft.utils.ChecksumUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

public class AntiTransparency implements Runnable {
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
        for (Map.Entry<String, String> model : CheatConfig.modelsCheck.entrySet()) {
            try {
                IResource resource = resourceManager.getResource(new ResourceLocation(model.getKey()));
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

                // LogManagement.info("checksum " + model.getKey() + " " + Checksum.getSHA1(jsonModel.toString()) + " " + model.getValue());

                if (!Objects.equals(ChecksumUtils.getSHA1(jsonModel.toString()), model.getValue())) {
                    return true;
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
        for (String texture : CheatConfig.texturesCheck) {
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

    @Override
    public void run() {
        long startAt = System.currentTimeMillis();
        LogManagement.info("Start check x-ray..");

        boolean hasIllegal = checkTexturePack();
        if (hasIllegal) {
            CheatScreen.onDetectCheat(CheatType.XRAY);
        }

        LogManagement.info("Stop check x-ray in " + (System.currentTimeMillis() - startAt) + " ms");
    }
}
