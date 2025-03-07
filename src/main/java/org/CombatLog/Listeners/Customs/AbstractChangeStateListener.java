package org.CombatLog.Listeners.Customs;

import org.CombatLog.Events.*;
import org.CombatLog.Utils.MessagesCombat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class AbstractChangeStateListener implements Listener {

    @EventHandler
    public void onActivateIdle(onActivateIdle event) {
        MessagesCombat.sendMessageOutCombat(event.getPlayer());
    }

    @EventHandler
    public void onActivateCombat(onActivateCombat event) {
        MessagesCombat.sendMessageInCombat(event.getPlayer());
    }

    @EventHandler
    public void onActivateDisconnected(onActivateDisconnected event) {
        MessagesCombat.broadcastMessageDisconected(event.getPlayer());
    }

    @EventHandler
    public void onActivatePenalized(onActivatePenalized event) {
        if (event.shouldBroadcast()) {
            MessagesCombat.broadcastMessagePenalize(event.getPlayer());
        }
        //teletransportar a la carcel
        Player player = Bukkit.getPlayer(event.getPlayer());
        if (player != null) {
            player.teleport(new Location(player.getWorld(), 732, 67.5, 473), PlayerTeleportEvent.TeleportCause.PLUGIN);
            MessagesCombat.sendMessageInPenalize(event.getPlayer());
        }
    }

    @EventHandler
    public void onUnpenalized(onUnpenalized event) {
        MessagesCombat.sendMessageOutPenalize(event.getPlayer());
        //quitar de la carcel
        Player player = Bukkit.getPlayer(event.getPlayer());
        if (player != null) {
            player.teleport(new Location(player.getWorld(), 750, 72.5, 480), PlayerTeleportEvent.TeleportCause.PLUGIN);

        }
    }
}
