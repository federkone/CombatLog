package org.CombatLog.Utils;

import org.CombatLog.CombatLog;
import org.CombatLog.State.PlayerState;
import org.bukkit.ChatColor;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class TranslateStates {
    private static CombatLog plugin;
    private static final Map<PlayerState, String> stateMessages = new HashMap<>();

    public static void initialize(CombatLog pluginInstance) {
        plugin = pluginInstance;
        loadStateMessages();
    }

    private static void loadStateMessages() {
        stateMessages.put(PlayerState.COMBAT, getMessage("messages.state.combat"));
        stateMessages.put(PlayerState.IDLE, getMessage("messages.state.idle"));
        stateMessages.put(PlayerState.PENALIZED, getMessage("messages.state.penalized"));
        stateMessages.put(PlayerState.DISCONNECTED, getMessage("messages.state.disconnected"));
    }
    private static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getMessagesConfig().getString(path));
    }

    public static String translateState(PlayerState state) {
         return stateMessages.get(state);
    }
}
