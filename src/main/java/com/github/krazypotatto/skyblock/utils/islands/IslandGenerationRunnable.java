package com.github.krazypotatto.skyblock.utils.islands;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.queue.SyncTaskManager;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class IslandGenerationRunnable extends BukkitRunnable {

    public Skyblock pl;
    public Player p;

    public IslandGenerationRunnable(Skyblock pl, Player p){
        this.pl = pl;
        this.p = p;
    }

    @Override
    public void run() {

        Location islandLocation = IslandGeneratorStrategy.getNextGridLocation(IslandGeneratorStrategy.getLastIslandLocation(pl), pl);
        Location spawn = pl.schematic.placeSchematic(islandLocation, pl);
        Island is = new Island(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ(), islandLocation.getBlockX(), islandLocation.getBlockZ(), p.getUniqueId(), UUID.randomUUID(), new ArrayList<>(), new ArrayList<>(), Bukkit.getWorlds().get(0).getUID());
        pl.getLogger().info(String.format("Generated island at coordinates: %.0f/%.0f/%.0f for player %s:%s", spawn.getX(), spawn.getY(), spawn.getZ(), p.getName(), p.getUniqueId()));
        try {
            is.saveData();
        } catch (IOException e) {
            pl.messages.sendLocatedMessage(p, "commands.island.created.error", MessagesConfigHandler.PrefixType.ERROR);
            throw new RuntimeException(e);
        }
        pl.islandManager.addIsland(is);
        pl.messages.sendLocatedMessage(p, "commands.island.created.success", MessagesConfigHandler.PrefixType.SUCCESS);
        p.teleport(is.getSpawn());

        SyncTaskManager.getInstance().taskEnded();

    }
}
