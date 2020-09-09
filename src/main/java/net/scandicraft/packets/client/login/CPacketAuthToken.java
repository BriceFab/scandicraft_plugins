package net.scandicraft.packets.client.login;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.scandicraft.Environnement;
import net.scandicraft.config.Config;
import net.scandicraft.logs.LogManagement;

import java.io.IOException;

/**
 * Packet qui gère l'authentification avec le token de l'API
 */
public class CPacketAuthToken extends CLoginPacket {
    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        final String auth_token = Minecraft.getMinecraft().getScandiCraftSession().getToken();
        if (Config.ENV != Environnement.PROD) {
            LogManagement.info("packet send auth token: " + auth_token);
        }
        writeString(buf, auth_token);
    }
}
