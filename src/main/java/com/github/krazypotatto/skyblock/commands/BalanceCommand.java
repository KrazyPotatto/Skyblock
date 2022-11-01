package com.github.krazypotatto.skyblock.commands;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.commands.bank.BalCommand;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BalanceCommand implements CommandExecutor {

    private Skyblock pl;
    private final List<ICommandExecutor> commands = new ArrayList<>();
    private final ICommandExecutor commandFallback = new BalCommand();

    public BalanceCommand(Skyblock pl){
        this.pl = pl;

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            pl.messages.sendLocatedMessage(sender, "commands.not-console", MessagesConfigHandler.PrefixType.ERROR);
            return true;
        }
        Player p = (Player) sender;
        if(args.length < 1){
            commandFallback.executeCommand(p, new String[]{}, pl);
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
            if(Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()){
                commandFallback.executeCommand(p, args, pl);
            }else {
                pl.messages.sendLocatedMessage(p, "commands.not-found", MessagesConfigHandler.PrefixType.ERROR);
            }
        }
    }

}
