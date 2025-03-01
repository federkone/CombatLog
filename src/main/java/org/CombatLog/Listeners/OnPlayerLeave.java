package org.CombatLog.Listeners;

import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class OnPlayerLeave implements Listener {
     private final PlayerStateHandler stateHandler;

     public OnPlayerLeave(PlayerStateHandler stateHandler){
         this.stateHandler = stateHandler;
     }

     @EventHandler
     public void onPlayerQuit(PlayerQuitEvent event) {
         Player player = event.getPlayer();
         if(stateHandler.isActive(player.getUniqueId())){
             stateHandler.disconnectOnCombat(player.getUniqueId());
             //anunciar en el servidor que el jugador se ha desconectado en combate
             Bukkit.broadcastMessage("El jugador " + player.getName() + " se ha desconectado en combate, tiene 5 minutos para volver al juego o sera penalizado.");
         }
     }

     @EventHandler
     public void onPlayerDeath(PlayerDeathEvent event) {
         Player player = event.getEntity();
         stateHandler.deactivateCombat(player.getUniqueId());
         stateHandler.cancelTimerCombat(player.getUniqueId());
     }
}
