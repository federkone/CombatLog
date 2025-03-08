package org.CombatLog.State;

import org.CombatLog.Events.*;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStateHandler {
    private final Map<UUID, PlayerState> playerStates = new ConcurrentHashMap<>();  //esta lista se puede dejar registrada en una DB
    private final Map<UUID, Integer> timers = new ConcurrentHashMap<>();
    private int timeCombat ; // 60 segundos
    private int timePenalized ; // 500 segundos
    private int timeDisconnected ; // 300 segundos

    public PlayerStateHandler( int timeCombat, int timeDisconnected, int timePenalized) {
        this.timeCombat = timeCombat;
        this.timePenalized = timePenalized;
        this.timeDisconnected = timeDisconnected;
    }

    //todo:manejo de timers y estados global------------------------------------------------------->>
    public void updateTimers(){
        timers.entrySet().forEach(entry -> {
            int time = entry.getValue();
            UUID uuid = entry.getKey();
            if (isPenalized(uuid) && Bukkit.getPlayer(uuid) == null ) {//podemos concatenar preguntar si está penalizado para omitir la actualizacion del tiempo "perma penalizado" ,hasta que se cambie el estado desde otro sitio
                return; // Si está penalizado y no está en línea, no actualizar el tiempo
            }
            if (!isIdle(uuid)) {
                if (time<=0){
                    changeState(entry);
                } else {
                    entry.setValue(time - 1);
                }
            }
        });
    }

    private void changeState(Map.Entry<UUID,Integer> entry){
        UUID uuid = entry.getKey();

        if (isPenalized(uuid) ){
            setPlayerState(uuid, PlayerState.IDLE);
            onChangeState event = new onUnpenalized(uuid);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        if (inCombat(uuid)){
            setPlayerState(uuid, PlayerState.IDLE);
            onChangeState event = new onActivateIdle(uuid);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        if (isDisconnected(uuid)){
            setPlayerState(uuid, PlayerState.PENALIZED);
            onChangeState event = new onActivatePenalized(uuid,true);
            Bukkit.getServer().getPluginManager().callEvent(event);
            entry.setValue(timePenalized);   //500 segundos
        }
    }

   //todo:remover estados y tiempos----------------------------------------------------->>
    public void removeState(UUID uuid){
        playerStates.remove(uuid);
        removeTimer(uuid);
    }

    public void removeTimer(UUID uuid){
        timers.remove(uuid);
    }

    //todo:obtener TIEMPOS------------------------------------------------------------>>>
    public Integer getTimer(UUID uuid){
        return timers.getOrDefault(uuid,0);
    }

    public Map<UUID,Integer> getTimers(){
        return timers;
    }

    //todo:setear TIEMPOS-------------------------------------------------------------->>>
    public void setTimer(UUID uuid, int time){
        timers.put(uuid, time);
    }

    //todo:actualizar ESTADOS ------------------------------------------------------------->>
    public void setPlayerState(UUID uuid, PlayerState state) {
        playerStates.put(uuid, state);
    }

    public void activateCombat(UUID uuid){
        if (isPenalized(uuid)){
            return;
        }
        if (inCombat(uuid)) {
            setTimer(uuid, timeCombat); // 60 segundos
            return;
        }
        setPlayerState(uuid, PlayerState.COMBAT);
        onChangeState event = new onActivateCombat(uuid);
        Bukkit.getServer().getPluginManager().callEvent(event);
        setTimer(uuid, timeCombat); // 60 segundos
    }

    public void activateIDLE(UUID uuid){
        setPlayerState(uuid, PlayerState.IDLE);
        onChangeState event = new onActivateIdle(uuid);
        Bukkit.getServer().getPluginManager().callEvent(event);
        setTimer(uuid, 0);
    }

    public void activatePenalize(UUID uuid){
        setPlayerState(uuid, PlayerState.PENALIZED);
        onChangeState event = new onActivatePenalized(uuid,true);
        Bukkit.getServer().getPluginManager().callEvent(event);
        setTimer(uuid, timePenalized); // 500 segundos
    }

    public void activateDisconnected(UUID uuid){
        setPlayerState(uuid, PlayerState.DISCONNECTED);
        onChangeState event = new onActivateDisconnected(uuid);
        Bukkit.getServer().getPluginManager().callEvent(event);
        setTimer(uuid, timeDisconnected); // 300 segundos
    }

    public void resetAll(){
        playerStates.entrySet().forEach(entry ->{
            UUID uuid = entry.getKey();
            PlayerState state = entry.getValue();
            if ( state == PlayerState.PENALIZED){
                onChangeState event = new onUnpenalized(uuid);
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
            entry.setValue(PlayerState.IDLE);
            setTimer(uuid,0);
        });
    }

    //todo:OBtener ESTADOS --------------------------------------------------------------->>
    public Map<UUID, PlayerState> getPlayerStates() {
        return playerStates;
    }

    public PlayerState getPlayerState(UUID uuid) {
        return playerStates.getOrDefault(uuid,PlayerState.IDLE);
    }

    public boolean hasState(UUID uuid) {
        return playerStates.containsKey(uuid);
    }

    public boolean isPenalized(UUID uuid){
        return getPlayerState(uuid)==PlayerState.PENALIZED;
    }

    public boolean isIdle(UUID uuid){
        return getPlayerState(uuid)==PlayerState.IDLE;
    }

    public boolean inCombat(UUID uuid){
        return getPlayerState(uuid)==PlayerState.COMBAT;
    }

    public boolean isDisconnected(UUID uuid){
        return getPlayerState(uuid)==PlayerState.DISCONNECTED;
    }
}