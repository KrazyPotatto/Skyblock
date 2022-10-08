package com.github.krazypotatto.skyblock.commands;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IslandCommand implements CommandExecutor {

    private Skyblock pl;

    public IslandCommand(Skyblock pl){
        this.pl = pl;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            pl.messages.sendLocatedMessage(sender, "commands.not-console", MessagesConfigHandler.PrefixType.ERROR);
            return true;
        }
        Player p = (Player) sender;
        pl.schematic.placeSchematic(p.getLocation().add(5, 10, 5));
        return true;
    }

}
