package dev.rgbmc.simpleinv.listeners;

import dev.rgbmc.simpleinv.holders.SimpleInventoryHolder;
import dev.rgbmc.simpleinv.objects.ClickState;
import dev.rgbmc.simpleinv.objects.CloseState;
import dev.rgbmc.simpleinv.objects.InventoryMode;
import dev.rgbmc.simpleinv.objects.SlotState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class SimpleInventoryListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof SimpleInventoryHolder)) return;
        SimpleInventoryHolder holder = (SimpleInventoryHolder) event.getInventory().getHolder();
        if (holder.getParent().getMode().equals(InventoryMode.MENU)) {
            event.setCancelled(true);
        }
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getHolder() == null) return;
        if (!(event.getClickedInventory().getHolder() instanceof SimpleInventoryHolder)) {
            if (!(event.isShiftClick() && event.isLeftClick())){
                event.setCancelled(false);
            }
            return;
        }
        if (holder.getParent().isAllow(event.getSlot())) {
            event.setCancelled(false);
        }
        holder.getParent().clickSlot(event.getSlot(), new ClickState(event));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof SimpleInventoryHolder)) return;
        SimpleInventoryHolder holder = (SimpleInventoryHolder) event.getInventory().getHolder();
        holder.getParent().closeInventory(new CloseState(event));
        for (int i = 0; i < event.getInventory().getSize(); i++) {
            SlotState slotState = new SlotState((Player) event.getPlayer(), event.getInventory().getItem(i), i);
            holder.getParent().closedSlot(slotState);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof SimpleInventoryHolder)) return;
        SimpleInventoryHolder holder = (SimpleInventoryHolder) event.getInventory().getHolder();
        if (holder.getParent().getMode().equals(InventoryMode.MENU)) {
            boolean allow = false;
            for (int slot : event.getRawSlots()) {
                allow = holder.getParent().isAllow(slot) || slot >= holder.getParent().getSize();
            }
            if (allow) {
                event.setCancelled(false);
                return;
            }
            event.setCancelled(true);
        }
    }
}
