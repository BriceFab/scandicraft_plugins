package net.scandicraft.packets.client.play.capacities.impl;

import net.minecraft.network.PacketBuffer;
import net.scandicraft.capacities.CapacityAction;
import net.scandicraft.capacities.ICapacity;
import net.scandicraft.packets.client.play.capacities.CPacketCapacities;

import java.io.IOException;

public class CPacketChangeCapacity extends CPacketCapacities {
    private ICapacity nextCapacity = null;

    public CPacketChangeCapacity() {
        super(CapacityAction.CHANGE_CURRENT_CAPACITY);
    }

    public CPacketChangeCapacity(ICapacity nextCapacity) {
        super(CapacityAction.CHANGE_CURRENT_CAPACITY);
        this.nextCapacity = nextCapacity;
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        super.writePacketData(buf);
        writeString(buf, this.nextCapacity != null ? this.nextCapacity.getName() : "next_from_list");   //next capacity id
    }
}
