package net.scandicraft.mods.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.scandicraft.Config;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

import java.net.UnknownHostException;
import java.time.Instant;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class ModPing extends ModDraggable {
    private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
    private final OldServerPinger oldServerPinger = new OldServerPinger();

    private final int pingIconWidth = 10;
    private final int pingIconHeight = 8;

    private Instant lastPing = null;
    private boolean pingingServer = false;
    private boolean canPing = true;
    private final int pingEachSeconds = 3;  //TODO put in ScandiCraftSettings

    @Override
    public int getWidth() {
        return pingIconWidth + font.getStringWidth("xxx ms");
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {
        ServerData serverData = mc.getCurrentServerData();
        if (serverData == null) {
            return;
        }

        //Chaque x secondes
        // Config.print_debug("Current " + Instant.now().toString() + " lastPing + 30s" + lastPing.plusSeconds(30).toString());
        if (lastPing != null) {
            canPing = Instant.now().compareTo(lastPing.plusSeconds(pingEachSeconds)) >= 0;
        }

        if (canPing && !pingingServer) {
            Config.print_debug("ModPing new Runnable");
            pingingServer = true;
            serverData.pingToServer = -2L;
            field_148302_b.submit(() -> {
                try {
                    oldServerPinger.ping(serverData);
                } catch (UnknownHostException var2) {
                    serverData.pingToServer = -1L;
                    serverData.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t resolve hostname";
                } catch (Exception var3) {
                    serverData.pingToServer = -1L;
                    serverData.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t connect to server.";
                }
            });
            lastPing = Instant.now();
            pingingServer = false;
        }

        int k = 0;
        int l;
        String s1;

        // Config.print_debug("ModPing pingToServer " + serverData.pingToServer + " " + serverData.pinged + " " + serverData.playerList + " " + serverData.populationInfo + " " + serverData.gameVersion);

        if (serverData.pingToServer != -2L) {
            if (serverData.pingToServer < 0L) {
                l = 5;
            } else if (serverData.pingToServer < 150L) {
                l = 0;
            } else if (serverData.pingToServer < 300L) {
                l = 1;
            } else if (serverData.pingToServer < 600L) {
                l = 2;
            } else if (serverData.pingToServer < 1000L) {
                l = 3;
            } else {
                l = 4;
            }

            if (serverData.pingToServer < 0L) {
                s1 = "(no connection)";
            } else {
                s1 = serverData.pingToServer + "ms";
            }
        } else {
            k = 1;
            l = (int) (Minecraft.getSystemTime() / 100L + (long) (2) & 7L);

            if (l > 4) {
                l = 8 - l;
            }

            s1 = "Pinging...";
        }

        // Config.print_debug("ping: " + s1);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture(pos.getAbsoluteX() - 15, pos.getAbsoluteY(), (float) (k * 10), (float) (176 + l * 8), pingIconWidth, pingIconHeight, 256.0F, 256.0F);
        font.drawString(s1, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
}
