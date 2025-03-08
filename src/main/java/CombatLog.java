package org.CombatLog;

import net.citizensnpcs.trait.CommandTrait;
import org.CombatLog.CitizensApiExpansion.NpcManager;
import org.CombatLog.Config.File;
import org.CombatLog.Config.PlayerDataFile;
import org.CombatLog.Scoreboards.ScoreboardCombat;
import org.CombatLog.State.PlayerStateHandler;
import org.CombatLog.Utils.MessagesCombat;
import org.CombatLog.Utils.TranslateStates;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class CombatLog extends JavaPlugin {
    private FileConfiguration messagesConfig;
    private  PlayerStateHandler stateHandler;
    private NpcManager npcManager=null ;
    private PlayerDataFile playerDataFile;
    private ScoreboardCombat scoreboard;
    private RegisterEvents events;

    @Override
    public void onLoad() {
        File file = new File(this);
        file.loadConfig();
        loadMessagesConfig();

        int timeCombat = getConfig().getInt("TimeCombat");
        int timeDisconnected = getConfig().getInt("TimeDisconnected");
        int timePenalize = getConfig().getInt("TimePenalize");

        stateHandler = new PlayerStateHandler(timeCombat, timeDisconnected, timePenalize);
        playerDataFile = new PlayerDataFile(this); //data player
        playerDataFile.loadAllData(stateHandler);

        scoreboard = new ScoreboardCombat(stateHandler);
    }

    @Override
    public void onEnable() {
        MessagesCombat.initialize(this);
        TranslateStates.initialize(this);
        events = new RegisterEvents().register(this);
        RegisterCommands.register(this);
        RegisterRunnable.register(this);

        this.getServer().getConsoleSender().sendMessage("Plugin LOG DE COMBATE habilitado");
    }

    @Override
    public void onDisable() {
        playerDataFile.saveAllData(stateHandler);
        this.getServer().getConsoleSender().sendMessage("Plugin LOG DE COMBATE deshabilitado");
    }

    public PlayerStateHandler getStateHandler(){
        return this.stateHandler;
    }

    public NpcManager getNpcManager(){
        return this.npcManager;
    }

    public void setNpcManager(NpcManager npcManager){
        this.npcManager=npcManager;
    }

    public ScoreboardCombat getScoreboard(){
        return this.scoreboard;
    }

    public void disable(){
        Bukkit.getScheduler().cancelTasks(this); //runnables
        events.unregisterCombatLister();
        events.unregisterPlayerJoinLister();
        events.unregisterPlayerLeaveLister();
        this.getStateHandler().resetAll();
        if(npcManager != null){
            this.getNpcManager().clearAll();
        }
        events.unregisterAll(this);

        this.getServer().getConsoleSender().sendMessage("Plugin LOG DE COMBATE deshabilitado");
    }

    public void enable(){
        events = new RegisterEvents().register(this);
        RegisterRunnable.register(this);
    }

    private void loadMessagesConfig() {
        java.io.File messagesFile = new java.io.File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }
}
