package dev.rgbmc.simpleinv.holders;

import dev.rgbmc.simpleinv.SimpleInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SimpleInventoryHolder implements InventoryHolder {

    private SimpleInventory parent;

    public SimpleInventoryHolder(SimpleInventory parent) {
        this.parent = parent;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Inventory getInventory() {
        return parent.build();
    }

    public SimpleInventory getParent() {
        return parent;
    }

    public void setParent(SimpleInventory parent) {
        this.parent = parent;
    }
}
