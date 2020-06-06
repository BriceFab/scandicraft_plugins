package net.scandicraft;

import net.minecraft.client.Minecraft;

import java.util.UUID;

public class ScandiCraftSession {

    private final String username;
    private String api_token;

    public ScandiCraftSession(String username, String api_token) {
        this.username = username;
        this.api_token = api_token;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return UUID.fromString(Minecraft.getMinecraft().getSession().getPlayerID());
    }

    public String getToken() {
        return api_token;
    }
}
