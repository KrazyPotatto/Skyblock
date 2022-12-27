package com.github.krazypotatto.skyblock.utils.schematics;

import com.github.krazypotatto.skyblock.Skyblock;
import com.google.common.reflect.TypeToken;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SchematicReaderUtils {

    private List<SchematicPart> parts;

    public SchematicReaderUtils(String filename, Skyblock pl) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(pl.getDataFolder().getPath(), filename));


            parts = Skyblock.gsonGlobal().fromJson(reader, new TypeToken<List<SchematicPart>>() {}.getType());

            reader.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /***
     * Place the schematic and returns to spawn Location
     * @param loc
     * @return
     */
    public Location placeSchematic(Location loc, Skyblock pl){
        Location spawn = new Location(loc.getWorld(), 0, 0, 0);
        for(SchematicPart part: parts){
            if(part.type.equalsIgnoreCase("block")){
                loc.getWorld().getBlockAt(loc.getBlockX() + part.x,
                        loc.getBlockY() + part.y, loc.getBlockZ() + part.z)
                        .setType(Material.valueOf(part.block.toUpperCase()));
            }
            if(part.type.equalsIgnoreCase("tree")){
                loc.getWorld().generateTree(new Location(loc.getWorld(), loc.getBlockX() + part.x,
                        loc.getBlockY() + part.y, loc.getBlockZ() + part.z), TreeType.valueOf(part.tree.toUpperCase()));
            }
            if(part.type.equalsIgnoreCase("spawn")){
                spawn.setX(part.x + loc.getBlockX());
                spawn.setY(part.y + loc.getBlockY());
                spawn.setZ(part.z + loc.getBlockZ());
            }
        }
        pl.getConfig().set("islands.last.x", loc.getBlockX());
        pl.getConfig().set("islands.last.y", loc.getBlockY());
        pl.getConfig().set("islands.last.z", loc.getBlockZ());
        pl.saveConfig();
        return spawn;
    }

}
