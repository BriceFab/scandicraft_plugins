package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiLockIconButton extends GuiButton
{
    private boolean locked = false;

    public GuiLockIconButton(int p_i45538_1_, int p_i45538_2_, int p_i45538_3_)
    {
        super(p_i45538_1_, p_i45538_2_, p_i45538_3_, 20, 20, "");
    }

    public boolean isLocked()
    {
        return this.locked;
    }

    public void setLocked(boolean lockedIn)
    {
        this.locked = lockedIn;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            mc.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.getWidth() && mouseY < this.yPosition + this.getHeight();
            GuiLockIconButton.Icon guilockiconbutton$icon;

            if (this.locked)
            {
                if (!this.enabled)
                {
                    guilockiconbutton$icon = GuiLockIconButton.Icon.LOCKED_DISABLED;
                }
                else if (flag)
                {
                    guilockiconbutton$icon = GuiLockIconButton.Icon.LOCKED_HOVER;
                }
                else
                {
                    guilockiconbutton$icon = GuiLockIconButton.Icon.LOCKED;
                }
            }
            else if (!this.enabled)
            {
                guilockiconbutton$icon = GuiLockIconButton.Icon.UNLOCKED_DISABLED;
            }
            else if (flag)
            {
                guilockiconbutton$icon = GuiLockIconButton.Icon.UNLOCKED_HOVER;
            }
            else
            {
                guilockiconbutton$icon = GuiLockIconButton.Icon.UNLOCKED;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, guilockiconbutton$icon.getX(), guilockiconbutton$icon.getY(), this.getWidth(), this.getHeight());
        }
    }

    static enum Icon
    {
        LOCKED(0, 146),
        LOCKED_HOVER(0, 166),
        LOCKED_DISABLED(0, 186),
        UNLOCKED(20, 146),
        UNLOCKED_HOVER(20, 166),
        UNLOCKED_DISABLED(20, 186);

        private final int x;
        private final int y;

        private Icon(int xIn, int yIn)
        {
            this.x = xIn;
            this.y = yIn;
        }

        public int getX()
        {
            return this.x;
        }

        public int getY()
        {
            return this.y;
        }
    }
}
