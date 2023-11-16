package dev.rgbmc.simpleinv.handlers;

import dev.rgbmc.simpleinv.objects.SlotState;

import java.util.function.Consumer;

public class CloseSlotHandler {
    private final Consumer<SlotState> consumer;

    public CloseSlotHandler(Consumer<SlotState> consumer) {
        this.consumer = consumer;
    }

    public void call(SlotState slotState) {
        consumer.accept(slotState);
    }
}
