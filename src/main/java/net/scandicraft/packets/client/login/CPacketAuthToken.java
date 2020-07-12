package net.scandicraft.packets.client.login;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

/**
 * Packet qui gère l'authentification avec le token de l'API
 */
public class CPacketAuthToken extends CLoginPacket {
    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        writeString(buf, Minecraft.getMinecraft().getScandiCraftSession().getToken());
    }
}
