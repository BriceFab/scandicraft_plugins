package net.scandicraft.gui.hud;

public interface IRenderConfig {
    void save(ScreenPosition pos);

    ScreenPosition load();
}
