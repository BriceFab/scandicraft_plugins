package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;

public class S3EPacketTeams implements Packet<INetHandlerPlayClient>
{
    private String name = "";
    private String displayName = "";
    private String field_149319_c = "";
    private String field_149316_d = "";
    private String nameTagVisibility;
    private int color;
    private Collection<String> players;
    private int action;
    private int friendlyFlags;

    public S3EPacketTeams()
    {
        this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
        this.color = -1;
        this.players = Lists.<String>newArrayList();
    }

    public S3EPacketTeams(ScorePlayerTeam p_i45225_1_, int p_i45225_2_)
    {
        this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
        this.color = -1;
        this.players = Lists.<String>newArrayList();
        this.name = p_i45225_1_.getRegisteredName();
        this.action = p_i45225_2_;

        if (p_i45225_2_ == 0 || p_i45225_2_ == 2)
        {
            this.displayName = p_i45225_1_.getTeamName();
            this.field_149319_c = p_i45225_1_.getColorPrefix();
            this.field_149316_d = p_i45225_1_.getColorSuffix();
            this.friendlyFlags = p_i45225_1_.getFriendlyFlags();
            this.nameTagVisibility = p_i45225_1_.getNameTagVisibility().internalName;
            this.color = p_i45225_1_.getChatFormat().getColorIndex();
        }

        if (p_i45225_2_ == 0)
        {
            this.players.addAll(p_i45225_1_.getMembershipCollection());
        }
    }

    public S3EPacketTeams(ScorePlayerTeam p_i45226_1_, Collection<String> p_i45226_2_, int p_i45226_3_)
    {
        this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
        this.color = -1;
        this.players = Lists.<String>newArrayList();

        if (p_i45226_3_ != 3 && p_i45226_3_ != 4)
        {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        }
        else if (p_i45226_2_ != null && !p_i45226_2_.isEmpty())
        {
            this.action = p_i45226_3_;
            this.name = p_i45226_1_.getRegisteredName();
            this.players.addAll(p_i45226_2_);
        }
        else
        {
            throw new IllegalArgumentException("Players cannot be null/empty");
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.name = buf.readStringFromBuffer(16);
        this.action = buf.readByte();

        if (this.action == 0 || this.action == 2)
        {
            this.displayName = buf.readStringFromBuffer(32);
            this.field_149319_c = buf.readStringFromBuffer(16);
            this.field_149316_d = buf.readStringFromBuffer(16);
            this.friendlyFlags = buf.readByte();
            this.nameTagVisibility = buf.readStringFromBuffer(32);
            this.color = buf.readByte();
        }

        if (this.action == 0 || this.action == 3 || this.action == 4)
        {
            int i = buf.readVarIntFromBuffer();

            for (int j = 0; j < i; ++j)
            {
                this.players.add(buf.readStringFromBuffer(40));
            }
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeString(this.name);
        buf.writeByte(this.action);

        if (this.action == 0 || this.action == 2)
        {
            buf.writeString(this.displayName);
            buf.writeString(this.field_149319_c);
            buf.writeString(this.field_149316_d);
            buf.writeByte(this.friendlyFlags);
            buf.writeString(this.nameTagVisibility);
            buf.writeByte(this.color);
        }

        if (this.action == 0 || this.action == 3 || this.action == 4)
        {
            buf.writeVarIntToBuffer(this.players.size());

            for (String s : this.players)
            {
                buf.writeString(s);
            }
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleTeams(this);
    }

    public String getName()
    {
        return this.name;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public String func_149311_e()
    {
        return this.field_149319_c;
    }

    public String func_149309_f()
    {
        return this.field_149316_d;
    }

    public Collection<String> getPlayers()
    {
        return this.players;
    }

    public int getAction()
    {
        return this.action;
    }

    public int getFriendlyFlags()
    {
        return this.friendlyFlags;
    }

    public int func_179813_h()
    {
        return this.color;
    }

    public String getNameTagVisibility()
    {
        return this.nameTagVisibility;
    }
}
