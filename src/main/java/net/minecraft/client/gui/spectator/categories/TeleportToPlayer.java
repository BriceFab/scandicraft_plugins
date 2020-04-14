package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.PlayerMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject
{
    private static final Ordering<NetworkPlayerInfo> PROFILE_ORDER = Ordering.from(new Comparator<NetworkPlayerInfo>()
    {
        public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_)
        {
            return ComparisonChain.start().compare(p_compare_1_.getGameProfile().getId(), p_compare_2_.getGameProfile().getId()).result();
        }
    });
    private final List<ISpectatorMenuObject> items;

    public TeleportToPlayer()
    {
        this(PROFILE_ORDER.<NetworkPlayerInfo>sortedCopy(Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()));
    }

    public TeleportToPlayer(Collection<NetworkPlayerInfo> profiles)
    {
        this.items = Lists.<ISpectatorMenuObject>newArrayList();

        for (NetworkPlayerInfo networkplayerinfo : PROFILE_ORDER.sortedCopy(profiles))
        {
            if (networkplayerinfo.getGameType() != WorldSettings.GameType.SPECTATOR)
            {
                this.items.add(new PlayerMenuObject(networkplayerinfo.getGameProfile()));
            }
        }
    }

    public List<ISpectatorMenuObject> getItems()
    {
        return this.items;
    }

    public IChatComponent getPrompt()
    {
        return new ChatComponentText("Select a player to teleport to");
    }

    public void selectItem(SpectatorMenu menu)
    {
        menu.selectCategory(this);
    }

    public IChatComponent getSpectatorName()
    {
        return new ChatComponentText("Teleport to player");
    }

    public void renderIcon(float brightness, int alpha)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.SPECTATOR_WIDGETS);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 16, 16, 256.0F, 256.0F);
    }

    public boolean isEnabled()
    {
        return !this.items.isEmpty();
    }
}
