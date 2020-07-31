package net.scandicraft.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.gui.buttons.helper.BaseButton;

public class TexturedButton extends BaseButton {
    protected float scale = 1.0F;
    protected ResourceLocation texture;
    protected ResourceLocation texture_hover;

    public TexturedButton(int buttonId, int x, int y, float scale, ResourceLocation texture, ResourceLocation texture_hover) {
        super(buttonId, x, y, null);

        this.texture = texture;
        this.texture_hover = texture_hover;
        this.scale = scale;
    }

    public TexturedButton(int buttonId, int x, int y, ResourceLocation texture) {
        this(buttonId, x, y, 1.0F, texture, texture);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + (this.width * this.scale) && mouseY < this.yPosition + (this.height * this.scale);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        super.drawButton(mc, mouseX, mouseY);

        if (this.visible && this.texture != null) {
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + (this.width * scale) && mouseY < this.yPosition + (this.height * scale));
            mc.getTextureManager().bindTexture(this.hovered ? this.texture_hover : this.texture);

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

//            if (this.texture.getResourcePath().contains("play")) {
//                this.xPosition = 37;
//                this.yPosition = 118;
//            }
//            if (this.texture.getResourcePath().contains("solo")) {
//                this.xPosition = 36;
//                this.yPosition = 72;
//            }
//
//            final ScaledResolution scaledResolution = new ScaledResolution(mc);
//
//            if (this.texture.getResourcePath().contains("exit")) {
//                this.scale = 0.04F;
//                this.xPosition = 84;
//                this.yPosition = 190;
//            }
//
//            if (this.texture.getResourcePath().contains("settings")) {
//                this.scale = 0.04F;
//                this.xPosition = 58;
//                this.yPosition = 190;
//            }

            Gui.drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, 0.0F, 0.0F, (int) (this.width * this.scale), (int) (this.height * this.scale), this.width * this.scale, this.height * this.scale);
        }
    }
}
