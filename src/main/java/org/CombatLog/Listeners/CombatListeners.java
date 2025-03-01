package org.CombatLog.Listeners;

import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CombatListeners implements Listener {
    private final PlayerStateHandler stateHandler;

    public CombatListeners(PlayerStateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }

    @EventHandler
    public void onPlayerCombat(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player victim && event.getDamager() instanceof Player attacker) {
            if (!stateHandler.isActive(victim.getUniqueId())) {
                stateHandler.activateCombat(victim.getUniqueId());
            }
            if (!stateHandler.isActive(attacker.getUniqueId())) {
                stateHandler.activateCombat(attacker.getUniqueId());
            }
        }
    }

    //cancelar eventos mientras el jugador esta en combate, por ejemplo: comandos,teletransporte

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (stateHandler.isActive(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage("No puedes ejecutar comandos mientras estas en combate");
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (stateHandler.isActive(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage("No puedes teletransportarte mientras estas en combate");
        }
    }

}

