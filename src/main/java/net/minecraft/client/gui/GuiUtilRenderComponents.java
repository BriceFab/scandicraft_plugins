package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GuiUtilRenderComponents
{
    public static String removeTextColorsIfConfigured(String text, boolean forceColor)
    {
        return !forceColor && !Minecraft.getMinecraft().gameSettings.chatColours ? EnumChatFormatting.getTextWithoutFormattingCodes(text) : text;
    }

    public static List<IChatComponent> splitText(IChatComponent textComponent, int maxTextLenght, FontRenderer fontRendererIn, boolean trimSpace, boolean forceTextColor)
    {
        int i = 0;
        IChatComponent ichatcomponent = new ChatComponentText("");
        List<IChatComponent> list = Lists.<IChatComponent>newArrayList();
        List<IChatComponent> list1 = Lists.newArrayList(textComponent);

        for (int j = 0; j < ((List)list1).size(); ++j)
        {
            IChatComponent ichatcomponent1 = (IChatComponent)list1.get(j);
            String s = ichatcomponent1.getUnformattedTextForChat();
            boolean flag = false;

            if (s.contains("\n"))
            {
                int k = s.indexOf(10);
                String s1 = s.substring(k + 1);
                s = s.substring(0, k + 1);
                ChatComponentText chatcomponenttext = new ChatComponentText(s1);
                chatcomponenttext.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
                list1.add(j + 1, chatcomponenttext);
                flag = true;
            }

            String s4 = removeTextColorsIfConfigured(ichatcomponent1.getChatStyle().getFormattingCode() + s, forceTextColor);
            String s5 = s4.endsWith("\n") ? s4.substring(0, s4.length() - 1) : s4;
            int i1 = fontRendererIn.getStringWidth(s5);
            ChatComponentText chatcomponenttext1 = new ChatComponentText(s5);
            chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());

            if (i + i1 > maxTextLenght)
            {
                String s2 = fontRendererIn.trimStringToWidth(s4, maxTextLenght - i, false);
                String s3 = s2.length() < s4.length() ? s4.substring(s2.length()) : null;

                if (s3 != null && s3.length() > 0)
                {
                    int l = s2.lastIndexOf(" ");

                    if (l >= 0 && fontRendererIn.getStringWidth(s4.substring(0, l)) > 0)
                    {
                        s2 = s4.substring(0, l);

                        if (trimSpace)
                        {
                            ++l;
                        }

                        s3 = s4.substring(l);
                    }
                    else if (i > 0 && !s4.contains(" "))
                    {
                        s2 = "";
                        s3 = s4;
                    }

                    ChatComponentText chatcomponenttext2 = new ChatComponentText(s3);
                    chatcomponenttext2.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
                    list1.add(j + 1, chatcomponenttext2);
                }

                i1 = fontRendererIn.getStringWidth(s2);
                chatcomponenttext1 = new ChatComponentText(s2);
                chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
                flag = true;
            }

            if (i + i1 <= maxTextLenght)
            {
                i += i1;
                ichatcomponent.appendSibling(chatcomponenttext1);
            }
            else
            {
                flag = true;
            }

            if (flag)
            {
                list.add(ichatcomponent);
                i = 0;
                ichatcomponent = new ChatComponentText("");
            }
        }

        list.add(ichatcomponent);
        return list;
    }
}
