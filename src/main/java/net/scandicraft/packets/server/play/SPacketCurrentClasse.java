package net.scandicraft.packets.server.play;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.scandicraft.classes.ClasseManager;
import net.scandicraft.classes.ClasseType;
import net.scandicraft.classes.IClasse;
import net.scandicraft.logs.LogManagement;

import java.io.IOException;
import java.util.Objects;

/**
 * Packet qui reçoit un changement de capacité depuis le serveur
 */
public class SPacketCurrentClasse extends SPlayPacket {
    private IClasse currentClasse = null;

    /**
     * Reads the raw packet data from the data stream.
     *
     * @param buf buffer
     */
    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        int currentClasseID = buf.readInt();
        ClasseType classeType = ClasseType.getClasseTypeFromId(currentClasseID);

        if (classeType != null) {
            this.currentClasse = classeType.getIClasse();
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     *
     * @param handler handler
     */
    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        if (this.currentClasse != null) {
            ClasseManager.getInstance().setPlayerClasse(this.currentClasse);
        }
    }
}
