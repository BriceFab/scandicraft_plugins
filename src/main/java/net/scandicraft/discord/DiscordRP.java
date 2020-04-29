package net.scandicraft.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.scandicraft.Config;

public class DiscordRP {

    private boolean running = true;
    private long created = 0;

    public void start() {
        this.created = System.currentTimeMillis();

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(discordUser -> {
            Config.print_debug("Discord ScandiCraft " + discordUser.username + " #" + discordUser.discriminator + ".");
            update("Chargement..");
        }).build();

        DiscordRPC.discordInitialize(Config.DISCORD_CLIENT_ID, handlers, true);

        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String state) {
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(state);
        builder.setBigImage("icon", "");
        builder.setDetails(Config.URL_WEBSITE);
        builder.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(builder.build());
    }

}