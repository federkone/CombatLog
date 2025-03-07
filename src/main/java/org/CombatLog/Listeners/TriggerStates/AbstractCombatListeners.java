package org.CombatLog.Listeners.TriggerStates;

import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public abstract class AbstractCombatListeners implements Listener {
    protected final PlayerStateHandler stateHandler;

    public AbstractCombatListeners(PlayerStateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }

    @EventHandler
    public void onPlayerCombat(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player victim && event.getDamager() instanceof Player attacker) {
            handleCombat(victim, attacker);
        }
    }

    protected abstract void handleCombat(Player victim, Player attacker);

    protected void activateCombat(Player victim, Player attacker) {
        stateHandler.activateCombat(victim.getUniqueId());
        stateHandler.activateCombat(attacker.getUniqueId());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (stateHandler.inCombat(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage("No puedes teletransportarte mientras estas en combate");
        }
    }
}
