package net.scandicraft.packets.server;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.scandicraft.logs.LogManagement;
import net.scandicraft.packets.client.CPacketMoreData;

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
        LogManagement.info("The server says hello !");

        //Send CPacketMoreData
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new CPacketMoreData());
    }
}
