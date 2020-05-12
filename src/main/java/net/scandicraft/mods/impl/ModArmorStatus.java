package net.scandicraft.mods.impl;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.items.register.ScandiCraftItems;
import net.scandicraft.mods.ModDraggable;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;

public class ModArmorStatus extends ModDraggable {
    public static ScreenPosition ARMOR_STATUS_POSITION = ScreenPosition.fromRelativePosition(0.004166666666666667, 0.337037037037037);

    @Override
    public String getName() {
        return "armor";
    }

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
        return (16 * 5);
    }

    @Override
    public void render(ScreenPosition pos) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(mc.thePlayer.getCurrentEquippedItem());
        items.addAll(Arrays.asList(mc.thePlayer.inventory.armorInventory));

        for (int i = 0; i < items.size(); i++) {
            ItemStack itemStack = items.get(i);
            renderItemStack(pos, i, itemStack);
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(mc.thePlayer.getCurrentEquippedItem());
        items.addAll(Arrays.asList(mc.thePlayer.inventory.armorInventory));

        for (int i = 0; i < items.size(); i++) {
            ItemStack itemStack = items.get(i);
            if (itemStack == null) {
                itemStack = getDefaultItem(i);
            }
            renderItemStack(pos, i, itemStack);
        }
    }

    private ItemStack getDefaultItem(int i) {
        switch (i) {
            default:
                return null;
            case 4:
                return new ItemStack(ScandiCraftItems.scandium_helmet);
            case 3:
                return new ItemStack(ScandiCraftItems.scandium_chestplate);
            case 2:
                return new ItemStack(ScandiCraftItems.scandium_leggings);
            case 1:
                return new ItemStack(ScandiCraftItems.scandium_boots);
            case 0:
                return new ItemStack(ScandiCraftItems.scandium_sword);
        }
    }

    private void renderItemStack(ScreenPosition pos, int i, ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        GL11.glPushMatrix();
        int yAdd = (-16 * i) + (16 * 4);    //4= le nombre d'éléments à afficher

        if (itemStack.getItem().isDamageable()) {
            double damage = ((itemStack.getMaxDamage() - itemStack.getItemDamage()) / (double) itemStack.getMaxDamage() * 100);
            font.drawString(String.format("%d%%", (int) damage), pos.getAbsoluteX() + 20, pos.getAbsoluteY() + yAdd + 5, -1);
        }

        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, pos.getAbsoluteX(), pos.getAbsoluteY() + yAdd);

        GL11.glPopMatrix();
    }

}
