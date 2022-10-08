package com.github.krazypotatto.skyblock.commands.island;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.commands.ICommandExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

    }
}
