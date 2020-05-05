package net.scandicraft.gui.hud;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.scandicraft.events.EventManager;
import net.scandicraft.events.EventTarget;
import net.scandicraft.events.impl.RenderEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class HUDManager {

    private HUDManager() {
    }

    private static HUDManager INSTANCE = null;

    public static HUDManager getInstance() {

        if (INSTANCE != null) {
            return INSTANCE;
        }

        INSTANCE = new HUDManager();
        EventManager.register(INSTANCE);

        return INSTANCE;
    }

    private final Set<IRenderer> registeredRenderers = Sets.newHashSet();
    private final Minecraft mc = Minecraft.getMinecraft();

    public void register(IRenderer renderer) {
        this.registeredRenderers.add(renderer);
    }

    public void registerAll(IRenderer... renderers) {
        this.registeredRenderers.addAll(Arrays.asList(renderers));
    }

    public void unregister(IRenderer... renderers) {
        for (IRenderer render : renderers) {
            this.registeredRenderers.remove(render);
        }
    }

    public Collection<IRenderer> getRegisteredRenderers() {
        return Sets.newHashSet(registeredRenderers);
    }

    public void openConfigScreen() {
        mc.displayGuiScreen(new HUDConfigScreen(this));
    }

    @EventTarget
    public void onRender(RenderEvent e) {
        if (mc.currentScreen == null || mc.currentScreen instanceof GuiChat) {
            for (IRenderer renderer : registeredRenderers) {
                callRenderer(renderer);
            }
        }
    }

    private void callRenderer(IRenderer renderer) {
        if (!renderer.isEnabled()) {
            return;
        }

        ScreenPosition pos = renderer.load();
        if (pos == null) {
            pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }

        renderer.render(pos);
    }

}
