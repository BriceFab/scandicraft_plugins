package net.scandicraft.packets.client.login;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.scandicraft.packets.SCLoginPacket;

import java.io.IOException;

public abstract class CLoginPacket extends SCLoginPacket {

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        //Sending from the client (don't need read)
    }

    @Override
    public void processPacket(INetHandlerLoginClient handler) {
        //Sending from the client (don't need process)
    }

    public void writeString(PacketBuffer buffer, String toWrite) {
        buffer.writeInt(toWrite.length());
        buffer.writeString(toWrite);
    }

}
