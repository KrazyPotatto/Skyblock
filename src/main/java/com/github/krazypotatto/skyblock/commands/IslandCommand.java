package com.github.krazypotatto.skyblock.commands;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.commands.island.*;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class IslandCommand implements CommandExecutor {

    private final Skyblock pl;
    private final List<ICommandExecutor> commands = new ArrayList<>();

    public IslandCommand(Skyblock pl){
        this.pl = pl;
        commands.add(new CreateCommand());
        commands.add(new LeaveCommand());
        commands.add(new HomeCommand());
        commands.add(new InviteCommand());
        commands.add(new JoinCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            pl.messages.sendLocatedMessage(sender, "commands.not-console", MessagesConfigHandler.PrefixType.ERROR);
            return true;
        }
        Player p = (Player) sender;
        if(args.length < 1){
            pl.messages.sendLocatedMessage(p, "commands.not-found", MessagesConfigHandler.PrefixType.ERROR);
        } else {
            dispatchAction(p, args, args[0]);
        }
        return true;
    }

    public void dispatchAction(@NotNull Player p, @NotNull String[] args, @NotNull String command){
        Optional<ICommandExecutor> found = commands.stream().filter(c -> c.getCommand().equalsIgnoreCase(command)).findFirst();
        if(found.isPresent()){
            if(p.hasPermission(found.get().getPermission())) {
                found.get().executeCommand(p, Arrays.copyOfRange(args, 1, args.length), pl);
            } else {
                pl.messages.sendLocatedMessage(p, "commands.no-permission", MessagesConfigHandler.PrefixType.ERROR);
            }
        } else {
            pl.messages.sendLocatedMessage(p, "commands.not-found", MessagesConfigHandler.PrefixType.ERROR);
        }
    }
}
