package org.CombatLog.Listeners.TriggerStates;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.entity.Player;

public class CombatListeners extends AbstractCombatListeners {

    public CombatListeners(PlayerStateHandler stateHandler) {
        super(stateHandler);
    }

    @Override
    protected void handleCombat(Player victim, Player attacker) {
        super.activateCombat(victim, attacker);
    }

}

