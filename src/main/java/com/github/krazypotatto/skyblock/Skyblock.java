package com.github.krazypotatto.skyblock;

import com.github.krazypotatto.skyblock.api.impl.VaultEconomyConnector;
import com.github.krazypotatto.skyblock.commands.BalanceCommand;
import com.github.krazypotatto.skyblock.commands.IslandCommand;
import com.github.krazypotatto.skyblock.utils.MessagesConfigHandler;
import com.github.krazypotatto.skyblock.utils.islands.IslandManager;
import com.github.krazypotatto.skyblock.utils.schematics.SchematicReaderUtils;
import com.github.krazypotatto.skyblock.utils.serializables.Bank;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import com.github.krazypotatto.skyblock.utils.serializables.parts.rewards.AbstractReward;
import com.github.krazypotatto.skyblock.utils.serializables.parts.rewards.ItemReward;
import com.github.krazypotatto.skyblock.utils.serializables.parts.rewards.MonetaryReward;
import com.github.krazypotatto.skyblock.worldgen.VoidChunkGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public final class Skyblock extends JavaPlugin {

    private static Gson gsonInstance;

    public static Gson gsonGlobal(){
        if(gsonInstance == null){
            RuntimeTypeAdapterFactory<AbstractReward> rewardGsonAdapter =
                    RuntimeTypeAdapterFactory.of(AbstractReward.class)
                            .registerSubtype(ItemReward.class)
                            .registerSubtype(MonetaryReward.class);
            gsonInstance = new GsonBuilder().registerTypeAdapterFactory(rewardGsonAdapter).setPrettyPrinting().create();
        }
        return gsonInstance;
    }

    public IslandManager islandManager;
    public MessagesConfigHandler messages;
    public SchematicReaderUtils schematic;

    public Bank bank;

    @Override
    public void onEnable() {

        loadFiles();
        registerEconomy();
        Objects.requireNonNull(getCommand("is")).setExecutor(new IslandCommand(this));
        Objects.requireNonNull(getCommand("bal")).setExecutor(new BalanceCommand(this));
    }

    @Override
    public void onDisable() {
        try {
            this.bank.saveData();
            this.islandManager.saveAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Plugin shutdown logic
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, String id){
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
        if((new File(Paths.get(getDataFolder().getPath(), "bank.json").toUri())).exists()){
            bank = new Bank(this);
            getLogger().info("Loaded bank information!");
        }else {
            bank = new Bank(0, 0, new ArrayList<>());
            getLogger().info("Created bank information!");
        }
        messages = new MessagesConfigHandler(this);
        getLogger().info("All config files were loaded successfully!");
    }

    private void registerEconomy(){
        if(getServer().getPluginManager().isPluginEnabled("Vault")) {
            getServer().getServicesManager().register(Economy.class, new VaultEconomyConnector(this), this, ServicePriority.Highest);
            getLogger().info("Vault connecter has been registered");
        }else {
            getLogger().info("Vault was not detected. Not registering. Some plugins may not be interoperable with the currency system.");
        }
    }

}
