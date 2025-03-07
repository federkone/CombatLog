package org.CombatLog.Listeners.TriggerStates;

import net.citizensnpcs.api.CitizensAPI;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.entity.Player;

public class CombatListenersNpc extends AbstractCombatListeners {

    public CombatListenersNpc(PlayerStateHandler stateHandler) {
        super(stateHandler);
    }

    @Override
    protected void handleCombat(Player victim, Player attacker) {
        if (CitizensAPI.getNPCRegistry().isNPC(victim)) {
            return;
        }
        super.activateCombat(victim, attacker);
    }
}
