package org.CombatLog.Events;

import java.util.UUID;

public class onActivatePenalized extends onChangeState {
    private final boolean broadcast;

    public onActivatePenalized(UUID player,boolean broadcast) {
        super(player);
        this.broadcast = broadcast;
    }

    public boolean shouldBroadcast() {
        return broadcast;
    }
}
