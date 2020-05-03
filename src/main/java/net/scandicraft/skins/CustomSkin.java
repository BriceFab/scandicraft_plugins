package net.scandicraft.skins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class CustomSkin {
    private static final HashMap<String, CustomSkin> map = new HashMap<>();
    private static final ResourceLocation steve = new ResourceLocation("textures/entity/steve.png");

    private final String pseudo;
    private ResourceLocation rl;
    private BufferedImage buffer;

    public CustomSkin(String ps) {
        this.pseudo = ps;

        this.downloadSkin();
    }

    public void downloadSkin() {
        ImgThread it = new ImgThread(this);
        it.start();
    }

    public boolean hasSkin() {
        return this.rl != null;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public ResourceLocation getSkin() {
        if (this.hasSkin()) {
            return this.rl;
        } else if (this.buffer != null) {
            DynamicTexture previewTexture = new DynamicTexture(this.buffer);
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            this.rl = textureManager.getDynamicTextureLocation(this.pseudo, previewTexture);

            return this.rl;
        } else {
            return steve;
        }
    }

    public void setBuffer(BufferedImage img) {
        this.buffer = img;
    }

    public static ResourceLocation getSkin(String pseudo) {
        if (pseudo == null || pseudo.equals("null")) {
            return steve;
        }

        CustomSkin cs = map.get(pseudo);

        if (cs == null) {
            cs = new CustomSkin(pseudo);
            map.put(pseudo, cs);
        }

        return cs.getSkin();
    }

}