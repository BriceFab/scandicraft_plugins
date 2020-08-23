package net.scandicraft.packets.client.play;

import net.minecraft.network.PacketBuffer;
import net.scandicraft.capacities.ICapacity;

import java.io.IOException;

/**
 * Packet qui envoie au serveur le changement de capacité
 */
public class CPacketChangeCapacity extends CPlayPacket {
    private ICapacity nextCapacity = null;

    public CPacketChangeCapacity() {
    }

    public CPacketChangeCapacity(ICapacity nextCapacity) {
        this.nextCapacity = nextCapacity;
    }

    /**
     * Writes the raw packet data to the data stream.
     *
     * @param buf buffer
     */
    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        writeString(buf, this.nextCapacity != null ? this.nextCapacity.getName() : "next_from_list");   //next capacity id
    }
}
