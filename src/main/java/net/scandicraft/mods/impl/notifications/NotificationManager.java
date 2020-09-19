package net.scandicraft.mods.impl.notifications;

import net.scandicraft.client.MinecraftInstance;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager extends MinecraftInstance {

    private static NotificationManager instance = null;
    private List<Notification> notifications = new ArrayList<>();

    private final float notification_duration = 5.0F; //secondes
    private final float notification_fade_duration = 0.5F;   //secondes
    private final float notification_width = 100F;
    private final float notification_height = 25F;

    public void add(Notification notification) {
        if (!this.notifications.contains(notification)) {
            this.notifications.add(notification);
        }
    }

    public void remove(Notification notification) {
        this.notifications.remove(notification);
    }

    public void render() {
        if (this.hasNotifications() && !mc.isGamePaused()) {
            new ArrayList<>(this.notifications).forEach(notification -> {
                notification.update();
                notification.render();
            });
        }
    }

    public boolean hasNotifications() {
        return this.count() > 0;
    }

    public int getNotificationIndex(Notification notification) {
        return this.notifications.indexOf(notification);
    }

    public int count() {
        return this.notifications.size();
    }

    public float getNotificationDuration() {
        return notification_duration;
    }

    public float getNotificationWidth() {
        return notification_width;
    }

    public float getNotificationHeight() {
        return notification_height;
    }

    public float getNotificationFadeDuration() {
        return notification_fade_duration;
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }

        return instance;
    }
}
