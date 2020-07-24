package net.scandicraft.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.scandicraft.config.Theme;
import net.scandicraft.fonts.Fonts;
import net.scandicraft.fonts.GameFontRenderer;
import net.scandicraft.render.RenderUtils;

import java.awt.*;

public class DefaultButton extends Gui {
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    protected Color buttonColor = Theme.SECONDARY_COLOR;
    protected Color textColor = Theme.TEXT_COLOR;
    protected Color hoverButtonColor = Theme.HOVER_COLOR;
    protected Color hoverTextColor = Theme.HOVER_TEXT_COLOR;
    protected Color disabledButtonColor = Theme.DISABLED_COLOR;
    protected Color disabledTextColor = Theme.DISABLED_TEXT_COLOR;
    protected Color lineColor = Theme.PRIMARY_COLOR;
    protected GameFontRenderer fontSize = Fonts.fontNormal;
    protected GameFontRenderer hoverFontSize = Fonts.fontSemiBold;
    protected int shiftFontHeight = -7;
    protected int shiftHoverFontHeight = -7;

    protected int width;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled;
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
            FontRenderer fontRenderer = isMcFont ? mc.fontRendererObj : Fonts.fontNormal;
            if (!isMcFont && this.fontSize != null) {
                fontRenderer = this.fontSize;
            }

            hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);

            final int delta = RenderUtils.deltaTime;

            if (enabled && hovered) {
                cut += 0.025F * delta;
                if (cut >= 2) cut = 2;
            } else {
                cut -= 0.025F * delta;
                if (cut <= 0) cut = 0;
            }

            Color renderButtonColor = this.buttonColor;
            Color renderTextColor = this.textColor;
            if (enabled && hovered) {
                renderButtonColor = hoverButtonColor;
                renderTextColor = hoverTextColor;
                if (hoverFontSize != null) {
                    fontRenderer = hoverFontSize;
                }
            }
            if (!enabled) {
                renderTextColor = disabledTextColor;
            }

            Gui.drawRect(
                    this.xPosition + (int) this.cut, this.yPosition + (int) this.cut,
                    this.xPosition + this.width - (int) this.cut, this.yPosition + this.height - (int) this.cut,
                    this.enabled ? renderButtonColor.getRGB() : disabledButtonColor.getRGB()
            );

            //draw vertical line
            if (enabled) {
                if (hovered) {
                    this.drawVerticalLine((int) (this.xPosition + cut), (int) (this.yPosition - 1 + cut), (int) (this.yPosition + this.height - cut), lineColor.getRGB(), lineWidth);
                } else {
                    this.drawVerticalLine(this.xPosition, this.yPosition - 1, this.yPosition + this.height, lineColor.getRGB(), lineWidth);
                }
            }

            mouseDragged(mc, mouseX, mouseY);

            fontRenderer.drawString(displayString,
                    (this.xPosition + this.width / 2) - fontRenderer.getStringWidth(displayString) / 2,
                    (int) (this.yPosition + (this.height + (isMcFont ? -5 : hovered ? this.shiftHoverFontHeight : this.shiftFontHeight)) / 2F), renderTextColor.getRGB());
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
