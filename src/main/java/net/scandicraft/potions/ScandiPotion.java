package net.scandicraft.potions;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ScandiPotion extends Potion {
    //ScandiCraft Start ID 24
    public static final Potion feather_falling = (new ScandiPotion(24, new ResourceLocation("feather_falling"), false, Color.YELLOW.getRGB()))
            .setIconIndex(3, 2)
            .setPotionName("potion.feather_falling");

    protected ScandiPotion(int potionID, ResourceLocation location, boolean badEffect, int potionColor) {
        super(potionID, location, badEffect, potionColor);
    }
}
