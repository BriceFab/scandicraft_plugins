package net.minecraft.client.network;

import com.google.common.base.Objects;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;

public class NetworkPlayerInfo
{
    /**
     * The GameProfile for the player represented by this NetworkPlayerInfo instance
     */
    private final GameProfile gameProfile;
    private WorldSettings.GameType gameType;

    /** Player response time to server in milliseconds */
    private int responseTime;
    private boolean playerTexturesLoaded = false;
    private ResourceLocation locationSkin;
    private ResourceLocation locationCape;
    private String skinType;

    /**
     * When this is non-null, it is displayed instead of the player's real name
     */
    private IChatComponent displayName;
    private int lastHealth = 0;
    private int displayHealth = 0;
    private long lastHealthTime = 0L;
    private long healthBlinkTime = 0L;
    private long renderVisibilityId = 0L;

    public NetworkPlayerInfo(GameProfile p_i46294_1_)
    {
        this.gameProfile = p_i46294_1_;
    }

    public NetworkPlayerInfo(S38PacketPlayerListItem.AddPlayerData p_i46295_1_)
    {
        this.gameProfile = p_i46295_1_.getProfile();
        this.gameType = p_i46295_1_.getGameMode();
        this.responseTime = p_i46295_1_.getPing();
        this.displayName = p_i46295_1_.getDisplayName();
    }

    /**
     * Returns the GameProfile for the player represented by this NetworkPlayerInfo instance
     */
    public GameProfile getGameProfile()
    {
        return this.gameProfile;
    }

    public WorldSettings.GameType getGameType()
    {
        return this.gameType;
    }

    public int getResponseTime()
    {
        return this.responseTime;
    }

    protected void setGameType(WorldSettings.GameType gameMode)
    {
        this.gameType = gameMode;
    }

    protected void setResponseTime(int latency)
    {
        this.responseTime = latency;
    }

    public boolean hasLocationSkin()
    {
        return this.locationSkin != null;
    }

    public String getSkinType()
    {
        return this.skinType == null ? DefaultPlayerSkin.getSkinType(this.gameProfile.getId()) : this.skinType;
    }

    public ResourceLocation getLocationSkin()
    {
        if (this.locationSkin == null)
        {
            this.loadPlayerTextures();
        }

        return (ResourceLocation)Objects.firstNonNull(this.locationSkin, DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
    }

    public ResourceLocation getLocationCape()
    {
        if (this.locationCape == null)
        {
            this.loadPlayerTextures();
        }

        return this.locationCape;
    }

    public ScorePlayerTeam getPlayerTeam()
    {
        return Minecraft.getMinecraft().theWorld.getScoreboard().getPlayersTeam(this.getGameProfile().getName());
    }

    protected void loadPlayerTextures()
    {
        synchronized (this)
        {
            if (!this.playerTexturesLoaded)
            {
                this.playerTexturesLoaded = true;
                Minecraft.getMinecraft().getSkinManager().loadProfileTextures(this.gameProfile, new SkinManager.SkinAvailableCallback()
                {
                    public void skinAvailable(Type p_180521_1_, ResourceLocation location, MinecraftProfileTexture profileTexture)
                    {
                        switch (p_180521_1_)
                        {
                            case SKIN:
                                NetworkPlayerInfo.this.locationSkin = location;
                                NetworkPlayerInfo.this.skinType = profileTexture.getMetadata("model");

                                if (NetworkPlayerInfo.this.skinType == null)
                                {
                                    NetworkPlayerInfo.this.skinType = "default";
                                }

                                break;

                            case CAPE:
                                NetworkPlayerInfo.this.locationCape = location;
                        }
                    }
                }, true);
            }
        }
    }

    public void setDisplayName(IChatComponent displayNameIn)
    {
        this.displayName = displayNameIn;
    }

    public IChatComponent getDisplayName()
    {
        return this.displayName;
    }

    public int getLastHealth()
    {
        return this.lastHealth;
    }

    public void setLastHealth(int p_178836_1_)
    {
        this.lastHealth = p_178836_1_;
    }

    public int getDisplayHealth()
    {
        return this.displayHealth;
    }

    public void setDisplayHealth(int p_178857_1_)
    {
        this.displayHealth = p_178857_1_;
    }

    public long getLastHealthTime()
    {
        return this.lastHealthTime;
    }

    public void setLastHealthTime(long p_178846_1_)
    {
        this.lastHealthTime = p_178846_1_;
    }

    public long getHealthBlinkTime()
    {
        return this.healthBlinkTime;
    }

    public void setHealthBlinkTime(long p_178844_1_)
    {
        this.healthBlinkTime = p_178844_1_;
    }

    public long getRenderVisibilityId()
    {
        return this.renderVisibilityId;
    }

    public void setRenderVisibilityId(long p_178843_1_)
    {
        this.renderVisibilityId = p_178843_1_;
    }
}
