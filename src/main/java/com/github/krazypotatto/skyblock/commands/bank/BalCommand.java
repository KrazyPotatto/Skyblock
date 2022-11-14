package com.github.krazypotatto.skyblock.commands.bank;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.commands.ICommandExecutor;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import com.github.krazypotatto.skyblock.utils.serializables.parts.BankAccount;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BalCommand implements ICommandExecutor {
    @Override
    public String getPermission() {
        return "bal.show";
    }

    @Override
    public String getCommand() {
        return "";
    }

    @Override
    public void executeCommand(@NotNull Player p, @NotNull String[] args, @NotNull Skyblock pl) {
        Optional<Island> island;
        String playerName = "";
        if(args.length == 1){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            island = pl.islandManager.getIslandFromPlayer(offlinePlayer);
            playerName = offlinePlayer.getName();
        }else {
            island = pl.islandManager.getIslandFromPlayer(p);
        }
        if(island.isPresent()){
            Optional<BankAccount> account = pl.bank.getAccount(island.get().getIslandID());
            if(account.isPresent()){
                if(args.length == 1){
                    pl.messages.sendLocatedMessage(p, "commands.balance.bal.other", MessagesConfigHandler.PrefixType.DEFAULT, playerName, account.get().getBalance());
                }else {
                    pl.messages.sendLocatedMessage(p, "commands.balance.bal.show", MessagesConfigHandler.PrefixType.DEFAULT, account.get().getBalance());
                }
            }else {
                pl.messages.sendLocatedMessage(p, "commands.balance.no-account", MessagesConfigHandler.PrefixType.ERROR);
            }
        }else {
            pl.messages.sendLocatedMessage(p, "commands.balance.no-account", MessagesConfigHandler.PrefixType.ERROR);
        }
    }
}
