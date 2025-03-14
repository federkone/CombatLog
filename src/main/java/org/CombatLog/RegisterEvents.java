package org.CombatLog;

import org.CombatLog.CombatLog;
import org.CombatLog.CitizensApiExpansion.NpcManager;
import org.CombatLog.Listeners.Customs.AbstractChangeStateListener;
import org.CombatLog.Listeners.Customs.ChangeStateListener;
import org.CombatLog.Listeners.Customs.ChangeStateListenerNpc;
import org.CombatLog.Listeners.Penalized.AllEventsCancelable;
import org.CombatLog.Listeners.TriggerStates.Combat.AbstractCombatListeners;
import org.CombatLog.Listeners.TriggerStates.Combat.CombatListeners;
import org.CombatLog.Listeners.TriggerStates.Combat.CombatListenersNpc;
import org.CombatLog.Listeners.TriggerStates.JoinLeave.APlayerJoinListener;
import org.CombatLog.Listeners.TriggerStates.JoinLeave.PlayerJoinListener;
import org.CombatLog.Listeners.TriggerStates.JoinLeave.PlayerJoinNpcListener;
import org.CombatLog.Listeners.TriggerStates.JoinLeave.PlayerLeaveListener;
import org.CombatLog.PlaceHolderApiExpAnsion.CombatLogPlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;

public class RegisterEvents {
    public AbstractCombatListeners combatListeners;
    public AbstractChangeStateListener changeStateListener;
    public AllEventsCancelable allEventsCancelable;
    public APlayerJoinListener playerJoinListener;
    public PlayerLeaveListener playerLeaveListener;


    public RegisterEvents register(CombatLog combatLog){
        PluginManager pluginManager = combatLog.getServer().getPluginManager();
        //citizens
        if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
            combatLog.setNpcManager(new NpcManager(combatLog.getStateHandler()));
            changeStateListener =new ChangeStateListenerNpc(combatLog.getNpcManager());
            combatListeners = new CombatListenersNpc(combatLog.getStateHandler());
            playerJoinListener=new PlayerJoinNpcListener(combatLog);

            pluginManager.registerEvents(changeStateListener, combatLog);
            pluginManager.registerEvents(combatListeners, combatLog);
            combatLog.getLogger().info("Citizens found. Citizens expansion enabled.");
        } else {
            combatLog.setNpcManager(null);
            changeStateListener = new ChangeStateListener();
            combatListeners =new CombatListeners(combatLog.getStateHandler());
            playerJoinListener=new PlayerJoinListener(combatLog);

            pluginManager.registerEvents(changeStateListener, combatLog);
            pluginManager.registerEvents(combatListeners, combatLog);
            combatLog.getLogger().warning("Citizens not found. Citizens expansion won't be loaded.");
        }

        // PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new CombatLogPlaceHolder(combatLog.getStateHandler()).register();
            combatLog.getLogger().info("PlaceholderAPI Expansion registered successfully!");
        } else {
            combatLog.getLogger().warning("PlaceholderAPI not found. Expansion won't be loaded.");
        }

        allEventsCancelable=new AllEventsCancelable(combatLog.getStateHandler());
        playerLeaveListener=new PlayerLeaveListener(combatLog.getStateHandler());

        pluginManager.registerEvents(allEventsCancelable, combatLog);
        pluginManager.registerEvents(playerJoinListener, combatLog);
        pluginManager.registerEvents(playerLeaveListener, combatLog);

        return this;
    }

    public void unregisterCombatLister(){
        HandlerList.unregisterAll(combatListeners);//eventos trigger de combate
    }

    public void unregisterAllEventsCancellable(){
        HandlerList.unregisterAll(allEventsCancelable);//eventos limitaciones de penalizados
    }

    public void unregisterChangeStateListener(){
        HandlerList.unregisterAll(changeStateListener);//eventos de cambios de estado
    }

    public void unregisterPlayerJoinLister(){
        HandlerList.unregisterAll(playerJoinListener);//eventos playerJoinListener
    }

    public void unregisterPlayerLeaveLister(){
        HandlerList.unregisterAll(playerLeaveListener);//eventos playerJoinListener
    }

    public void unregisterAll(CombatLog combatLog){
        HandlerList.unregisterAll(combatLog);//todos los eventosde este plugin
    }
}
