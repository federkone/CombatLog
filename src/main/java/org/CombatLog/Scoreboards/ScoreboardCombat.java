package org.CombatLog.Scoreboards;

import com.google.j2objc.annotations.Property;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardCombat {
    private final PlayerStateHandler stateHandler;
    private final ScoreboardManager manager = Bukkit.getScoreboardManager();

    public ScoreboardCombat(PlayerStateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }

    public void update() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (stateHandler.isActive(player.getUniqueId())) {
                updateScoreboard(player, stateHandler.getCombatTime(player.getUniqueId()));
            }else{
                disableCombatScoreboard(player);
            }
        }
    }

    @SuppressWarnings("deprecation")//method getNewScoreboard() deprecated
    private void updateScoreboard(Player player, int timeLeft) {
        // Obtenemos el scoreboard actual del jugador.
        Scoreboard scoreboard = player.getScoreboard();
        // Verificamos si el jugador ya tiene un objective "combat"
        Objective objective = scoreboard.getObjective("combat");
        if (objective == null && manager != null) {
            scoreboard = manager.getNewScoreboard();
            objective = scoreboard.registerNewObjective("combat", "dummy", "⛔ Combate ⛔");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            objective.getScore("⏳ Tiempo:").setScore( timeLeft);

            player.setScoreboard(scoreboard);
        } else {
            objective.getScore("⏳ Tiempo:").setScore(timeLeft);
        }
    }

    private void disableCombatScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective combatObjective = scoreboard.getObjective("combat");
        if (combatObjective != null) {
            combatObjective.unregister(); // Elimina el objective "combat" sin tocar el resto
        }
        player.sendMessage("✅ Has salido de combate!");
    }



}