package net.scandicraft.mods.impl;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.Theme;
import net.scandicraft.capacities.CapacityManager;
import net.scandicraft.capacities.ICapacity;
import net.scandicraft.classes.ClasseManager;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;
import net.scandicraft.utils.GuiUtils;

import java.util.ArrayList;

public class ModCapacities extends ModDraggable {
    public static ScreenPosition FPS_POSITION = ScreenPosition.fromRelativePosition(0.8416666666666667, 0.0392156862745098);
    private final int icon_width = 20;
    private final int icon_height = 20;
    private final int y_padding = 10;
    private final int select_padding = 2;

    //Laisse activer le mod (mais en arrière plan)
    @Override
    public boolean canBeUnregister() {
        return false;
    }

    @Override
    public String getName() {
        return "capacities";
    }

    @Override
    public ScreenPosition getDefaultPos() {
        return FPS_POSITION;
    }

    @Override
    public int getWidth() {
        return icon_width;
    }

    @Override
    public int getHeight() {
        return ((icon_height + y_padding - (select_padding / 2)) * ClasseManager.getInstance().getPlayerClasse().getCapacities().size() - select_padding + 1);
    }

    @Override
    public void render(ScreenPosition pos) {
        //TODO disable mod if no classe
        if (ClasseManager.getInstance().getPlayerClasse() == null) {
            return;
        }

        final ArrayList<ICapacity> playerCapacities = ClasseManager.getInstance().getPlayerClasse().getCapacities();
        final ICapacity playerCurrentCapacity = CapacityManager.getInstance().getPlayerCurrentCapacity();
        for (int i = 0; i < playerCapacities.size(); i++) {
            ICapacity capacity = playerCapacities.get(i);
            ResourceLocation icon = capacity.getCapacityIcon();
            renderCapacity(icon, i, capacity.equals(playerCurrentCapacity));
//            renderCapacity(icon, i, i == 0);
        }
    }

    private void renderCapacity(ResourceLocation resourceLocationCapacity, final int index, final boolean isSelected) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);

        if (resourceLocationCapacity == null) {
            resourceLocationCapacity = new ResourceLocation("scandicraft/capacities/archer_2.png");
        }

        int yAdd = (icon_height + y_padding) * index;    //4= le nombre d'éléments à afficher

        mc.getTextureManager().bindTexture(resourceLocationCapacity);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(pos.getAbsoluteX(), pos.getAbsoluteY() + yAdd, 0.0F, 0.0F, icon_width, icon_height, icon_width, icon_height, icon_width, icon_height);

        if (isSelected) {
            GuiUtils.drawHollowRect(pos.getAbsoluteX() - select_padding, (pos.getAbsoluteY() - select_padding) + yAdd, icon_width + (select_padding * 2), icon_height + (select_padding * 2), Theme.PRIMARY_COLOR.getRGB());
        }
    }
}
