package org.CombatLog.Scoreboards;

import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardCombat {
    private final PlayerStateHandler stateHandler;
    private final Map<Player, Scoreboard> playerScoreboards = new HashMap<>();

    public ScoreboardCombat(PlayerStateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }

    public void update() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (stateHandler.inCombat(player.getUniqueId())) {
                updateScoreboard(player, stateHandler.getTimer(player.getUniqueId()));
            }else{
                disableCombatScoreboard(player);
            }
        }
    }

    private void updateScoreboard(Player player, int timeLeft) {
        Scoreboard scoreboard = playerScoreboards.computeIfAbsent(player, p -> {
            Scoreboard newScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            player.setScoreboard(newScoreboard); // Asignar el nuevo scoreboard al jugador
            return newScoreboard;
        });

        Objective objective = scoreboard.getObjective("combat");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("combat", "dummy", "⛔ Combate ⛔");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        objective.getScore("⏳ Tiempo:").setScore(timeLeft);
    }

    private void disableCombatScoreboard(Player player) {
        if (playerScoreboards.remove(player) != null) // Eliminar el scoreboard del mapa
        {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }

    }



}