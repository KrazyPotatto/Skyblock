package com.github.krazypotatto.skyblock.utils.queue;

import com.github.krazypotatto.skyblock.Skyblock;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SyncTaskManager {

    private static SyncTaskManager instance;

    public static SyncTaskManager getInstance(){
        if(instance == null){
            instance = new SyncTaskManager();
            // Run the next task every 5 seconds
            new SyncTaskManagerRunnable().runTaskTimerAsynchronously(instance.pl, 20L, 5 * 20L);
        }
        return instance;
    }

    public boolean isQueueRunning = false;

    private final ArrayList<BukkitRunnable> tasks;
    private final Skyblock pl;

    private SyncTaskManager(){
        pl = JavaPlugin.getPlugin(Skyblock.class);
        tasks = new ArrayList<>();
    }

    public void addTask(BukkitRunnable runnable){
        tasks.add(runnable);
        if(!isQueueRunning) runNextTask();
    }

    public void runNextTask(){
        if(tasks.size() <= 0 || isQueueRunning) return;
        isQueueRunning = true;
        pl.getLogger().info("Running next task in the SyncTaskManager");
        tasks.get(0).runTask(pl);
    }

    public void taskEnded(){
        tasks.remove(0);
        isQueueRunning = false;
    }

    public List<BukkitRunnable> getTasks(){ return tasks; }

}
