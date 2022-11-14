package com.github.krazypotatto.skyblock.commands.island;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.commands.ICommandExecutor;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class InviteCommand implements ICommandExecutor {

    @Override
    public String getPermission() {
        return "is.invite";
    }

    @Override
    public String getCommand() {
        return "invite";
    }

    @Override
    public void executeCommand(@NotNull Player p, @NotNull String[] args, @NotNull Skyblock pl) {
        Optional<Island> is = pl.islandManager.getIslandFromPlayer(p);
        if(is.isPresent()){
            Island island = is.get();
            if(island.getOwner().equals(p.getUniqueId())){
                OfflinePlayer invite = Bukkit.getOfflinePlayerIfCached(args[0]);
                if(invite != null){
                    if(pl.islandManager.getIslandFromPlayer(invite).isPresent()){
                        pl.messages.sendLocatedMessage(p, "commands.island.invite.already-in-island", MessagesConfigHandler.PrefixType.ERROR, invite.getName());
                        return;
                    }
                    if(island.getInvites().contains(invite.getUniqueId())){
                        island.getInvites().remove(invite.getUniqueId());
                        pl.messages.sendLocatedMessage(p, "commands.island.invite.sent-revocation", MessagesConfigHandler.PrefixType.SUCCESS, invite.getName());
                        if(invite.isOnline())
                            pl.messages.sendLocatedMessage(invite.getPlayer(), "commands.island.invite.revoked", MessagesConfigHandler.PrefixType.ERROR, p.getName());
                    }else{
                        island.getInvites().add(invite.getUniqueId());
                        pl.messages.sendLocatedMessage(p, "commands.island.invite.sent", MessagesConfigHandler.PrefixType.SUCCESS, invite.getName());
                        if(invite.isOnline())
                            pl.messages.sendLocatedMessage(invite.getPlayer(), "commands.island.invite.received", MessagesConfigHandler.PrefixType.SUCCESS, p.getName());
                    }
                    try {
                        island.saveData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    pl.messages.sendLocatedMessage(p, "commands.player-not-found", MessagesConfigHandler.PrefixType.ERROR);
                }
            }else{
                pl.messages.sendLocatedMessage(p, "commands.no-permission", MessagesConfigHandler.PrefixType.ERROR);
            }
        } else {
            pl.messages.sendLocatedMessage(p, "commands.island.no-island", MessagesConfigHandler.PrefixType.ERROR);
        }
    }
}
