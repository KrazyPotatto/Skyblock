package com.github.krazypotatto.skyblock.utils.queue;

import org.bukkit.scheduler.BukkitRunnable;

public class SyncTaskManagerRunnable extends BukkitRunnable {

    @Override
    public void run() {
        SyncTaskManager.getInstance().runNextTask();
    }

}
