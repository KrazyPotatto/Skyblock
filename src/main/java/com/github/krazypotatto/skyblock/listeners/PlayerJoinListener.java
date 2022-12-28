package com.github.krazypotatto.skyblock.listeners;

import com.github.krazypotatto.skyblock.Skyblock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.EventListener;

public class PlayerJoinListener implements EventListener, Listener {

    private Skyblock pl;

    public PlayerJoinListener(Skyblock pl){
        this.pl = pl;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(!e.getPlayer().hasPlayedBefore()){
            pl.bank.addCirculating(2500);
        }
    }

}
