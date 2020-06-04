package net.scandicraft.packets.server.play;

import net.minecraft.network.PacketBuffer;
import net.scandicraft.packets.SCPlayPacket;

import java.io.IOException;

/* ScandiCraft: All packets that server send */
public abstract class SPlayPacket extends SCPlayPacket {

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        //Sent from the server (read)
    }

    public String readString(PacketBuffer buffer) {
        int length = buffer.readInt();
        return buffer.readStringFromBuffer(length);
    }
}
