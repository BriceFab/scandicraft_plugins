package net.scandicraft.packets.server;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.scandicraft.logs.LogManagement;

import java.io.IOException;

public class SPacketHelloWorld extends SPacket {


    /**
     * Reads the raw packet data from the data stream.
     *
     * @param buf
     */
    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        //no data for this one
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     *
     * @param handler
     */
    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        LogManagement.warn("The server says hello !");
    }
}
