package dev.rgbmc.simpleinv.objects;

import dev.rgbmc.simpleinv.SimpleInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SlotState extends State {
  private final Player player;
  private final ItemStack item;
  private final int slot;

  public SlotState(Player player, ItemStack item, int slot, SimpleInventory parent) {
    super(parent);
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
