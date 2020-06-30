package net.scandicraft.mods.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.scandicraft.gui.hud.ScreenPosition;
import net.scandicraft.mods.ModDraggable;

public class ModPing extends ModDraggable {
    @Override
    public ScreenPosition getDefaultPos() {
        return ScreenPosition.fromRelativePosition(0.8337236533957846, 0.5083333333333333);
    }
    // private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
    // private final OldServerPinger oldServerPinger = new OldServerPinger();

    @Override
    public String getName() {
        return "ping";
    }

    private final int pingIconWidth = 10;
    private final int pingIconHeight = 8;

    // private Instant lastPing = null;
    // private boolean pingingServer = false;
    // private boolean canPing = true;
    // private final int pingEachSeconds = 3;  //TODO put in ScandiCraftSettings

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
        /*
//        ServerData serverData = mc.getCurrentServerData();
//        if (serverData == null) {
//            return;
//        }

        //Chaque x secondes
        // LogManagement.info("Current " + Instant.now().toString() + " lastPing + 30s" + lastPing.plusSeconds(30).toString());
        if (lastPing != null) {
            canPing = Instant.now().compareTo(lastPing.plusSeconds(pingEachSeconds)) >= 0;
        }

        if (canPing && !pingingServer) {
            LogManagement.info("ModPing new Runnable");
            pingingServer = true;
            serverData.pingToServer = -2L;
            field_148302_b.submit(() -> {
                try {
                    oldServerPinger.ping(serverData);
                } catch (UnknownHostException var2) {
                    serverData.pingToServer = -1L;
                    serverData.serverMOTD = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
                } catch (Exception var3) {
                    serverData.pingToServer = -1L;
                    serverData.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
                }
            });
            lastPing = Instant.now();
            pingingServer = false;
        }
        */

        String sPing;
        int ping = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
//        LogManagement.info("ping " + ping + " ms");

        int k = 0;
        int l;

        if (ping > 0L) {
            if (ping < 0L) {
                l = 5;
            } else if (ping < 150L) {
                l = 0;
            } else if (ping < 300L) {
                l = 1;
            } else if (ping < 600L) {
                l = 2;
            } else if (ping < 1000L) {
                l = 3;
            } else {
                l = 4;
            }

            if (ping < 0L) {
                sPing = "(no connection)";
            } else {
                sPing = ping + " ms";
            }
        } else {
            k = 1;
            l = (int) (Minecraft.getSystemTime() / 100L + (long) (2) & 7L);

            if (l > 4) {
                l = 8 - l;
            }

            sPing = "Pinging...";
        }

        /*
        int k = 0;
        int l = 0;
        String sPing = "";
        int ping = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
        if (ping > 0) {
            sPing = String.format("%d ms", ping);

            if (ping < 150L) {
                l = 0;
            } else if (ping < 300L) {
                l = 1;
            } else if (ping < 600L) {
                l = 2;
            } else if (ping < 1000L) {
                l = 3;
            } else {
                l = 4;
            }
        } else {
            sPing = "Pinging...";
            k = 1;
            l = (int) (Minecraft.getSystemTime() / 100L + (long) (2) & 7L);

            l = 5;

            if (l > 4) {
                l = 8 - l;
            }
        }
         */

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture(pos.getAbsoluteX() - 15, pos.getAbsoluteY(), (float) (k * 10), (float) (176 + l * 8), pingIconWidth, pingIconHeight, 256.0F, 256.0F);
        font.drawString(sPing, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }

}
