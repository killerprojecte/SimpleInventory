package dev.rgbmc.simpleinv.item;

import dev.rgbmc.simpleinv.utils.Color;
import java.util.*;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    private final List<String> lore = new ArrayList<>();
    private final Map<Enchantment, Integer> enchantments = new HashMap<>();
    private final List<ItemFlag> itemFlags = new ArrayList<>();
    private Material material = Material.AIR;
    private String displayName = "&eCreated By SimpleInventory#ItemBuilder";
    private int amount = 1;
    private int customModelData = Integer.MIN_VALUE;
    private boolean unbreakable = false;

    public ItemBuilder type(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore.addAll(lore);
        return this;
    }

    public ItemBuilder clearLore() {
        this.lore.clear();
        return this;
    }

    public ItemBuilder lore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment) {
        return enchantment(enchantment, 1);
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        enchantments.remove(enchantment);
        return this;
    }

    public ItemBuilder clearEnchantments() {
        enchantments.clear();
        return this;
    }

    public ItemBuilder customModelData(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    public ItemBuilder flag(ItemFlag itemFlag) {
        itemFlags.add(itemFlag);
        return this;
    }

    public ItemBuilder removeFlag(ItemFlag itemFlag) {
        itemFlags.remove(itemFlag);
        return this;
    }

    public ItemBuilder clearFlags() {
        itemFlags.clear();
        return this;
    }

    public ItemBuilder unbreakable() {
        this.unbreakable = true;
        return this;
    }

    public ItemBuilder breakable() {
        this.unbreakable = false;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        item.setAmount(amount);
        if (meta != null) {
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                meta.addEnchant(entry.getKey(), entry.getValue(), true);
            }
            meta.setDisplayName(Color.color(displayName));
            if (customModelData != Integer.MIN_VALUE) {
                meta.setCustomModelData(customModelData);
            }
            meta.setLore(lore.stream().map(Color::color).collect(Collectors.toList()));
            meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
            meta.setUnbreakable(unbreakable);
            item.setItemMeta(meta);
        }
        return item;
    }
}
