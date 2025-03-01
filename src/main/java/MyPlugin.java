package org.CombatLog;

import org.CombatLog.Listeners.CombatListeners;
import org.CombatLog.Listeners.OnPlayerJoin;
import org.CombatLog.Listeners.OnPlayerLeave;
import org.CombatLog.Scoreboards.ScoreboardCombat;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.CombatLog.BukkitRunnable.Runnable;


public class MyPlugin extends JavaPlugin {
    private final PlayerStateHandler stateHandler = new PlayerStateHandler();

    @Override
    public void onEnable() {
        ScoreboardCombat scoreboardCombat = new ScoreboardCombat(stateHandler);
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new CombatListeners(stateHandler), this);
        pluginManager.registerEvents(new OnPlayerJoin(stateHandler), this);
        pluginManager.registerEvents(new OnPlayerLeave(stateHandler), this);

        Runnable runnable = new Runnable(stateHandler,scoreboardCombat);
        runnable.runTaskTimer(this, 0L, 20L); //por segundo actualizar el tiempo de combate y penalizacion etc

        this.getServer().getConsoleSender().sendMessage("Plugin LOG DE COMBATE habilitado");
    }

    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage("Plugin LOG DE COMBATE deshabilitado");
    }

}
