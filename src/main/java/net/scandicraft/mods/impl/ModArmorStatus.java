package net.scandicraft.mods.impl;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;
import org.lwjgl.opengl.GL11;

public class ModArmorStatus extends ModDraggable {

    private ScreenPosition pos = ScreenPosition.fromRelativePosition(0.5, 0.5);

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
        ItemStack[] armors = mc.thePlayer.inventory.armorInventory;
        for (int i = 0; i < armors.length; i++) {
            ItemStack itemStack = armors[i];
            renderItemStack(pos, i, itemStack);
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        renderItemStack(pos, 3, new ItemStack(Items.scandium_helmet));
        renderItemStack(pos, 2, new ItemStack(Items.scandium_chestplate));
        renderItemStack(pos, 1, new ItemStack(Items.scandium_leggings));
        renderItemStack(pos, 0, new ItemStack(Items.scandium_boots));
    }

    private void renderItemStack(ScreenPosition pos, int i, ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        GL11.glPushMatrix();
        int yAdd = (-16 * i) + 48;

        if (itemStack.getItem().isDamageable()) {
            double damage = ((itemStack.getMaxDamage() - itemStack.getItemDamage()) / (double) itemStack.getMaxDamage() * 100);
            font.drawString(String.format("%d%%", (int) damage), pos.getAbsoluteX() + 20, pos.getAbsoluteY() + yAdd + 5, -1);
        }

        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, pos.getAbsoluteX(), pos.getAbsoluteY() + yAdd);

        GL11.glPopMatrix();
    }

    @Override
    public void save(ScreenPosition pos) {
        this.pos = pos;
    }

    @Override
    public ScreenPosition load() {
        return pos;
    }
}
