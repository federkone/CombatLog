package org.CombatLog.Listeners.TriggerStates;

import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class PlayerLeaveListener implements Listener {
     private final PlayerStateHandler stateHandler;

     public PlayerLeaveListener(PlayerStateHandler stateHandler){
         this.stateHandler = stateHandler;
     }

     @EventHandler
     public void onPlayerQuit(PlayerQuitEvent event) {
         Player player = event.getPlayer();
         if(stateHandler.inCombat(player.getUniqueId())){
             stateHandler.activateDisconnected(player.getUniqueId());
         }else{
             if (!stateHandler.isPenalized(player.getUniqueId())) {
                stateHandler.removeState(player.getUniqueId());
             }
         }
     }

     @EventHandler
     public void onPlayerDeath(PlayerDeathEvent event) {
         Player player = event.getEntity();
         stateHandler.activateIDLE(player.getUniqueId());
     }
}
