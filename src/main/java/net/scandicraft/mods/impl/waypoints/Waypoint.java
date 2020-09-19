package net.scandicraft.mods.impl.waypoints;

import net.scandicraft.client.MinecraftInstance;

import java.awt.*;

public class Waypoint extends MinecraftInstance {

    public String name;
    public double x, y, z;
    public int color;

    public Waypoint(String name) {
        this(name, Color.WHITE.getRGB());
    }

    public Waypoint(String name, int color) {
        this(name, mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, color);
    }

    public Waypoint(String name, double x, double y, double z, int color) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

}
