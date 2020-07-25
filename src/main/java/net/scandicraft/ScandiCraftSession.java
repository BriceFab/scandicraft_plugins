package net.scandicraft;

import net.minecraft.client.Minecraft;

import java.util.UUID;

public class ScandiCraftSession {

    private final String username;
    private String api_token;
    private String password;

    public ScandiCraftSession(String username, String api_token) {
        this.username = username;
        this.api_token = api_token;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return Minecraft.getMinecraft().thePlayer.getUniqueID();
    }

    public String getToken() {
        return api_token;
    }

    public void setToken(String api_token) {
        this.api_token = api_token;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
