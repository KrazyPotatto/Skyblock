package com.github.krazypotatto.skyblock.utils.schematics;

import com.github.krazypotatto.skyblock.Skyblock;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
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
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(pl.getDataFolder().getPath(), filename));

            parts = gson.fromJson(reader, new TypeToken<List<SchematicPart>>() {
            }.getType());
            parts.forEach(System.out::println);

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
    public Location placeSchematic(Location loc){
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
        return spawn;
    }

}
