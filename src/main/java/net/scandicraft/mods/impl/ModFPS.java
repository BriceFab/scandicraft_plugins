package net.scandicraft.mods.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

public class ModFPS extends ModDraggable {
    public static ScreenPosition FPS_POSITION = ScreenPosition.fromRelativePosition(0.8416666666666667, 0.0392156862745098);
    private final ResourceLocation capacity1 = new ResourceLocation("scandicraft/capacities/guerrier_2.png");
    private final ResourceLocation capacity2 = new ResourceLocation("scandicraft/capacities/guerrier_2_test2.png");
    private final ResourceLocation capacity3 = new ResourceLocation("scandicraft/capacities/guerrier_1.png");
    private final ResourceLocation capacity4 = new ResourceLocation("scandicraft/capacities/guerrier_1_test2.png");
    private final ResourceLocation capacity5 = new ResourceLocation("scandicraft/capacities/archer_2.png");
    private final ResourceLocation capacity6 = new ResourceLocation("scandicraft/capacities/unknow_test2.png");

    @Override
    public String getName() {
        return "fps";
    }

    @Override
    public ScreenPosition getDefaultPos() {
        return FPS_POSITION;
    }

    @Override
    public int getWidth() {
        return font.getStringWidth("FPS: 000");
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {
//        renderCapacity(capacity1, 0, 0);
//        renderCapacity(capacity2, 100, 0);
//        renderCapacity(capacity3, 200, 0);
//        renderCapacity(capacity4, 200, 100);
//        renderCapacity(capacity5, 300, 0);
//        renderCapacity(capacity6, 300, 100);
        font.drawString("FPS: " + Minecraft.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }

    private void renderCapacity(ResourceLocation resourceLocationCapacity, int x, int y) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        final int width = 30;
        final int height = 30;

        mc.getTextureManager().bindTexture(resourceLocationCapacity);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(x, y, 0.0F, 0.0F, width, height, width, height, width, height);
    }

}
