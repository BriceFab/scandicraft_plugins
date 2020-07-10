package net.scandicraft.gui.hud;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.scandicraft.events.EventManager;
import net.scandicraft.events.EventTarget;
import net.scandicraft.events.impl.ClientTickEvent;
import net.scandicraft.events.impl.RenderEvent;
import net.scandicraft.mods.Mod;
import net.scandicraft.settings.ScandiCraftSettings;
import org.lwjgl.opengl.GL11;

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
        if ((mc.currentScreen == null || mc.currentScreen instanceof GuiChat) && !mc.gameSettings.showDebugInfo) {
            for (IRenderer renderer : registeredRenderers) {
                callRenderer(renderer);
            }
        }
    }

    private void callRenderer(IRenderer renderer) {
        Mod actMod = (Mod) renderer;
        if (!actMod.isEnabled() || !actMod.isUsable()) {
            return;
        }

        ScreenPosition pos = renderer.load();
        if (pos == null) {
            pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(-pos.getAbsoluteX() * (pos.getScale() - 1.0F), -pos.getAbsoluteY() * (pos.getScale() - 1.0F), 0.0F);
        GL11.glScalef(pos.getScale(), pos.getScale(), 1.0F);

        renderer.render(pos);

        GL11.glScalef(1.0F / pos.getScale(), 1.0F / pos.getScale(), 1.0F);
        GL11.glTranslatef(pos.getAbsoluteX() * (pos.getScale() - 1.0F), pos.getAbsoluteY() * (pos.getScale() - 1.0F), 0.0F);
        GL11.glPopMatrix();
    }

    @EventTarget
    public void onTick(ClientTickEvent e) {
        if (ScandiCraftSettings.OPEN_HUD_MANAGER.isPressed()) {
            openConfigScreen();
        }
    }

}
