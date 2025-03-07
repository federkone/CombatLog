package org.CombatLog.Events;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public abstract class onChangeState extends Event {
    private final UUID player;
    private static final HandlerList HANDLERS = new HandlerList();

    public onChangeState(UUID player) {
        this.player = player;

    }
    public UUID getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}