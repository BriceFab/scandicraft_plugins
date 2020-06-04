package net.scandicraft.packets.client.play;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.scandicraft.packets.SCPlayPacket;

import java.io.IOException;

public abstract class CPlayPacket extends SCPlayPacket {

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
