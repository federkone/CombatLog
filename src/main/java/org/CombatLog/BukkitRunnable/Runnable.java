package org.CombatLog.BukkitRunnable;

import org.CombatLog.Scoreboards.ScoreboardCombat;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class Runnable extends BukkitRunnable {
    private final PlayerStateHandler stateHandler;
    private final ScoreboardCombat scoreboardCombat;

    public Runnable(PlayerStateHandler stateHandler, ScoreboardCombat scoreboardCombat) {
        this.stateHandler = stateHandler;
        this.scoreboardCombat = scoreboardCombat;
    }

    @Override
    public void run() {
       stateHandler.udapteTimerCombat();
       stateHandler.updateTimerPenalize();
       scoreboardCombat.update();
    }
}