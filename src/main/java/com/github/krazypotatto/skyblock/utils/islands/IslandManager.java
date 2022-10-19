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

    public Optional<Island> getIslandFromPlayer(UUID playerUUID){
        return islands.stream().filter(i -> i.getOwner().equals(playerUUID) || i.getMembers().contains(playerUUID)).findFirst();
    }

    public Optional<Island> getIsland(OfflinePlayer p){
        return getIslandFromPlayer(p.getUniqueId());
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
