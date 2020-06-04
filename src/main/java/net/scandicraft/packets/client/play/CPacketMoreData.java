package net.scandicraft.packets.client.play;

import net.minecraft.entity.item.EntityPainting;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;

import java.io.IOException;
import java.util.UUID;

public class CPacketMoreData extends CPlayPacket {

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeInt(1337);
        buf.writeDouble(8.23);
        buf.writeFloat(33.3F);
        buf.writeByteArray(new byte[]{1, 2, 3, 4, 5});
        buf.writeBoolean(true);
        writeString(buf, "Hello PacketMoreData");
        buf.writeUuid(UUID.fromString("689eedc3-d28f-4a2a-9326-53daa0114a22"));
        buf.writeEnumValue(EntityPainting.EnumArt.ALBAN);
        buf.writeBlockPos(new BlockPos(10, 20, 30));
        buf.writeItemStackToBuffer(new ItemStack(Blocks.pumpkin, 10, 5));

        NBTTagCompound testTag = new NBTTagCompound();
        testTag.setBoolean("testboolean", true);
        buf.writeNBTTagCompoundToBuffer(testTag);
    }

}
