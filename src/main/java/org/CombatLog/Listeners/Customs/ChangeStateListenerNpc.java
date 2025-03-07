package org.CombatLog.Listeners.Customs;

import org.CombatLog.CitizensApiExpansion.NpcManager;
import org.CombatLog.Events.*;
import org.bukkit.event.EventHandler;

public class ChangeStateListenerNpc extends AbstractChangeStateListener {
    private final NpcManager npcManager;

    public ChangeStateListenerNpc(NpcManager npcManager) {
        this.npcManager = npcManager;
    }

    @Override
    @EventHandler
    public void onActivateDisconnected(onActivateDisconnected event) {
        super.onActivateDisconnected(event);
        npcManager.createNpc(event.getPlayer());
    }

    @Override
    @EventHandler
    public void onActivatePenalized(onActivatePenalized event) {
        super.onActivatePenalized(event);
        npcManager.removeNpc(event.getPlayer());
    }
}
