package net.scandicraft.packets.client;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.scandicraft.packets.SCPacket;

import java.io.IOException;

public abstract class CPacket extends SCPacket {

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        //Sending from the client (don't need read)
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        //Sending from the client (don't need process)
    }

    public void writeString(PacketBuffer buffer, String toWrite) {
        buffer.writeInt(toWrite.length());
        buffer.writeString(toWrite);
    }

}
