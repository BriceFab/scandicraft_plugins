package net.scandicraft.gui.buttons.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.config.Theme;

/**
 * Base des boutons
 */
public class BaseButton extends Gui implements GuiButtonInterface {
    public int id;
    protected int width;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public boolean enabled;
    public boolean visible;
    public boolean hovered;

    public BaseButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, Theme.DEFAULT_BUTTON_WIDTH, Theme.DEFAULT_BUTTON_HEIGHT, buttonText);
    }

    public BaseButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;

        this.enabled = true;
        this.visible = true;
    }

    /**
     * Appeler pour dessiner le bouton
     *
     * @param mc     minecraft
     * @param mouseX mouseX
     * @param mouseY mouseY
     */
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);

            mouseDragged(mc, mouseX, mouseY);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     *
     * @param mc     mc
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @return boolean
     */
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Lance le son lors du click
     *
     * @param soundHandlerIn soundHandler
     */
    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     *
     * @param mouseX mouseX
     * @param mouseY mouseY
     */
    @Override
    public void mouseReleased(int mouseX, int mouseY) {

    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    public int getHoverState(boolean mouseOver) {
        int i = 1;

        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }

        return i;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    public void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    /* Getters/Setters */
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
