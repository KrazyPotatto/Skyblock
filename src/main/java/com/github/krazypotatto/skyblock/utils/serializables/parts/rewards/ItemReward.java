package com.github.krazypotatto.skyblock.utils.serializables.parts.rewards;

import com.github.krazypotatto.skyblock.utils.serializables.parts.SerializableItemStack;
import org.bukkit.entity.Player;

public class ItemReward extends AbstractReward{

    private SerializableItemStack item;

    public ItemReward() {
    }

    public ItemReward(SerializableItemStack item) {
        this.item = item;
    }

    public SerializableItemStack getItem() {
        return item;
    }

    public void setItem(SerializableItemStack item) {
        this.item = item;
    }

    @Override
    void giveReward(Player player) {
        player.getInventory().addItem(item.toItemStack());
    }
}
