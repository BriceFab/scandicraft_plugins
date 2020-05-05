package net.scandicraft.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.Config;
import net.scandicraft.fonts.Fonts;
import net.scandicraft.render.RenderUtils;

import java.awt.*;

public class DefaultButton extends Gui {
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    /**
     * Button width in pixels
     */
    protected int width;

    /**
     * Button height in pixels
     */
    protected int height;

    /**
     * The x position of this control.
     */
    public int xPosition;

    /**
     * The y position of this control.
     */
    public int yPosition;

    /**
     * The string displayed on this control.
     */
    public String displayString;
    public int id;

    /**
     * True if this control is enabled, false to disable.
     */
    public boolean enabled;

    /**
     * Hides the button completely if false.
     */
    public boolean visible;
    protected boolean hovered;

    private float cut;

    public int packedFGColour; //FML

    public DefaultButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);

        if (packedFGColour != 0) {
            // j = packedFGColour;
        }
    }

    public DefaultButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver) {
        int i = 1;

        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }

        return i;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (visible) {

            boolean isMcFont = mc.getLanguageManager().isCurrentLocaleUnicode();
            final FontRenderer fontRenderer = isMcFont ? mc.fontRendererObj : Fonts.fontNormal;

            hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition &&
                    mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);

            final int delta = RenderUtils.deltaTime;

            if (enabled && hovered) {
                cut += 0.025F * delta;
                if (cut >= 2) cut = 2;
            } else {
                cut -= 0.025F * delta;
                if (cut <= 0) cut = 0;
            }

            Color buttonColor = new Color(13, 13, 13, 180);
            Color textColor = Color.WHITE;
            if (enabled && hovered) {
                buttonColor = new Color(204, 204, 204, 80);
            }
            if (!enabled) {
                textColor = new Color(128, 128, 128);
            }
            Color disabledColor = new Color(191, 191, 191, 30);

            Gui.drawRect(
                    this.xPosition + (int) this.cut, this.yPosition + (int) this.cut,
                    this.xPosition + this.width - (int) this.cut, this.yPosition + this.height - (int) this.cut,
                    this.enabled ? buttonColor.getRGB() : disabledColor.getRGB()
            );

            mc.getTextureManager().bindTexture(buttonTextures);
            mouseDragged(mc, mouseX, mouseY);

            fontRenderer.drawStringWithShadow(displayString,
                    (float) ((this.xPosition + this.width / 2) -
                            fontRenderer.getStringWidth(displayString) / 2),
                    this.yPosition + (this.height + (isMcFont ? -5 : -9)) / 2F, textColor.getRGB());
            GlStateManager.resetColor();
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY) {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
    }

    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
}
