package net.scandicraft.scheduler;

import java.util.concurrent.*;

public class Scheduler {
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    final ExecutorService executor = Executors.newFixedThreadPool(1);

    public void execute() {
        executor.submit(scheduleTask());
    }

    public Runnable scheduleTask() {
        return new Runnable() {
            public void run() {
                final ScheduledFuture cancel = scheduler.scheduleAtFixedRate(new Runnable() {
                    public void run() {
                        // do work here

                    }
                }, 0, 1, TimeUnit.SECONDS);
                scheduler.schedule(new Runnable() {
                    public void run() {
                        cancel.cancel(true);
                        execute();
                    }
                }, 1, TimeUnit.MINUTES);
            }
        };
    }
}
