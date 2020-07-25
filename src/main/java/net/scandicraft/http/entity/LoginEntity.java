package net.scandicraft.http.entity;

import com.google.gson.annotations.SerializedName;

public class LoginEntity {
    @SerializedName("username")
    private String username;

    private String password;

    public LoginEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
