package net.scandicraft.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.Theme;
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
    private final int lineWidth = 3;  //ScandiCraft red line width

    public DefaultButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, Theme.DEFAULT_BUTTON_WIDTH, Theme.DEFAULT_BUTTON_HEIGHT, buttonText);

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

            hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);

            final int delta = RenderUtils.deltaTime;

            if (enabled && hovered) {
                cut += 0.025F * delta;
                if (cut >= 2) cut = 2;
            } else {
                cut -= 0.025F * delta;
                if (cut <= 0) cut = 0;
            }

            Color buttonColor = Theme.SECONDARY_COLOR;
            Color textColor = Color.WHITE;
            if (enabled && hovered) {
                buttonColor = Theme.HOVER_COLOR;
            }
            if (!enabled) {
                textColor = Theme.DISABLED_TEXT_COLOR;
            }

            Gui.drawRect(
                    this.xPosition + (int) this.cut, this.yPosition + (int) this.cut,
                    this.xPosition + this.width - (int) this.cut, this.yPosition + this.height - (int) this.cut,
                    this.enabled ? buttonColor.getRGB() : Theme.DISABLED_COLOR.getRGB()
            );

            //draw vertical line
            if (enabled) {
                if (hovered) {
                    this.drawVerticalLine((int) (this.xPosition + cut), (int) (this.yPosition - 1 + cut), (int) (this.yPosition + this.height - cut), Theme.PRIMARY_COLOR.getRGB(), lineWidth);
                } else {
                    this.drawVerticalLine(this.xPosition, this.yPosition - 1, this.yPosition + this.height, Theme.PRIMARY_COLOR.getRGB(), lineWidth);
                }
            }

            mouseDragged(mc, mouseX, mouseY);

            fontRenderer.drawString(displayString,
                    (this.xPosition + this.width / 2) - fontRenderer.getStringWidth(displayString) / 2,
                    (int) (this.yPosition + (this.height + (isMcFont ? -5 : -7)) / 2F), textColor.getRGB());
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

    public void setHeight(int height) {
        this.height = height;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
}
