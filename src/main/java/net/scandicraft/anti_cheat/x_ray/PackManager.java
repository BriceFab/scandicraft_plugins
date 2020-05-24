package net.scandicraft.anti_cheat.x_ray;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.scandicraft.Config;
import net.scandicraft.gui.cheat.CheatScreen;
import net.scandicraft.anti_cheat.CheatType;
import net.scandicraft.logs.LogManagement;

public class PackManager implements IResourceManagerReloadListener {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        LogManagement.info("check x-ray ressources packs..");

        boolean hasIllegal = AntiTransparency.checkTexturePack();
        if (hasIllegal) {
            CheatScreen.onDetectCheat(CheatType.XRAY);
        }
    }
}
