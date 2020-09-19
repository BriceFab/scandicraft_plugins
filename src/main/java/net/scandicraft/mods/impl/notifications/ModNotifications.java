package net.scandicraft.mods.impl.notifications;

import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

public class ModNotifications extends ModDraggable {

    public static ScreenPosition POSITION = ScreenPosition.fromRelativePosition(0.8416666666666667, 0.0392156862745098);
    private final NotificationManager notificationManager = NotificationManager.getInstance();

    @Override
    public String getName() {
        return "notifications";
    }

    @Override
    public ScreenPosition getDefaultPos() {
        return POSITION;
    }

    @Override
    public int getWidth() {
        return (int) notificationManager.getNotificationWidth();
    }

    @Override
    public int getHeight() {
        return (int) notificationManager.getNotificationHeight();
    }

    @Override
    public void render(ScreenPosition pos) {
        notificationManager.render();
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        Notification demo = new Notification("Notification de demo", true);

        demo.update();
        demo.render();
    }
}
