package com.github.krazypotatto.skyblock.utils.serializables.parts;

import java.util.UUID;

public class BankAccount {

    private UUID owner;
    private double balance;

    public BankAccount(UUID owner, float balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public void incrementBalance(double toAdd) {
        this.balance += toAdd;
    }

}
