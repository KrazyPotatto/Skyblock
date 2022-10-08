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

    public void placeSchematic(Location loc){
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
        }

    }

}
