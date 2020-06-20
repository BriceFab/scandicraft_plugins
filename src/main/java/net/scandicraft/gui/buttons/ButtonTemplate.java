package net.scandicraft.gui.buttons;

import net.scandicraft.Theme;

public class ButtonTemplate {

    private final Integer id;
    private final String text;
    private final Boolean fullWidth;
    private final int width;
    private final int height;
    private final int columnIndex;  //Column: 0 | 1 | 2 | 3

    public ButtonTemplate(Integer id, String text) {
        this(id, text, true);
    }

    public ButtonTemplate(Integer id, String text, Boolean fullWidth) {
        this(id, text, fullWidth, Theme.DEFAULT_BUTTON_WIDTH, Theme.DEFAULT_BUTTON_HEIGHT, 0);
    }

    public ButtonTemplate(Integer id, String text, int width, int columnIndex) {
        this(id, text, false, width, Theme.DEFAULT_BUTTON_HEIGHT, columnIndex);
    }

    public ButtonTemplate(Integer id, String text, Boolean fullWidth, int width, int height, int columnIndex) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }
}
