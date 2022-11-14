package com.github.krazypotatto.skyblock.commands.island;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.commands.ICommandExecutor;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class JoinCommand implements ICommandExecutor {
    @Override
    public String getPermission() {
        return "is.join";
    }

    @Override
    public String getCommand() {
        return "join";
    }

    @Override
    public void executeCommand(@NotNull Player p, @NotNull String[] args, @NotNull Skyblock pl) {
        Optional<Island> playerIsland = pl.islandManager.getIslandFromPlayer(p);
        if(playerIsland.isPresent()){
            pl.messages.sendLocatedMessage(p, "commands.island.join.in-island", MessagesConfigHandler.PrefixType.ERROR);
           return;
        }
        if(args.length < 1){
            pl.messages.sendLocatedMessage(p, "commands.island.join.specify", MessagesConfigHandler.PrefixType.ERROR);
            return;
        }
        OfflinePlayer owner = Bukkit.getOfflinePlayerIfCached(args[0]);
        if(owner == null || !pl.islandManager.isOwner(owner.getUniqueId())){
            pl.messages.sendLocatedMessage(p, "commands.island.join.not-found", MessagesConfigHandler.PrefixType.ERROR);
            return;
        }
        Island is = pl.islandManager.getIslandFromPlayer(owner).get();
        if(!is.getInvites().contains(p.getUniqueId())){
            pl.messages.sendLocatedMessage(p, "commands.island.join.not-invited", MessagesConfigHandler.PrefixType.ERROR);
            return;
        }
        pl.messages.sendLocatedMessage(p, "commands.island.join.success", MessagesConfigHandler.PrefixType.SUCCESS, owner.getName());
        is.getInvites().remove(p.getUniqueId());
        is.getMembers().add(p.getUniqueId());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(is.getSpawn().getWorld()));
        if (regions != null) {
            Objects.requireNonNull(regions.getRegion(is.getIslandID().toString())).getMembers().addPlayer(p.getUniqueId());
        }
        try {
            is.saveData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
