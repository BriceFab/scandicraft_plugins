package net.scandicraft.gui.buttons.helper;

import net.scandicraft.gui.buttons.ButtonTemplate;
import net.scandicraft.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Aide pour le dessin de boutons dans un GuiScreen
 */
public class ButtonsHelper {

    public static List<GuiButtonInterface> drawButtons(final int screen_width, final int screen_height, List<ButtonTemplate> templateButtons) {
        List<GuiButtonInterface> buttonList = new ArrayList<>();

        final int totalNbrButtons = templateButtons.size();
        int totalNbrFullButton = ArrayUtils.filter(ButtonTemplate::getFullWidth, templateButtons).size() / 2;
        final int ySpace = 24;
        final int startHeight = (screen_height / 2) - (ySpace * (totalNbrButtons - totalNbrFullButton) / 2);
        for (int i = 0; i < totalNbrButtons; i++) {
            ButtonTemplate buttonTemplate = templateButtons.get(i);
            int x, y;
            if (!buttonTemplate.hasXYPosition()) {
                int shiftX = 0;
                int shiftY = 0;
                if (buttonTemplate.getFullWidth()) {    //button normal
                    shiftX = buttonTemplate.getWidth() / 2;
                } else {    //button column
                    shiftX = (buttonTemplate.getWidth() / 2) * ((buttonTemplate.getColumnIndex() + 1) * 2);
                    if (buttonTemplate.getColumnIndex() > 0) {
                        shiftX -= (buttonTemplate.getColumnIndex() + 1) * buttonTemplate.getWidth();
                        shiftX -= 2 * buttonTemplate.getColumnIndex();  //espace entre les 2 buttons sur la même ligne
                        shiftY -= ySpace * buttonTemplate.getColumnIndex();
                    }
                }
                x = screen_width / 2 - shiftX;
                y = (startHeight + ySpace * i) + shiftY;
            } else {
                x = buttonTemplate.getX();
                y = buttonTemplate.getY();
            }

//            GuiButtonInterface displayButton = getButton(buttonTemplate, x, y);
//            if (displayButton != null) {
//                buttonList.add(buttonTemplate);
//            }
        }

        return buttonList;
    }

}
