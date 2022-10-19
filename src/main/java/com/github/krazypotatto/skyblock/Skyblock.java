package com.github.krazypotatto.skyblock;

import com.github.krazypotatto.skyblock.commands.IslandCommand;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.islands.IslandManager;
import com.github.krazypotatto.skyblock.utils.schematics.SchematicReaderUtils;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import com.github.krazypotatto.skyblock.worldgen.VoidChunkGenerator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

public final class Skyblock extends JavaPlugin {

    public IslandManager islandManager;
    public MessagesConfigHandler messages;
    public SchematicReaderUtils schematic;

    @Override
    public void onEnable() {
        loadFiles();
        Objects.requireNonNull(getCommand("is")).setExecutor(new IslandCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
        return new VoidChunkGenerator();
    }

    private void loadFiles(){
        getLogger().info("Starting to load configurations files...");
        // Save the default config
        saveDefaultConfig();
        getLogger().info("Saved default config!");
        // Save the default schematic if it doesn't exist
        if(!new File(Paths.get(getDataFolder().getPath(), "schem.json").toUri()).exists())
            saveResource("schem.json", false);
        // Load the schematic to be used
        schematic = new SchematicReaderUtils("schem.json", this);
        getLogger().info("Loaded the schem.json file.");
        // Load all saved Islands
        islandManager = new IslandManager(Island.loadIslands(this));
        getLogger().info("Loaded " + islandManager.count() + " islands from files.");
        messages = new MessagesConfigHandler(this);
        getLogger().info("All config files were loaded successfully!");
    }

}
