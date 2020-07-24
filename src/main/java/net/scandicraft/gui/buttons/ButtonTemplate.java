package net.scandicraft.gui.buttons;

import net.minecraft.client.gui.GuiButton;
import net.scandicraft.config.Theme;

public class ButtonTemplate {

    private final Integer id;
    private final String text;
    private final Boolean fullWidth;
    private final int width;
    private final int height;
    private int columnIndex;  //Column: 0 | 1 | 2 | 3
    private int x, y = 0;
    private Class<? extends GuiButton> guiButtonTemplate = GuiButton.class;

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

    public ButtonTemplate(int id, int x, int y, String text, int width, int height) {
        this.id = id;
        this.text = text;
        this.fullWidth = false;
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
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

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean hasXYPosition() {
        return x != 0 && y != 0;
    }

    public ButtonTemplate setGuiButtonTemplate(Class<? extends GuiButton> guiButtonTemplate) {
        this.guiButtonTemplate = guiButtonTemplate;
        return this;
    }

    public Class<? extends GuiButton> getGuiButtonTemplate() {
        return guiButtonTemplate;
    }

    public Integer getId() {
        return id;
    }
}
