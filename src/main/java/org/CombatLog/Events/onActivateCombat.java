package org.CombatLog.Events;

import java.util.UUID;

public class onActivateCombat extends onChangeState {
    public onActivateCombat(UUID player) {
        super(player);
    }
}
