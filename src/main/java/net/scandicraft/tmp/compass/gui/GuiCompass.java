package net.scandicraft.tmp.compass.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.scandicraft.mods.impl.ModCompass;
import net.scandicraft.tmp.compass.FNCompass;

import java.io.IOException;

public class GuiCompass extends GuiScreen {
    protected ModCompass mod;

    protected FNCompass compass;

    protected GuiScreen parent;

    private boolean dragging;

    private int lastX;

    private int lastY;

    public GuiCompass(ModCompass mod, GuiScreen parent) {
        this.mod = mod;
        this.compass = mod.getCompass();
        this.parent = parent;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.compass.enabled)
            this.compass.drawCompass(this.width);
        if (this.dragging) {
            this.compass.offX += mouseX - this.lastX;
            this.compass.offY += mouseY - this.lastY;
        }
        this.lastX = mouseX;
        this.lastY = mouseY;
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= (this.width - this.compass.width) / 2 + this.compass.offX && mouseY >= this.compass.offY && mouseX <= (this.width + this.compass.width) / 2 + this.compass.offX && mouseY <= this.compass.offY + this.compass.height) {
            this.dragging = true;
            this.lastX = mouseX;
            this.lastY = mouseY;
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.dragging = false;
    }

    public void onGuiClosed() {
        // this.mod.saveConfig();
    }

    protected String getColoredBool(String prefix, boolean bool) {
        if (bool)
            return prefix + EnumChatFormatting.GREEN + "TRUE";
        return prefix + EnumChatFormatting.RED + "FALSE";
    }
}
