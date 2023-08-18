package dev.rgbmc.simpleinv.objects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SlotState {
    private final Player player;
    private final ItemStack item;
    private final int slot;
    public SlotState(Player player, ItemStack item, int slot) {
        this.player = player;
        this.item = item;
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItem() {
        return item;
    }
}
