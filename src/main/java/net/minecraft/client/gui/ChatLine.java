package net.minecraft.client.gui;

import net.minecraft.util.IChatComponent;

public class ChatLine
{
    /** GUI Update Counter value this Line was created at */
    private final int updateCounterCreated;
    private final IChatComponent lineString;

    /**
     * int value to refer to existing Chat Lines, can be 0 which means unreferrable
     */
    private final int chatLineID;

    public ChatLine(int updateCounterCreatedIn, IChatComponent lineStringIn, int chatLineIDIn)
    {
        this.lineString = lineStringIn;
        this.updateCounterCreated = updateCounterCreatedIn;
        this.chatLineID = chatLineIDIn;
    }

    public IChatComponent getChatComponent()
    {
        return this.lineString;
    }

    public int getUpdatedCounter()
    {
        return this.updateCounterCreated;
    }

    public int getChatLineID()
    {
        return this.chatLineID;
    }
}
