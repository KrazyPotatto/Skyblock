package com.github.krazypotatto.skyblock;

import com.github.krazypotatto.skyblock.commands.IslandCommand;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.schematics.SchematicReaderUtils;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import com.github.krazypotatto.skyblock.worldgen.VoidChunkGenerator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

public final class Skyblock extends JavaPlugin {

    private static Skyblock instance;

    public ArrayList<Island> islands = new ArrayList<>();
    public MessagesConfigHandler messages = new MessagesConfigHandler(this);
    public SchematicReaderUtils schematic;

    @Override
    public void onEnable() {
        instance = this;
        Objects.requireNonNull(getCommand("is")).setExecutor(new IslandCommand(this));
        saveDefaultConfig();
        saveResource("schem.json", false);
        schematic = new SchematicReaderUtils("schem.json", this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
        return new VoidChunkGenerator();
    }

    public static Skyblock getInstance(){ return instance; }

}
