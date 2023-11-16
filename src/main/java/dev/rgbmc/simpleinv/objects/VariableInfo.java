package dev.rgbmc.simpleinv.objects;

import org.bukkit.entity.Player;

public class VariableInfo {
    private final String origin;
    private final Player player;

    public VariableInfo(String origin, Player player) {
        this.origin = origin;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String getOrigin() {
        return origin;
    }
}
