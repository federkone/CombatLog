package org.CombatLog.Listeners.TriggerStates.Combat;

import net.citizensnpcs.api.CitizensAPI;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListenersNpc extends AbstractCombatListeners {

    public CombatListenersNpc(PlayerStateHandler stateHandler) {
        super(stateHandler);
    }

    @Override
    @EventHandler
    public void onPlayerCombat(EntityDamageByEntityEvent event) {
        if (CitizensAPI.getNPCRegistry().isNPC(event.getEntity())) {
            return;
        }
        super.onPlayerCombat(event);
    }
}
