package com.github.krazypotatto.skyblock.utils.serializables.parts.rewards;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.islands.IslandManager;
import com.github.krazypotatto.skyblock.utils.serializables.Bank;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class MonetaryReward extends AbstractReward{

    private int amount;
    private boolean create;

    public MonetaryReward(int amount){
        this.amount = amount;
        this.create = true;
    }

    public MonetaryReward(int amount, boolean create){
        this.amount = amount;
        this.create = create;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    @Override
    void giveReward(Player player) {
        Skyblock pl = (Skyblock) JavaPlugin.getPlugin(Skyblock.class);
        Bank bank = pl.bank;
        IslandManager im = pl.islandManager;
        Optional<Island> is = im.getIslandFromPlayer(player.getUniqueId());
        if(is.isPresent()){
            bank.addCirculating(amount);
            boolean success = bank.deposit(is.get().getIslandID(), amount);
            if(!success)
                pl.messages.sendLocatedMessage(player, "rewards.errors.monetary.unknown-error", MessagesConfigHandler.PrefixType.ERROR);
        }else {
            pl.messages.sendLocatedMessage(player, "rewards.errors.monetary.not-in-island", MessagesConfigHandler.PrefixType.ERROR);
        }
    }
}
/*
 * Example of a MonetaryReward in JSON
 * {
 *   "amount": 500,
 *   "create": true
 * }
 */