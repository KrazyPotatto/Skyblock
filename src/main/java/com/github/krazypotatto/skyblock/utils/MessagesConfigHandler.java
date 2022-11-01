package com.github.krazypotatto.skyblock.utils;

import com.github.krazypotatto.skyblock.Skyblock;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessagesConfigHandler {

    public enum PrefixType {
        NONE,
        SUCCESS,
        ERROR,
        DEFAULT;
    }

    private final FileConfiguration config;

    public MessagesConfigHandler(Skyblock pl) {
        File configFile = new File(pl.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            pl.saveResource("messages.yml", true);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        pl.getLogger().info("Loaded messages.");
    }

    private String generatePrefix(PrefixType type){
        String prefix = config.getString("prefix");
        switch (type){
            case NONE:
                prefix = "";
                break;
            case ERROR:
                prefix += config.getString("error-prefix");
                break;
            case SUCCESS:
                prefix += config.getString("success-prefix");
                break;
        }
        return prefix;
    }

    public void sendLocatedMessage(CommandSender p, String location, PrefixType prefix){
        p.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', generatePrefix(prefix) + config.getString(location))));
    }
    public void sendLocatedMessage(CommandSender p, String location, PrefixType prefix, Object... args){
        p.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', generatePrefix(prefix) + String.format(config.getString(location), args))));
    }

}