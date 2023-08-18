package dev.rgbmc.simpleinv.objects;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickState {
    private final ClickType clickType;
    private final Player player;
    private final int slot;
    private final int rawSlot;
    private final InventoryType.SlotType slotType;
    private final InventoryType inventoryType;
    private final Inventory inventory;
    private final InventoryClickEvent event;
    private final InventoryAction inventoryAction;
    private ItemStack currentItem;
    private ItemStack cursorItem;
    public ClickState(InventoryClickEvent event) {
        this.event = event;
        clickType = event.getClick();
        player = (Player) event.getWhoClicked();
        slot = event.getSlot();
        slotType = event.getSlotType();
        inventoryType = event.getClickedInventory().getType();
        inventory = event.getClickedInventory();
        currentItem = event.getCurrentItem();
        cursorItem = event.getCursor();
        inventoryAction = event.getAction();
        rawSlot = event.getRawSlot();
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public int getRawSlot() {
        return rawSlot;
    }

    public int getSlot() {
        return slot;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public InventoryAction getInventoryAction() {
        return inventoryAction;
    }

    public InventoryClickEvent getEvent() {
        return event;
    }

    public ItemStack getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ItemStack item) {
        this.currentItem = item;
        event.setCurrentItem(item);
    }

    public ItemStack getCursorItem() {
        return cursorItem;
    }

    public void setCursorItem(ItemStack item) {
        this.cursorItem = item;
        event.setCursor(item);
    }

    public Player getPlayer() {
        return player;
    }

    public InventoryType.SlotType getSlotType() {
        return slotType;
    }

    public boolean isCancelled() {
        return event.isCancelled();
    }

    public void setCancelled(boolean status) {
        event.setCancelled(status);
    }
}
