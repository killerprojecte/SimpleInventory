package dev.rgbmc.simpleinv.handlers;

import dev.rgbmc.simpleinv.objects.ClickState;
import java.util.ArrayList;
import java.util.List;

public class ClickHandlers {
    private final List<ClickHandler> handlers;

    public ClickHandlers() {
        this(new ArrayList<>());
    }
    public ClickHandlers(List<ClickHandler> handlers) {
        this.handlers = handlers;
    }

    public void call(ClickState clickState) {
        for (ClickHandler handler : handlers) {
            handler.call(clickState);
        }
    }

    public void clear() {
        handlers.clear();
    }

    public void add(ClickHandler clickHandler) {
        handlers.add(clickHandler);
    }

    public void addHandlers(List<ClickHandler> handlers) {
        this.handlers.addAll(handlers);
    }

    public void addHandlers(ClickHandlers handlers) {
        this.handlers.addAll(handlers.getHandlers());
    }

    public List<ClickHandler> getHandlers() {
        return new ArrayList<>(handlers);
    }

    public void remove(ClickHandler handler) {
        handlers.remove(handler);
    }
}
