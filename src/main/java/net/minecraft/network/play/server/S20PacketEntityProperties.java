package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S20PacketEntityProperties implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private final List<S20PacketEntityProperties.Snapshot> snapshots = Lists.<S20PacketEntityProperties.Snapshot>newArrayList();

    public S20PacketEntityProperties()
    {
    }

    public S20PacketEntityProperties(int entityIdIn, Collection<IAttributeInstance> p_i45236_2_)
    {
        this.entityId = entityIdIn;

        for (IAttributeInstance iattributeinstance : p_i45236_2_)
        {
            this.snapshots.add(new S20PacketEntityProperties.Snapshot(iattributeinstance.getAttribute().getAttributeUnlocalizedName(), iattributeinstance.getBaseValue(), iattributeinstance.func_111122_c()));
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.entityId = buf.readVarIntFromBuffer();
        int i = buf.readInt();

        for (int j = 0; j < i; ++j)
        {
            String s = buf.readStringFromBuffer(64);
            double d0 = buf.readDouble();
            List<AttributeModifier> list = Lists.<AttributeModifier>newArrayList();
            int k = buf.readVarIntFromBuffer();

            for (int l = 0; l < k; ++l)
            {
                UUID uuid = buf.readUuid();
                list.add(new AttributeModifier(uuid, "Unknown synced attribute modifier", buf.readDouble(), buf.readByte()));
            }

            this.snapshots.add(new S20PacketEntityProperties.Snapshot(s, d0, list));
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarIntToBuffer(this.entityId);
        buf.writeInt(this.snapshots.size());

        for (S20PacketEntityProperties.Snapshot s20packetentityproperties$snapshot : this.snapshots)
        {
            buf.writeString(s20packetentityproperties$snapshot.getName());
            buf.writeDouble(s20packetentityproperties$snapshot.getBaseValue());
            buf.writeVarIntToBuffer(s20packetentityproperties$snapshot.getModifiers().size());

            for (AttributeModifier attributemodifier : s20packetentityproperties$snapshot.getModifiers())
            {
                buf.writeUuid(attributemodifier.getID());
                buf.writeDouble(attributemodifier.getAmount());
                buf.writeByte(attributemodifier.getOperation());
            }
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleEntityProperties(this);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public List<S20PacketEntityProperties.Snapshot> getSnapshots()
    {
        return this.snapshots;
    }

    public class Snapshot
    {
        private final String name;
        private final double baseValue;
        private final Collection<AttributeModifier> modifiers;

        public Snapshot(String p_i45235_2_, double p_i45235_3_, Collection<AttributeModifier> p_i45235_5_)
        {
            this.name = p_i45235_2_;
            this.baseValue = p_i45235_3_;
            this.modifiers = p_i45235_5_;
        }

        public String getName()
        {
            return this.name;
        }

        public double getBaseValue()
        {
            return this.baseValue;
        }

        public Collection<AttributeModifier> getModifiers()
        {
            return this.modifiers;
        }
    }
}
