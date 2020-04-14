package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.server.MinecraftServer;

public class ServerScoreboard extends Scoreboard
{
    private final MinecraftServer scoreboardMCServer;
    private final Set<ScoreObjective> addedObjectives = Sets.<ScoreObjective>newHashSet();
    private ScoreboardSaveData scoreboardSaveData;

    public ServerScoreboard(MinecraftServer mcServer)
    {
        this.scoreboardMCServer = mcServer;
    }

    public void onScoreChanged(Score scoreIn)
    {
        super.onScoreChanged(scoreIn);

        if (this.addedObjectives.contains(scoreIn.getObjective()))
        {
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(scoreIn));
        }

        this.markSaveDataDirty();
    }

    public void onPlayerRemoved(String scoreName)
    {
        super.onPlayerRemoved(scoreName);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(scoreName));
        this.markSaveDataDirty();
    }

    public void onPlayerScoreRemoved(String scoreName, ScoreObjective objective)
    {
        super.onPlayerScoreRemoved(scoreName, objective);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(scoreName, objective));
        this.markSaveDataDirty();
    }

    /**
     * 0 is tab menu, 1 is sidebar, 2 is below name
     */
    public void setObjectiveInDisplaySlot(int objectiveSlot, ScoreObjective objective)
    {
        ScoreObjective scoreobjective = this.getObjectiveInDisplaySlot(objectiveSlot);
        super.setObjectiveInDisplaySlot(objectiveSlot, objective);

        if (scoreobjective != objective && scoreobjective != null)
        {
            if (this.getObjectiveDisplaySlotCount(scoreobjective) > 0)
            {
                this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(objectiveSlot, objective));
            }
            else
            {
                this.getPlayerIterator(scoreobjective);
            }
        }

        if (objective != null)
        {
            if (this.addedObjectives.contains(objective))
            {
                this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(objectiveSlot, objective));
            }
            else
            {
                this.addObjective(objective);
            }
        }

        this.markSaveDataDirty();
    }

    /**
     * Adds a player to the given team
     */
    public boolean addPlayerToTeam(String player, String newTeam)
    {
        if (super.addPlayerToTeam(player, newTeam))
        {
            ScorePlayerTeam scoreplayerteam = this.getTeam(newTeam);
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scoreplayerteam, Arrays.asList(new String[] {player}), 3));
            this.markSaveDataDirty();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Removes the given username from the given ScorePlayerTeam. If the player is not on the team then an
     * IllegalStateException is thrown.
     */
    public void removePlayerFromTeam(String username, ScorePlayerTeam playerTeam)
    {
        super.removePlayerFromTeam(username, playerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(playerTeam, Arrays.asList(new String[] {username}), 4));
        this.markSaveDataDirty();
    }

    /**
     * Called when a score objective is added
     */
    public void onScoreObjectiveAdded(ScoreObjective scoreObjectiveIn)
    {
        super.onScoreObjectiveAdded(scoreObjectiveIn);
        this.markSaveDataDirty();
    }

    public void func_96532_b(ScoreObjective p_96532_1_)
    {
        super.func_96532_b(p_96532_1_);

        if (this.addedObjectives.contains(p_96532_1_))
        {
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3BPacketScoreboardObjective(p_96532_1_, 2));
        }

        this.markSaveDataDirty();
    }

    public void onObjectiveRemoved(ScoreObjective objective)
    {
        super.onObjectiveRemoved(objective);

        if (this.addedObjectives.contains(objective))
        {
            this.getPlayerIterator(objective);
        }

        this.markSaveDataDirty();
    }

    /**
     * This packet will notify the players that this team is created, and that will register it on the client
     */
    public void broadcastTeamCreated(ScorePlayerTeam playerTeam)
    {
        super.broadcastTeamCreated(playerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(playerTeam, 0));
        this.markSaveDataDirty();
    }

    /**
     * This packet will notify the players that this team is updated
     */
    public void sendTeamUpdate(ScorePlayerTeam playerTeam)
    {
        super.sendTeamUpdate(playerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(playerTeam, 2));
        this.markSaveDataDirty();
    }

    public void onTeamRemoved(ScorePlayerTeam playerTeam)
    {
        super.onTeamRemoved(playerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(playerTeam, 1));
        this.markSaveDataDirty();
    }

    public void func_96547_a(ScoreboardSaveData p_96547_1_)
    {
        this.scoreboardSaveData = p_96547_1_;
    }

    protected void markSaveDataDirty()
    {
        if (this.scoreboardSaveData != null)
        {
            this.scoreboardSaveData.markDirty();
        }
    }

    public List<Packet> getCreatePackets(ScoreObjective objective)
    {
        List<Packet> list = Lists.<Packet>newArrayList();
        list.add(new S3BPacketScoreboardObjective(objective, 0));

        for (int i = 0; i < 19; ++i)
        {
            if (this.getObjectiveInDisplaySlot(i) == objective)
            {
                list.add(new S3DPacketDisplayScoreboard(i, objective));
            }
        }

        for (Score score : this.getSortedScores(objective))
        {
            list.add(new S3CPacketUpdateScore(score));
        }

        return list;
    }

    public void addObjective(ScoreObjective objective)
    {
        List<Packet> list = this.getCreatePackets(objective);

        for (EntityPlayerMP entityplayermp : this.scoreboardMCServer.getConfigurationManager().getPlayers())
        {
            for (Packet packet : list)
            {
                entityplayermp.playerNetServerHandler.sendPacket(packet);
            }
        }

        this.addedObjectives.add(objective);
    }

    public List<Packet> getDestroyPackets(ScoreObjective p_96548_1_)
    {
        List<Packet> list = Lists.<Packet>newArrayList();
        list.add(new S3BPacketScoreboardObjective(p_96548_1_, 1));

        for (int i = 0; i < 19; ++i)
        {
            if (this.getObjectiveInDisplaySlot(i) == p_96548_1_)
            {
                list.add(new S3DPacketDisplayScoreboard(i, p_96548_1_));
            }
        }

        return list;
    }

    public void getPlayerIterator(ScoreObjective p_96546_1_)
    {
        List<Packet> list = this.getDestroyPackets(p_96546_1_);

        for (EntityPlayerMP entityplayermp : this.scoreboardMCServer.getConfigurationManager().getPlayers())
        {
            for (Packet packet : list)
            {
                entityplayermp.playerNetServerHandler.sendPacket(packet);
            }
        }

        this.addedObjectives.remove(p_96546_1_);
    }

    public int getObjectiveDisplaySlotCount(ScoreObjective p_96552_1_)
    {
        int i = 0;

        for (int j = 0; j < 19; ++j)
        {
            if (this.getObjectiveInDisplaySlot(j) == p_96552_1_)
            {
                ++i;
            }
        }

        return i;
    }
}
