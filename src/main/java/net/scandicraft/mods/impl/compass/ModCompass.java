package net.scandicraft.mods.impl.compass;

import net.minecraft.client.gui.ScaledResolution;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;
import net.scandicraft.mods.ModInstances;
import net.scandicraft.mods.impl.compass.gui.GuiCompassHub;

public class ModCompass extends ModDraggable {

    private final FNCompass compass;

    public ModCompass() {
        this.compass = new FNCompass(this.mc);
    }

    public FNCompass getCompass() {
        return this.compass;
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 100;
    }

    @Override
    public void render(ScreenPosition pos) {

        if (mc.gameSettings.keyBindLeft.isPressed()) {
            // mc.displayGuiScreen(new GuiCompassHub(ModInstances.getModCompass()));
        }

        this.compass.drawCompass(new ScaledResolution(this.mc).getScaledWidth());
    }
}
