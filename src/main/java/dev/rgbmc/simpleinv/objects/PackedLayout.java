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
    private final String id;
    private final String identify;

    public PackedLayout(ItemBuilder itemBuilder, ClickHandlers clickHandlers, CloseSlotHandler closeSlotHandler, boolean interactive, boolean undefined, String id, String identify) {
        this.itemBuilder = itemBuilder;
        this.clickHandlers = clickHandlers;
        this.closeSlotHandler = closeSlotHandler;
        this.interactive = interactive;
        this.undefined = undefined;
        this.id = id;
        this.identify = identify;
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

    public String getId() {
        return id;
    }

    public String getIdentify() {
        return identify;
    }
}
