package org.CombatLog.BukkitRunnable;

import org.CombatLog.CitizensApiExpansion.NpcManager;
import org.CombatLog.Scoreboards.ScoreboardCombat;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class RunnableNpc extends BukkitRunnable {
    private final PlayerStateHandler stateHandler;
    //private final ScoreboardCombat scoreboardCombat;
    private final NpcManager npcManager;

    public RunnableNpc(PlayerStateHandler stateHandler, ScoreboardCombat scoreboardCombat, NpcManager npcManager) {
        this.stateHandler = stateHandler;
        //this.scoreboardCombat = scoreboardCombat;
        this.npcManager = npcManager;
    }

    @Override
    public void run() {
        stateHandler.updateTimers();
        npcManager.updateNpcs();
        //scoreboardCombat.update();
    }
}
