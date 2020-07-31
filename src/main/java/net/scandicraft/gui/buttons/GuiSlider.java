package net.scandicraft.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.scandicraft.gui.Textures;

import java.util.function.Consumer;

public class GuiSlider extends GuiButton {
    private double sliderValue;
    public boolean dragging;
    private Consumer<Double> callback;
    private Consumer<GuiSlider> onFinish;
    private double min;
    private double max;

    public GuiSlider(int id, int x, int y, int w, int h, double currentValue, Consumer<Double> callback, Consumer<GuiSlider> onFinish, double min, double max) {
        super(id, x, y, w, h, "");

        this.sliderValue = currentValue;
        this.displayString = "test";
        this.callback = callback;
        this.onFinish = onFinish;
        this.min = min;
        this.max = max;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    public int getHoverState(boolean mouseOver) {
        return 0;
    }

    public void setSliderValue(double sliderValue) {
        this.sliderValue = sliderValue;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    public void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.getWidth() - 8);
                this.sliderValue = MathHelper.clamp_float((float) this.sliderValue, (float) this.min, (float) this.max);
//                float f = this.options.denormalizeValue(this.sliderValue);
//                mc.gameSettings.setOptionFloatValue(this.options, f);
//                this.sliderValue = this.options.normalizeValue(f);
//                this.displayString = mc.gameSettings.getKeyBinding(this.options);
                this.callback.accept(this.sliderValue);
            } else {
                this.onFinish.accept(this);
            }

            mc.getTextureManager().bindTexture(Textures.WIDGETS_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.getWidth() - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.getWidth() - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.getWidth() - 8);
            this.sliderValue = MathHelper.clamp_float((float) this.sliderValue, (float) this.min, (float) this.max);
//            mc.gameSettings.setOptionFloatValue(this.options, this.options.denormalizeValue(this.sliderValue));
//            this.displayString = mc.gameSettings.getKeyBinding(this.options);
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }
}
