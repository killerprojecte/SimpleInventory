package dev.rgbmc.simpleinv.objects;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class CloseState {
    private final Inventory inventory;
    private final PlayerInventory playerInventory;
    private final Player player;
    private final InventoryCloseEvent event;

    public CloseState(InventoryCloseEvent event) {
        this.event = event;
        inventory = event.getInventory();
        player = (Player) event.getPlayer();
        playerInventory = player.getInventory();
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public InventoryCloseEvent getEvent() {
        return event;
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }
}
