package dev.rgbmc.simpleinv.item;

import dev.rgbmc.simpleinv.objects.VariableInfo;
import dev.rgbmc.simpleinv.utils.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemBuilder {
    private final List<String> lore = new ArrayList<>();
    private final Map<Enchantment, Integer> enchantments = new HashMap<>();
    private final List<ItemFlag> itemFlags = new ArrayList<>();
    private Material material = Material.AIR;
    private String displayName = "&eCreated By SimpleInventory#ItemBuilder";
    private int amount = 1;
    private int customModelData = Integer.MIN_VALUE;
    private boolean unbreakable = false;
    private short damage = 0;
    private ItemStack itemStack = null;
    private String slotName = "UNKNOWN";
    private String skullTexture = null;

    public ItemBuilder slotName(String slotName) {
        this.slotName = slotName;
        return this;
    }

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

    public ItemBuilder damage(short damage) {
        this.damage = damage;
        return this;
    }

    public ItemBuilder itemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemBuilder skullTexture(String texture) {
        this.skullTexture = texture;
        return this;
    }

    public ItemStack build(Player player) {
        return build(player, VariableInfo::getOrigin);
    }

    public ItemStack build(Player player, Function<VariableInfo, String> variableHandler) {
        if (itemStack != null) return itemStack;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        item.setAmount(amount);
        if (damage != 0) {
            item.setDurability(damage);
        }
        if (meta != null) {
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                meta.addEnchant(entry.getKey(), entry.getValue(), true);
            }
            meta.setDisplayName(Color.color(variableHandler.apply(new VariableInfo(displayName, player))));
            if (customModelData != Integer.MIN_VALUE) {
                meta.setCustomModelData(customModelData);
            }
            meta.setLore(
                    lore.stream()
                            .map(string -> variableHandler.apply(new VariableInfo(string, player)))
                            .map(Color::color)
                            .collect(Collectors.toList()));
            meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
            meta.setUnbreakable(unbreakable);
            item.setItemMeta(meta);
            if (skullTexture != null) {
                item = SkullBuilder.applySkin(itemStack, skullTexture);
            }
        }
        return item;
    }

    public String getSlotName() {
        return slotName;
    }
}
