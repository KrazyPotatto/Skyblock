package com.github.krazypotatto.skyblock.utils.serializables;

import com.github.krazypotatto.skyblock.Skyblock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Island extends AbstractSerializable<Island>{

    private long spawnX, spawnY, spawnZ;
    private UUID owner, islandID;
    private List<UUID> members, invites;

    public Island(String filename) {
        super(Island.class, new File(Paths.get(Skyblock.getInstance().getDataFolder().getPath(), "islands", filename).toUri()));
        try {
            loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Island(long spawnX, long spawnY, long spawnZ, UUID owner, UUID islandID, List<UUID> members, List<UUID> invites) {
        super(Island.class, new File(Paths.get(Skyblock.getInstance().getDataFolder().getPath(), "islands", islandID.toString() + ".json").toUri()));
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnZ = spawnZ;
        this.owner = owner;
        this.islandID = islandID;
        this.members = members;
        this.invites = invites;
    }

    @Override
    void applyValues(Island readClass) {
        this.islandID = readClass.islandID;
        this.owner = readClass.owner;
        this.spawnX = readClass.spawnX;
        this.spawnY = readClass.spawnY;
        this.spawnZ = readClass.spawnZ;
        this.members = readClass.members;
        this.invites = readClass.invites;
    }

    public long getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(long spawnX) {
        this.spawnX = spawnX;
    }

    public long getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(long spawnY) {
        this.spawnY = spawnY;
    }

    public long getSpawnZ() {
        return spawnZ;
    }

    public void setSpawnZ(long spawnZ) {
        this.spawnZ = spawnZ;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public UUID getIslandID() {
        return islandID;
    }

    public void setIslandID(UUID islandID) {
        this.islandID = islandID;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public List<UUID> getInvites() {
        return invites;
    }

    public void setInvites(List<UUID> invites) {
        this.invites = invites;
    }

    @Override
    public String toString() {
        return "Island{" +
                "spawnX=" + spawnX +
                ", spawnY=" + spawnY +
                ", spawnZ=" + spawnZ +
                ", owner=" + owner +
                ", islandID=" + islandID +
                ", members=" + members +
                ", invites=" + invites +
                '}';
    }

    public static ArrayList<Island> loadIslands(Skyblock pl){
        File file = new File(Paths.get(pl.getDataFolder().getPath(), "islands").toUri());
        if(!file.exists()) return new ArrayList<>();
        ArrayList<Island> list = new ArrayList<>();
        for(File f: Objects.requireNonNull(file.listFiles())){
            list.add(new Island(f.getName()));
        }
        return list;
    }

}
