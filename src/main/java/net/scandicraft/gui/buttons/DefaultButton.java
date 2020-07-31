package net.scandicraft.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.scandicraft.config.Theme;
import net.scandicraft.fonts.Fonts;
import net.scandicraft.fonts.GameFontRenderer;
import net.scandicraft.gui.buttons.helper.BaseButton;
import net.scandicraft.render.RenderUtils;

import java.awt.*;

public class DefaultButton extends BaseButton {
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
    private float cut;
    private final int lineWidth = 3;  //ScandiCraft red line width

    public DefaultButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public DefaultButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        super.drawButton(mc, mouseX, mouseY);

        if (visible) {
            boolean isMcFont = mc.getLanguageManager().isCurrentLocaleUnicode();
            FontRenderer fontRenderer = isMcFont ? mc.fontRendererObj : Fonts.fontNormal;
            if (!isMcFont && this.fontSize != null) {
                fontRenderer = this.fontSize;
            }

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

            fontRenderer.drawString(displayString,
                    (this.xPosition + this.width / 2) - fontRenderer.getStringWidth(displayString) / 2,
                    (int) (this.yPosition + (this.height + (isMcFont ? -5 : hovered ? this.shiftHoverFontHeight : this.shiftFontHeight)) / 2F), renderTextColor.getRGB());

            GlStateManager.resetColor();
        }
    }
}