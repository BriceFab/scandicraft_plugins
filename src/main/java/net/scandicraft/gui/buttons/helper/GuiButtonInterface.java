package net.scandicraft.gui.buttons.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;

/**
 * Interface des boutons
 */
public interface GuiButtonInterface {

    /**
     * Appeler pour dessiner le bouton
     *
     * @param mc     minecraft
     * @param mouseX mouseX
     * @param mouseY mouseY
     */
    void drawButton(Minecraft mc, int mouseX, int mouseY);

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     *
     * @param mc     mc
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @return boolean
     */
    boolean mousePressed(Minecraft mc, int mouseX, int mouseY);

    /**
     * Lance le son lors du click
     *
     * @param soundHandlerIn soundHandler
     */
    void playPressSound(SoundHandler soundHandlerIn);

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     *
     * @param mouseX mouseX
     * @param mouseY mouseY
     */
    void mouseReleased(int mouseX, int mouseY);

    /**
     * Whether the mouse cursor is currently over the button.
     */
    boolean isMouseOver();

    void drawButtonForegroundLayer(int mouseX, int mouseY);

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    int getHoverState(boolean mouseOver);

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    void mouseDragged(Minecraft mc, int mouseX, int mouseY);
}
