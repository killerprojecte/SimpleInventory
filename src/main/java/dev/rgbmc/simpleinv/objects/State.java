package dev.rgbmc.simpleinv.objects;

import dev.rgbmc.simpleinv.SimpleInventory;

public abstract class State {
    private final SimpleInventory parent;

    public State(SimpleInventory parent) {
        this.parent = parent;
    }

    public SimpleInventory getParent() {
        return parent;
    }
}
