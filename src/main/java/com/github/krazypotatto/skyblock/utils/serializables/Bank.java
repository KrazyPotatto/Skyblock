package com.github.krazypotatto.skyblock.utils.serializables;

import com.github.krazypotatto.skyblock.Skyblock;
import com.github.krazypotatto.skyblock.utils.serializables.parts.BankAccount;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Bank extends AbstractSerializable<Bank>{

    private double totalCirculating;
    private double totalAllocated;
    private List<BankAccount> accounts;

    public Bank(Skyblock pl) {
        super(Bank.class, new File(Paths.get(pl.getDataFolder().getPath(), "bank.json").toUri()));
        try {
            loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Bank(float totalCirculating, float totalAllocated, List<BankAccount> accouts) {
        super(Bank.class, new File(Paths.get(JavaPlugin.getPlugin(Skyblock.class).getDataFolder().getPath(), "bank.json").toUri()));
        this.totalCirculating = totalCirculating;
        this.totalAllocated = totalAllocated;
        this.accounts = accouts;
    }

    @Override
    void applyValues(Bank readClass) {
        this.totalCirculating = readClass.totalCirculating;
        this.totalAllocated = readClass.totalAllocated;
        this.accounts = readClass.accounts;
    }

    public double getTotalCirculating() {
        return totalCirculating;
    }

    public void setTotalCirculating(double totalCirculating) {
        this.totalCirculating = totalCirculating;
    }

    public double getTotalAllocated() {
        return totalAllocated;
    }

    public void setTotalAllocated(double totalAllocated) {
        this.totalAllocated = totalAllocated;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public Optional<BankAccount> getAccount(UUID uuid){
        return accounts.stream().filter(a -> a.getOwner().equals(uuid)).findFirst();
    }

    public void addCirculating(double toAdd){
        this.totalCirculating += toAdd;
        try {
            this.saveData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAllocated(double toAdd){
        this.totalAllocated += toAdd;
        try {
            this.saveData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean withdraw(UUID account, double amount){
        Optional<BankAccount> acc = getAccount(account);
        if(acc.isPresent()){
            BankAccount ba = acc.get();
            if(ba.getBalance() >= amount){
                ba.incrementBalance(-amount);
                addAllocated(-amount);
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public boolean deposit(UUID account, double amount){
        Optional<BankAccount> acc = getAccount(account);
        if(acc.isPresent()){
            BankAccount ba = acc.get();
            if(getTotalCirculating() - getTotalAllocated() >= amount){
                ba.incrementBalance(amount);
                addAllocated(amount);
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public double getBalance(UUID account){
        return getAccount(account).map(BankAccount::getBalance).orElse(-1.0);
    }

}
