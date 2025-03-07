package org.CombatLog.Config;

import org.CombatLog.CombatLog;
import org.bukkit.configuration.file.FileConfiguration;

public class File {
    private final CombatLog plugin;

    public File(CombatLog plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        FileConfiguration config = plugin.getConfig();

        config.addDefault("TimeCombat", 60);
        config.addDefault("TimeDisconnected", 300);
        config.addDefault("TimePenalize", 500);
        config.addDefault("CommandsForPenalized", "");

        config.options().copyDefaults(true);
        plugin.saveDefaultConfig();
    }
}
