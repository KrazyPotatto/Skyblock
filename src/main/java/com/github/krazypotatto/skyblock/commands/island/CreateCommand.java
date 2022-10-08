package com.github.krazypotatto.skyblock.commands.island;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.commands.ICommandExecutor;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class CreateCommand implements ICommandExecutor {
    @Override
    public String getPermission() {
        return "is.create";
    }

    @Override
    public String getCommand() {
        return "create";
    }

    @Override
    public void executeCommand(@NotNull Player p, @NotNull String[] args, @NotNull Skyblock pl) {
        Location spawn = pl.schematic.placeSchematic(p.getLocation().add(10 , 5, 10));
        Island is = new Island(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ(), p.getUniqueId(), UUID.randomUUID(), new ArrayList<>(), new ArrayList<>());
        try {
            is.saveData();
        } catch (IOException e) {
            pl.messages.sendLocatedMessage(p, "commands.island.created.error", MessagesConfigHandler.PrefixType.ERROR);
            throw new RuntimeException(e);
        }
        pl.islands.add(is);
        pl.messages.sendLocatedMessage(p, "commands.island.created.success", MessagesConfigHandler.PrefixType.SUCCESS);
    }
}
