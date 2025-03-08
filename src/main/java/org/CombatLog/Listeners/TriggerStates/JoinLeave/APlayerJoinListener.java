package org.CombatLog.Listeners.TriggerStates.JoinLeave;

import org.CombatLog.CitizensApiExpansion.NpcManager;
import org.CombatLog.CombatLog;
import org.CombatLog.Events.onActivatePenalized;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public abstract class APlayerJoinListener implements Listener {
    private final PlayerStateHandler stateHandler;
    private final NpcManager npcManager;

    public APlayerJoinListener(CombatLog plugin) {
        this.stateHandler = plugin.getStateHandler();
        this.npcManager = plugin.getNpcManager();
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
    }

    protected NpcManager getNpcManager() {
        return npcManager;
    }

}