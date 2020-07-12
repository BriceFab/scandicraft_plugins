package net.scandicraft.packets.server.play;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.scandicraft.capacities.CapacityManager;
import net.scandicraft.capacities.ICapacity;

import java.io.IOException;

/**
 * Packet qui reçoit un changement de capacité depuis le serveur
 */
public class SPacketCurrentCapacity extends SPlayPacket {
    private ICapacity currentCapacity = null;

    /**
     * Reads the raw packet data from the data stream.
     *
     * @param buf buffer
     */
    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        String currentCapacityID = readString(buf);

        this.currentCapacity = CapacityManager.getInstance().getCapacityFromCapacityID(currentCapacityID);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     *
     * @param handler handler
     */
    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        CapacityManager.getInstance().setPlayerCurrentCapacity(this.currentCapacity);
    }
}
