package dev.rgbmc.simpleinv.objects;

import dev.rgbmc.simpleinv.handlers.ClickHandlers;
import dev.rgbmc.simpleinv.handlers.CloseSlotHandler;
import dev.rgbmc.simpleinv.item.ItemBuilder;

public class PackedLayout {
    private final ItemBuilder itemBuilder;
    private final ClickHandlers clickHandlers;
    private final CloseSlotHandler closeSlotHandler;
    private final boolean interactive;
    private final boolean undefined;

    public PackedLayout(ItemBuilder itemBuilder, ClickHandlers clickHandlers, CloseSlotHandler closeSlotHandler, boolean interactive, boolean undefined) {
        this.itemBuilder = itemBuilder;
        this.clickHandlers = clickHandlers;
        this.closeSlotHandler = closeSlotHandler;
        this.interactive = interactive;
        this.undefined = undefined;
    }

    public ClickHandlers getClickHandlers() {
        return clickHandlers;
    }

    public CloseSlotHandler getCloseSlotHandler() {
        return closeSlotHandler;
    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }

    public boolean isInteractive() {
        return interactive;
    }

    public boolean isUndefined() {
        return undefined;
    }
}
