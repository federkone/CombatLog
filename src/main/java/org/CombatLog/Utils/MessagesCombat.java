package org.CombatLog.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MessagesCombat {

    public static void sendMessageOutCombat (UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
       if (player != null){
           player.sendMessage(ChatColor.GREEN + "✅ Ya no estás en combate!");
       }
    }

    public static void sendMessageInCombat (UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if (player != null){
            player.sendMessage(ChatColor.RED + "❌ Has entrado en combate!  Durante 1 minuto, si te desconectas en combate y no regresas seras penalizado ❌");
        }
    }
    public static void sendMessageInPenalize (UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if (player != null){
            player.sendMessage(ChatColor.RED + "❌ Has sido PENALIZADO❌");
        }
    }

    public static void broadcastMessagePenalize (UUID uuid){
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        Bukkit.broadcastMessage(ChatColor.RED + "El jugador " + player.getName() + " ha sido PENALIZADO");
    }

    public static void broadcastMessageDisconected (UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        Bukkit.broadcastMessage(ChatColor.RED + "El jugador " + player.getName() + " se ha desconectado en combate, tiene 5 minutos para volver al juego o sera penalizado.");
    }

    public static void sendMessageOutPenalize (UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if (player != null){
            player.sendMessage(ChatColor.GREEN + "✅ Ya no estás penalizado! ✅");
        }
    }
}
