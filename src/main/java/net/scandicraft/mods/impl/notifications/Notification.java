package net.scandicraft.mods.impl.notifications;

import net.scandicraft.fonts.Fonts;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModInstances;
import net.scandicraft.utils.ColorsUtils;
import net.scandicraft.utils.MathUtils;
import net.scandicraft.utils.RenderUtils;
import net.scandicraft.utils.timers.Timer;

import java.awt.*;

public class Notification {

    private final NotificationManager notificationManager = NotificationManager.getInstance();
    private final ModNotifications modNotifications = ModInstances.getModNotifications();

    public String message;
    public float x, y;
    public Timer timer = new Timer();
    private final float time, width, height;
    private final boolean infinite;
    private final float max_opacity = 0.5F;
    private final float min_opacity = 0.0F;
    private float opacity = min_opacity;

    public Notification(String message) {
        this(message, false);
    }

    public Notification(String message, boolean infinite) {
        timer.reset();

        this.time = notificationManager.getNotificationDuration();
        this.width = notificationManager.getNotificationWidth();
        this.height = notificationManager.getNotificationHeight();

        this.message = message;

        ScreenPosition screenPosition = modNotifications.getScreenPosition();
        this.x = (float) screenPosition.getAbsoluteX();
        this.y = (float) screenPosition.getAbsoluteY();

        if (infinite) {
            this.opacity = max_opacity;
        } else {
            this.opacity = min_opacity;
        }

        this.infinite = infinite;
    }

    public void update() {
        if (timer.isTime(time)) {
            notificationManager.remove(this);
        } else {
            this.updateFade();
        }
    }

    private void updateFade() {
        if (opacity < max_opacity) {
            opacity = MathUtils.convertMsToSeconds(timer.currentTime()) * max_opacity / (notificationManager.getNotificationFadeDuration());
        }

        if (opacity > max_opacity) {
            opacity = max_opacity;
        }

        if (opacity < min_opacity) {
            opacity = min_opacity;
        }
    }

    public void render() {
        final int index = !infinite ? notificationManager.getNotificationIndex(this) : 0;
        if (index >= 0) {
            int revertIndex = !infinite ? (notificationManager.count() - 1) - index : 0;
            float realY = y + ((height + 3) * revertIndex);

            RenderUtils.drawRect(x, realY, x + width, realY + height, ColorsUtils.transparency(Color.RED.getRGB(), opacity));

            if (message != null) {
                float fontOpacity = (opacity == max_opacity ? 1.0F : opacity);
                Fonts.fontSmall.drawString("Notification", x + 2, realY + 2, ColorsUtils.transparency(Color.ORANGE.getRGB(), fontOpacity));
                Fonts.fontExtraSmall.drawString(message, x + 2, realY + 2 + Fonts.fontSmall.getHeight(), ColorsUtils.transparency(Color.ORANGE.getRGB(), fontOpacity));
            }
        }
    }

}
