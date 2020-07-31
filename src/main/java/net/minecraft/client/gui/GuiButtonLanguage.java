package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.scandicraft.gui.Textures;

public class GuiButtonLanguage extends GuiButton {
    public GuiButtonLanguage(int buttonID, int xPos, int yPos) {
        super(buttonID, xPos, yPos, 20, 20, "");
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(Textures.WIDGETS_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.getWidth() && mouseY < this.yPosition + this.getHeight();
            int i = 106;

            if (flag) {
                i += this.getHeight();
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, i, this.getWidth(), this.getHeight());
        }
    }
}
