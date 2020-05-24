package net.scandicraft.gui.cheat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.scandicraft.Config;
import net.scandicraft.anti_cheat.CheatType;
import net.scandicraft.logs.LogManagement;

import java.awt.*;
import java.io.IOException;

public class CheatScreen extends GuiScreen {

    private CheatType currentCheat;

    public CheatScreen(CheatType type) {
        LogManagement.info(String.format("cheat detected for %s..", Minecraft.getMinecraft().thePlayer.getName()));
        if (currentCheat == null) {
            this.currentCheat = type;
        }
    }

    public static void onDetectCheat(CheatType type) {
        Minecraft.getMinecraft().displayGuiScreen(new CheatScreen(type));
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 60, this.height / 2 + 30, 120, 20, "J'ai compris"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, currentCheat.getMessage(), this.width / 2, 80, Color.RED.getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void onGuiClosed() {
        if (Config.ENV == Config.ENVIRONNEMENT.PROD) {
            LogManagement.info("exit for cheating..");
            this.mc.shutdown();
        }
    }
}
