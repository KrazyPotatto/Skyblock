package com.github.krazypotatto.skyblock.commands.island;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.commands.ICommandExecutor;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.queue.SyncTaskManager;
import com.github.krazypotatto.skyblock.utils.islands.IslandGenerationRunnable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
        if(pl.islandManager.getIsland(p).isPresent()
        || SyncTaskManager.getInstance().getTasks().stream().anyMatch(t -> t instanceof IslandGenerationRunnable && ((IslandGenerationRunnable) t).p.getUniqueId() == p.getUniqueId())) {
            pl.messages.sendLocatedMessage(p, "commands.island.in-island", MessagesConfigHandler.PrefixType.ERROR);
        }else{
            pl.messages.sendLocatedMessage(p, "commands.island.created.queued", MessagesConfigHandler.PrefixType.NONE);
            SyncTaskManager.getInstance().addTask(new IslandGenerationRunnable(pl, p));
        }
    }
}
