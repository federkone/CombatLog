package org.CombatLog.Listeners.TriggerStates.JoinLeave;

import org.CombatLog.CombatLog;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinNpcListener extends APlayerJoinListener {
    public PlayerJoinNpcListener(CombatLog plugin) {
        super(plugin);
    }

    @Override
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        super.onPlayerJoin(event);
        super.getNpcManager().removeNpc(event.getPlayer().getUniqueId());
    }
}
