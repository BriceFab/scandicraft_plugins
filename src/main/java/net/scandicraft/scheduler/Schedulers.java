package net.scandicraft.scheduler;

import net.minecraft.util.Util;
import net.scandicraft.config.Config;
import net.scandicraft.anti_cheat.CheatConfig;
import net.scandicraft.anti_cheat.process.ScanProcess;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Schedulers {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();    //accepte que 1 scheduler

    public void registerAll() {
        registerScanProcess();
    }

    public void registerScanProcess() {
        if (Util.getOSType() == Util.EnumOS.WINDOWS && Config.ENV != Config.ENVIRONNEMENT.DEV) {
            scheduler.scheduleAtFixedRate(new ScanProcess(), CheatConfig.initialDelay, CheatConfig.periodicDelay, TimeUnit.SECONDS);
        }
    }

}
