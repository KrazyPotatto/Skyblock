package com.github.krazypotatto.skyblock.api.impl;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.utils.serializables.Island;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides the vault interface, so that the economy adapter in vault does not need to be changed.
 *
 */
public class VaultEconomyConnector implements Economy {

    Skyblock pl;

    public VaultEconomyConnector(Skyblock pl){
        this.pl = pl;
    }

    @Override
    public boolean isEnabled() {
        return pl != null && pl.isEnabled();
    }

    @Override
    public String getName() {
        return "Skyblock";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return -1;
    }

    @Override
    public String format(double amount) {
        return String.format("%.3f", amount);
    }

    @Override
    public String currencyNamePlural() {
        return "Dollars";
    }

    @Override
    public String currencyNameSingular() {
        return "Dollar";
    }

    @Override
    public boolean hasAccount(String playerName) {
        return hasAccount(Bukkit.getOfflinePlayer(playerName));
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        Optional<Island> is = pl.islandManager.getIsland(player);
        if(!is.isPresent()) return false;
        return pl.bank.getAccount(is.get().getIslandID()).isPresent();
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    @Override
    public double getBalance(String playerName) {
        return getBalance(Bukkit.getOfflinePlayer(playerName));
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        if(!hasAccount(player)) return -1;
        Optional<Island> is = pl.islandManager.getIsland(player);
        return pl.bank.getAccount(is.get().getIslandID()).get().getBalance();
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        Optional<UUID> bankUUID = pl.islandManager.getIslandUUID(player);
        boolean success = false;
        double newBalance = -1;
        if(bankUUID.isPresent()) {
            success = pl.bank.withdraw(bankUUID.get(), amount);
            newBalance = pl.bank.getBalance(bankUUID.get());
        }
        return new EconomyResponse(amount, newBalance, success ? EconomyResponse.ResponseType.SUCCESS : EconomyResponse.ResponseType.FAILURE, success ? "" : ( bankUUID.isPresent() ? "This player does not have a bank account." : "You don't have the necessary funds."));
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return depositPlayer(Bukkit.getOfflinePlayer(playerName), amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        Optional<UUID> bankUUID = pl.islandManager.getIslandUUID(player);
        boolean success = false;
        double newBalance = -1;
        if(bankUUID.isPresent()) {
            success = pl.bank.deposit(bankUUID.get(), amount);
            newBalance = pl.bank.getBalance(bankUUID.get());
        }
        return new EconomyResponse(amount, newBalance, success ? EconomyResponse.ResponseType.SUCCESS : EconomyResponse.ResponseType.FAILURE, success ? "" : ( bankUUID.isPresent() ? "This player does not have a bank account." : "The server does not have the necessary funds to authorize this transaction"));
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return false;
    }
}
