package com.github.krazypotatto.skyblock.commands.island;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.commands.ICommandExecutor;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HomeCommand implements ICommandExecutor {

    @Override
    public String getPermission() {
        return "is.home";
    }

    @Override
    public String getCommand() {
        return "home";
    }

    @Override
    public void executeCommand(@NotNull Player p, @NotNull String[] args, @NotNull Skyblock pl) {
        Optional<Island> is = pl.islands.stream().filter(i -> i.getOwner().equals(p.getUniqueId())).findFirst();
        is.ifPresent(island -> {
            p.teleport(island.getSpawn());
            pl.messages.sendLocatedMessage(p, "commands.island.home", MessagesConfigHandler.PrefixType.SUCCESS);
        });
    }
}
