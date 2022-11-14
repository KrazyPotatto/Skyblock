package com.github.krazypotatto.skyblock.commands.island;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.commands.ICommandExecutor;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class LeaveCommand implements ICommandExecutor {
    @Override
    public String getPermission() {
        return "is.leave";
    }

    @Override
    public String getCommand() {
        return "leave";
    }

    @Override
    public void executeCommand(@NotNull Player p, @NotNull String[] args, @NotNull Skyblock pl) {
        Optional<Island> is = pl.islandManager.getIslandFromPlayer(p);
        if(!is.isPresent()){
            pl.messages.sendLocatedMessage(p, "commands.island.leave.not-in-island", MessagesConfigHandler.PrefixType.ERROR);
            return;
        }
        Island island = is.get();
        if(island.getOwner().equals(p.getUniqueId())){
            pl.messages.sendLocatedMessage(p, "commands.island.leave.owner", MessagesConfigHandler.PrefixType.ERROR);
            return;
        }
        island.getMembers().remove(p.getUniqueId());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(island.getSpawn().getWorld()));
        if (regions != null) {
            Objects.requireNonNull(regions.getRegion(island.getIslandID().toString())).getMembers().removePlayer(p.getUniqueId());
        }
        pl.messages.sendLocatedMessage(p, "commands.island.leave.success", MessagesConfigHandler.PrefixType.SUCCESS);
    }
}
