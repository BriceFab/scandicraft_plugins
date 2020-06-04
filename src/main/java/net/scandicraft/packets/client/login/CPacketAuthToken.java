package net.scandicraft.packets.client.login;

import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class CPacketAuthToken extends CLoginPacket {
    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        writeString(buf, "FAKE CLIENT TOKEN");
    }
}
