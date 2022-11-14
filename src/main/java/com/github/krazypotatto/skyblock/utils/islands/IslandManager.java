package com.github.krazypotatto.skyblock.utils.islands;

import com.github.krazypotatto.skyblock.utils.serializables.Island;
import org.bukkit.OfflinePlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class IslandManager {

    private final ArrayList<Island> islands;

    public IslandManager(ArrayList<Island> islands){
        this.islands = islands;
    }

    public Optional<Island> getIsland(UUID islandUUID){
        return islands.stream().filter(i -> i.getIslandID() == islandUUID).findFirst();
    }

    public boolean isOwner(UUID playerUUID){
        return islands.stream().anyMatch(i -> i.getOwner().equals(playerUUID));
    }

    public Optional<Island> getIslandFromPlayer(UUID playerUUID){
        return islands.stream().filter(i -> i.getOwner().equals(playerUUID) || i.getMembers().contains(playerUUID)).findFirst();
    }

    public Optional<Island> getIslandFromPlayer(OfflinePlayer p){
        return getIslandFromPlayer(p.getUniqueId());
    }

    public Optional<UUID> getIslandUUID(OfflinePlayer p){
        return getIslandFromPlayer(p.getUniqueId()).map(Island::getIslandID);
    }

    public void addIsland(Island is){
        islands.add(is);
    }

    public void saveAll(){
        islands.forEach(i -> {
            try {
                i.saveData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public int count(){ return islands.size(); }

}
