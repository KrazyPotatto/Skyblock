package com.github.krazypotatto.skyblock.utils.islands;

import com.github.krazypotatto.skyblock.Skyblock;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class IslandGeneratorStrategy {

    public static Location getLastIslandLocation(final Skyblock pl){
        return new Location(Bukkit.getWorlds().get(0),
                pl.getConfig().getInt("islands.last.x"),
                pl.getConfig().getInt("islands.last.y"),
                pl.getConfig().getInt("islands.last.z"));
    }

    public static Location getNextGridLocation(final Location lastIsland, final Skyblock pl){
        int x = lastIsland.getBlockX();
        int z = lastIsland.getBlockZ();
        int d = pl.getConfig().getInt("islands.spacing") * 2;
        if (x < z) {
            if (-1 * x < z) {
                lastIsland.setX(lastIsland.getX() + d);
                return lastIsland;
            }
            lastIsland.setZ(lastIsland.getZ() + d);
            return lastIsland;
        }
        if (x > z) {
            if (-1 * x >= z) {
                lastIsland.setX(lastIsland.getX() - d);
                return lastIsland;
            }
            lastIsland.setZ(lastIsland.getZ() - d);
            return lastIsland;
        }
        if (x <= 0) {
            lastIsland.setZ(lastIsland.getZ() + d);
            return lastIsland;
        }
        lastIsland.setZ(lastIsland.getZ() - d);
        return lastIsland;
    }

}
