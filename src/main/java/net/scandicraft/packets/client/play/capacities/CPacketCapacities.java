package net.scandicraft.packets.client.play.capacities;

import net.minecraft.network.PacketBuffer;
import net.scandicraft.capacities.CapacityAction;
import net.scandicraft.packets.client.play.CPlayPacket;

import java.io.IOException;

public class CPacketCapacities extends CPlayPacket {
    private CapacityAction action = null;

    public CPacketCapacities() {
    }

    public CPacketCapacities(CapacityAction action) {
        this.action = action;
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeEnumValue(this.action);
    }
}
