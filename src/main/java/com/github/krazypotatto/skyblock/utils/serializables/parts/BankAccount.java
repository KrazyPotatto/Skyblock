package com.github.krazypotatto.skyblock.utils.serializables.parts;

import java.util.UUID;

public class BankAccount {

    private UUID owner;
    private float balance;

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

    public float getBalance() {
        return balance;
    }

    public void setBalance(float newBalance) {
        this.balance = newBalance;
    }

    public void incrementBalance(float toAdd) {
        this.balance += toAdd;
    }

}
