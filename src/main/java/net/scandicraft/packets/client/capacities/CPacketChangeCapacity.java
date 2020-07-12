package net.scandicraft.packets.client.capacities;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.scandicraft.capacities.ICapacity;
import net.scandicraft.packets.client.play.CPlayPacket;

import java.io.IOException;

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
        buf.writeUuid(Minecraft.getMinecraft().getScandiCraftSession().getUuid());                      //Player uuid
        writeString(buf, this.nextCapacity != null ? this.nextCapacity.getName() : "next_from_list");   //next capacity id
    }
}
