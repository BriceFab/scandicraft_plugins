package net.scandicraft.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.minecraft.client.Minecraft;
import net.scandicraft.logs.LogManagement;

import java.util.Date;

public class VerifyToken {

    public static boolean isExpired() {
        try {
            DecodedJWT jwt = JWT.decode(Minecraft.getMinecraft().getScandiCraftSession().getToken());
            Date expireAt = jwt.getExpiresAt();

            //a value less than <code>0</code> if this Date is before the Date argument
            return expireAt.compareTo(new Date()) < 0;
        } catch (JWTDecodeException exception) {
            //Invalid token
            LogManagement.error("Token is invalid");
            return true;
        }
    }

}
