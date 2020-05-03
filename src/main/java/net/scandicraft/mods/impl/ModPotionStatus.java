package net.scandicraft.mods.impl;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

public class ModPotionStatus extends ModDraggable {
    public static ScreenPosition ARMOR_STATUS_POSITION = ScreenPosition.fromRelativePosition(0.004166666666666667, 0.337037037037037);
    private static final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
    private final int right_shift = 22;             //décalage du texte à gauche (de la texture potion)
    private final int y_space_between_potion = 20;  //espacement entre chaque potion (Y)

    @Override
    public ScreenPosition getDefaultPos() {
        return ARMOR_STATUS_POSITION;
    }

    @Override
    public int getWidth() {
        return 64;
    }

    @Override
    public int getHeight() {
        return 64;
    }

    @Override
    public void render(ScreenPosition pos) {
        Collection<PotionEffect> activePotionEffects = this.mc.thePlayer.getActivePotionEffects();
        if (!activePotionEffects.isEmpty()) {
            int defaultPosY = 0;
            for (Iterator<PotionEffect> iterator = activePotionEffects.iterator(); iterator.hasNext(); defaultPosY += y_space_between_potion) {
                PotionEffect potionEffect = iterator.next();
                renderPotion(potionEffect, defaultPosY);
            }
        }
    }

    private void renderPotion(PotionEffect potionEffect, int defaultPosY) {
        Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(inventoryBackground);

        if (potion.hasStatusIcon()) {
            int potionPosIndex = potion.getStatusIconIndex();
            mc.ingameGUI.drawTexturedModalRect(pos.getAbsoluteX(), pos.getAbsoluteY() + defaultPosY, potionPosIndex % 8 * 18, 198 + potionPosIndex / 8 * 18, 18, 18);
        }

        String potionName = String.format("%s %s", I18n.format(potion.getName()), getAmplifier(potionEffect));

        font.drawStringWithShadow(potionName, pos.getAbsoluteX() + right_shift, pos.getAbsoluteY() + defaultPosY, Color.WHITE.getRGB());
        font.drawStringWithShadow(Potion.getDurationString(potionEffect), pos.getAbsoluteX() + right_shift, pos.getAbsoluteY() + defaultPosY + 10, Color.LIGHT_GRAY.getRGB());
    }

    private String getAmplifier(PotionEffect potionEffect) {
        switch (potionEffect.getAmplifier()) {
            case 1:
                return I18n.format("enchantment.level.2");
            case 2:
                return I18n.format("enchantment.level.3");
            case 3:
                return I18n.format("enchantment.level.4");
            default:
                return "";
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        Collection<PotionEffect> activePotionEffects = this.mc.thePlayer.getActivePotionEffects();
        if (!activePotionEffects.isEmpty()) {
            int defaultPosY = 0;
            for (Iterator<PotionEffect> iterator = activePotionEffects.iterator(); iterator.hasNext(); defaultPosY += y_space_between_potion) {
                PotionEffect potionEffect = iterator.next();
                renderPotion(potionEffect, defaultPosY);
            }
        } else {
            PotionEffect potionEffect = new PotionEffect(Potion.moveSpeed.id, 6000, 1);
            renderPotion(potionEffect, 0);
        }
    }

}
