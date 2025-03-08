package org.CombatLog.Utils;

import org.CombatLog.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessagesCombat {
    private static CombatLog plugin;
    private static final Map<String, String> messages = new HashMap<>();

    public static void initialize(CombatLog pluginInstance) {
        plugin = pluginInstance;
        loadMessages();
    }
    private static void loadMessages() {
        messages.put("outCombat", getMessage("messages.outCombat"));
        messages.put("inCombat", getMessage("messages.inCombat"));
        messages.put("inPenalize", getMessage("messages.inPenalize"));
        messages.put("broadcastPenalize", getMessage("messages.broadcastPenalize"));
        messages.put("broadcastDisconnected", getMessage("messages.broadcastDisconnected"));
        messages.put("outPenalize", getMessage("messages.outPenalize"));
    }
    private static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getMessagesConfig().getString(path));
    }
    public static void sendMessageOutCombat (UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
       if (player != null){
           player.sendMessage(messages.get("outCombat"));
       }
    }

    public static void sendMessageInCombat (UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if (player != null){
            player.sendMessage(messages.get("inCombat"));
        }
    }
    public static void sendMessageInPenalize (UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if (player != null){
            player.sendMessage(messages.get("inPenalize"));
        }
    }

    public static void broadcastMessagePenalize (UUID uuid){
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        Bukkit.broadcastMessage(messages.get("broadcastPenalize").replace("{player}", player.getName()));
    }

    public static void broadcastMessageDisconected (UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        Bukkit.broadcastMessage(messages.get("broadcastDisconnected").replace("{player}", player.getName()));
    }

    public static void sendMessageOutPenalize (UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if (player != null){
            player.sendMessage(messages.get("outPenalize"));
        }
    }
}
