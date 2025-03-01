package org.CombatLog.Listeners;

import org.CombatLog.State.PlayerState;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class OnPlayerJoin implements Listener {
    private final PlayerStateHandler stateHandler;

    public OnPlayerJoin(PlayerStateHandler stateHandler){
        this.stateHandler = stateHandler;
    }

     @EventHandler
     //al ingresar al servidor se verifica si el jugador estaba en combate y se le aplica un castigo
     public void onPlayerJoin(PlayerJoinEvent event) {
         Player player = event.getPlayer();
         UUID uuid = player.getUniqueId();
         if(!stateHandler.hasState(uuid)){
             stateHandler.setPlayerState(uuid, PlayerState.IDLE);
         }
         if (stateHandler.isDisconnected(uuid)) {  //si el juegador logra ingresar antes de los 5 minutos se le quita el estado de desconectado y se le aplica el estado de combate activado
             stateHandler.activateCombat(uuid);
             player.sendMessage("Estas en combate durante 1 minuto, si te desconectas en combate y no regresas en 5 minutos seras penalizado");
         }
         if(stateHandler.isPenalized(uuid)){
             //CASTIGOS... matar,encarcelar,etc.
             player.sendMessage("¡Te has desconectado en combate has sido penalizado!");
         }

         //aca puede ir un switch y en el caso por defecto se le aplica el estado IDLE
         /*switch (stateHandler.getPlayerState(player)) {
             case DISCONNECTED:
                 stateHandler.activateCombat(player);
                 player.sendMessage("Estas en combate durante 1 minuto, si te desconectas en combate seras penalizado");
                 break;
             case PENALIZED:
                 player.sendMessage("¡Te has desconectado en combate!");
                 break;
             default:
                 stateHandler.setState(player, PlayerState.IDLE);
                 break;
         }*/

     }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        stateHandler.deactivateCombat(player.getUniqueId());
        stateHandler.cancelTimerCombat(player.getUniqueId());

    }
}
