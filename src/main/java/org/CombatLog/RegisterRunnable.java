package org.CombatLog;

import org.CombatLog.CombatLog;
import org.CombatLog.BukkitRunnable.Runnable;
import org.CombatLog.BukkitRunnable.RunnableNpc;
import org.bukkit.Bukkit;

public class RegisterRunnable {

    public static void register(CombatLog combatLog){
        if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
            RunnableNpc runnableNpc = new RunnableNpc(combatLog.getStateHandler(), combatLog.getScoreboard(),combatLog.getNpcManager());
            runnableNpc.runTaskTimer(combatLog, 0L, 20L);
        }else{
            Runnable runnable = new Runnable(combatLog.getStateHandler(), combatLog.getScoreboard());
            runnable.runTaskTimer(combatLog,0L,20L);
        }
        }
    }

