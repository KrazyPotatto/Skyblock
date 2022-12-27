package com.github.krazypotatto.skyblock.utils.islands;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.queue.SyncTaskManager;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import com.github.krazypotatto.skyblock.utils.serializables.parts.BankAccount;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
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
        pl.bank.getAccounts().add(new BankAccount(is.getIslandID(), 250));
        pl.bank.addCirculating(5000);
        pl.bank.addAllocated(250);
        pl.messages.sendLocatedMessage(p, "commands.island.created.success", MessagesConfigHandler.PrefixType.SUCCESS);
        p.teleport(is.getSpawn());

        int spacing = pl.getConfig().getInt("islands.spacing");

        BlockVector3 min = BlockVector3.at(is.getCenterX() - spacing, -64, is.getCenterZ() - spacing);
        BlockVector3 max = BlockVector3.at(is.getCenterX() + spacing, 319, is.getCenterZ() + spacing);
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(is.getIslandID().toString(), min, max);
        region.getMembers().addPlayer(p.getUniqueId());
        region.setFlag(Flags.PVP, StateFlag.State.DENY);
        region.setFlag(Flags.TNT, StateFlag.State.ALLOW);
        region.setFlag(Flags.DESTROY_VEHICLE, StateFlag.State.ALLOW);
        region.setFlag(Flags.PLACE_VEHICLE, StateFlag.State.ALLOW);
        region.setFlag(Flags.MOB_SPAWNING, StateFlag.State.ALLOW);
        region.setFlag(Flags.FALL_DAMAGE, StateFlag.State.ALLOW);

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(spawn.getWorld()));
        if (regions != null) {
            regions.addRegion(region);
            try {
                regions.save();
            } catch (StorageException e) {
                throw new RuntimeException(e);
            }
        }else{
            pl.getLogger().severe("Impossible to register WorldGuard region");
        }

        SyncTaskManager.getInstance().taskEnded();

    }
}
