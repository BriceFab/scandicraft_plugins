package net.scandicraft.gui.buttons;

import net.scandicraft.Theme;

public class ButtonTemplate {

    private final String text;
    private final Boolean fullWidth;
    private final int width;
    private final int height;
    private final int columnIndex;  //Column: 0 | 1 | 2 | 3

    public ButtonTemplate(String text) {
        this(text, true);
    }

    public ButtonTemplate(String text, Boolean fullWidth) {
        this(text, fullWidth, Theme.DEFAULT_BUTTON_WIDTH, Theme.DEFAULT_BUTTON_HEIGHT, 0);
    }

    public ButtonTemplate(String text, int width, int columnIndex) {
        this(text, false, width, Theme.DEFAULT_BUTTON_HEIGHT, columnIndex);
    }

    public ButtonTemplate(String text, Boolean fullWidth, int width, int height, int columnIndex) {
        this.text = text;
        this.fullWidth = fullWidth;
        this.height = height;
        this.width = width;
        this.columnIndex = columnIndex;
    }

    public String getText() {
        return text;
    }

    public Boolean getFullWidth() {
        return fullWidth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}
