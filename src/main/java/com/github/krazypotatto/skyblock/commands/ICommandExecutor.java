package com.github.krazypotatto.skyblock.commands;

import com.github.krazypotatto.skyblock.Skyblock;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ICommandExecutor {

    String getPermission();
    String getCommand();
    void executeCommand(@NotNull Player p, @NotNull String[] args, @NotNull Skyblock pl);

}
