package org.CombatLog.Listeners.TriggerStates;

import org.CombatLog.CitizensApiExpansion.NpcManager;
import org.CombatLog.Events.onActivatePenalized;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {
    private final PlayerStateHandler stateHandler;
    private final NpcManager npcManager;

    public PlayerJoinListener(PlayerStateHandler stateHandler, NpcManager npcManager) {
        this.stateHandler = stateHandler;
        this.npcManager = npcManager;
    }

     @EventHandler
     public void onPlayerJoin(PlayerJoinEvent event) {
         Player player = event.getPlayer();
         UUID uuid = player.getUniqueId();
         if(!stateHandler.hasState(uuid)){
             stateHandler.activateIDLE(uuid);
         }
         if (stateHandler.isDisconnected(uuid)) {
             stateHandler.activateCombat(uuid);
         }
         if(stateHandler.isPenalized(uuid)){
             Bukkit.getPluginManager().callEvent(new onActivatePenalized(uuid, false));
         }
         if (npcManager != null) {
             npcManager.removeNpc(uuid);
         }
     }

   /* @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        stateHandler.activateIDLE(event.getPlayer().getUniqueId());
    }*/
}
