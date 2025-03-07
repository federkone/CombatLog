package org.CombatLog.Config;

import org.CombatLog.State.PlayerState;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class PlayerDataFile {
    private final JavaPlugin plugin;
    private FileConfiguration playerDataConfig = null;
    private File playerDataFile = null;

    public PlayerDataFile(JavaPlugin plugin) {
        this.plugin = plugin;
        saveDefaultPlayerData();
    }

    public void reloadPlayerData() {
        if (playerDataFile == null) {
            playerDataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        }
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    public FileConfiguration getPlayerData() {
        if (playerDataConfig == null) {
            reloadPlayerData();
        }
        return playerDataConfig;
    }

    public void savePlayerData() {
        if (playerDataConfig == null || playerDataFile == null) {
            return;
        }
        try {
            getPlayerData().save(playerDataFile);
        } catch (IOException ex) {
            plugin.getLogger().severe("Could not save player data to " + playerDataFile);
        }
    }

    public void saveDefaultPlayerData() {
        if (playerDataFile == null) {
            playerDataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        }
        if (!playerDataFile.exists()) {
            plugin.saveResource("playerdata.yml", false);
        }
    }

    public void setPlayerData(UUID uuid, String key, Object value) {
        getPlayerData().set(uuid.toString() + "." + key, value);
        savePlayerData();
    }

    public Object getPlayerData(UUID uuid, String key) {
        return getPlayerData().get(uuid.toString() + "." + key);
    }

    public void saveAllData(PlayerStateHandler stateHandler) {
        for (Map.Entry<UUID, PlayerState> entry : stateHandler.getPlayerStates().entrySet()) {
            UUID uuid = entry.getKey();
            if (entry.getValue() == PlayerState.PENALIZED) {
                setPlayerData(uuid, "state", entry.getValue().toString());
                setPlayerData(uuid, "timer", stateHandler.getTimer(uuid));
            } else {
                getPlayerData().set(uuid.toString(), null); // Clear the record
            }
        }
        savePlayerData();
    }

    public void loadAllData(PlayerStateHandler stateHandler) {
        for (String key : getPlayerData().getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            String state = (String) getPlayerData(uuid, "state");
            if (state != null && state.equals("PENALIZED")) {
                stateHandler.setPlayerState(uuid, PlayerState.valueOf(state));
                Integer combatTime = (Integer) getPlayerData(uuid, "timer");
                if (combatTime != null) {
                    stateHandler.setTimer(uuid, combatTime);
                }
            }
        }
    }
}