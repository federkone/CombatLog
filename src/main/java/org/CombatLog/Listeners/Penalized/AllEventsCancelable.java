package org.CombatLog.Listeners.Penalized;

import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class AllEventsCancelable implements Listener {
    private final PlayerStateHandler stateHandler;

    public AllEventsCancelable(PlayerStateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }

    @EventHandler
    public void onPlayerCombat(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker) {
            if (stateHandler.isPenalized(attacker.getUniqueId())) {
                event.setCancelled(true);
                attacker.sendMessage(ChatColor.RED+"No puedes atacar mientras estás penalizado");
            }
        }
    }

   @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && stateHandler.isPenalized(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
    /*@EventHandler
    public void  onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (stateHandler.isPenalized(player.getUniqueId()) && !player.hasPermission("combatlog.penalized.bypass")) {
            event.setCancelled(true);
            //player.sendMessage(ChatColor.RED+"No puedes moverte mientras estás penalizado");
        }
    }*/

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (stateHandler.isPenalized(player.getUniqueId())  && event.getCause() != PlayerTeleportEvent.TeleportCause.PLUGIN  && !player.hasPermission("combatlog.penalized.bypass"))   {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED+"No puedes teletransportarte mientras estás penalizado");
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (stateHandler.isPenalized(player.getUniqueId()) && !player.hasPermission("combatlog.bypass")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED+"No puedes ejecutar comandos mientras estás penalizado");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (stateHandler.isPenalized(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED+"No puedes romper bloques mientras estás penalizado");
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player && stateHandler.isPenalized(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (stateHandler.isPenalized(player.getUniqueId())) {
            event.setCancelled(true);
            //player.sendMessage(ChatColor.RED+"No puedes interactuar mientras estás penalizado");
        }
    }


}
