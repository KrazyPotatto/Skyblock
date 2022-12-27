package com.github.krazypotatto.skyblock.utils.serializables.parts;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SerializableItemStack {

    private Material material = Material.DIRT;
    private int quantity = 1;
    private String displayName = "";
    private List<String> lore = new ArrayList<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();

    public SerializableItemStack() {
    }

    public SerializableItemStack(Material material, int quantity) {
        this.material = material;
        this.quantity = quantity;
    }

    public SerializableItemStack(Material material, int quantity, String displayName) {
        this.material = material;
        this.quantity = quantity;
        this.displayName = displayName;
    }

    public SerializableItemStack(Material material, int quantity, String displayName, List<String> lore) {
        this.material = material;
        this.quantity = quantity;
        this.displayName = displayName;
        this.lore = lore;
    }

    public SerializableItemStack(Material material, int quantity, String displayName, String... lore) {
        this.material = material;
        this.quantity = quantity;
        this.displayName = displayName;
        this.lore = Arrays.asList(lore);
    }

    public SerializableItemStack(Material material, int quantity, String displayName, List<String> lore, Map<Enchantment, Integer> enchantments) {
        this.material = material;
        this.quantity = quantity;
        this.displayName = displayName;
        this.lore = lore;
        this.enchantments = enchantments;
    }

    private SerializableItemStack(ItemStack is){
        material = is.getType();
        quantity = is.getAmount();
        displayName = is.displayName().toString();
        lore = is.getLore();
        enchantments = is.getEnchantments();
    }

    public static SerializableItemStack fromItemStack(ItemStack is){
        return new SerializableItemStack(is);
    }

    public ItemStack toItemStack(){
        ItemStack is = new ItemStack(material, quantity);
        if(!displayName.equals(""))
            is.getItemMeta().displayName(Component.text(displayName));
        is.getItemMeta().setLore(lore);
        enchantments.forEach(is::addUnsafeEnchantment);
        return is;
    }


    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }
}
