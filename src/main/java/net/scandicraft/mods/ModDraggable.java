package net.scandicraft.mods;

import net.scandicraft.gui.hud.IRenderer;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.settings.FileManager;

import java.io.File;

public abstract class ModDraggable extends Mod implements IRenderer {

    protected ScreenPosition pos;
    protected float scale;

    public ModDraggable() {
        pos = loadPositionFromFile();
    }

    @Override
    public ScreenPosition load() {
        return pos;
    }

    @Override
    public void save(ScreenPosition pos) {
        this.pos = pos;
        savePositionToFile();
    }

    private File getFolder() {
        File folder = new File(FileManager.getModsDirectory(), getName());
        folder.mkdirs();
        return folder;
    }

    private ScreenPosition loadPositionFromFile() {

        ScreenPosition loaded = FileManager.readFromJsonFile(new File(getFolder(), "position.json"), ScreenPosition.class);
        if (loaded == null) {
            loaded = getDefaultPos();
            this.pos = loaded;
            savePositionToFile();
        }

        return loaded;
    }

    private void savePositionToFile() {
        FileManager.writeJsonToFile(new File(getFolder(), "position.json"), pos);
    }

    public final int getLineOffset(ScreenPosition pos, int lineNum) {
        return pos.getAbsoluteY() + getLineOffset(lineNum);
    }

    private int getLineOffset(int lineNum) {
        return (font.FONT_HEIGHT + 3) * lineNum;
    }

    public ScreenPosition getDefaultPos() {
        return ScreenPosition.fromRelativePosition(0.5, 0.5);
    }

    public ScreenPosition getScreenPosition() {
        return pos;
    }
}
