package org.CombatLog.State;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStateHandler {
    private final Map<UUID, PlayerState> playerStates = new HashMap<>();  //esta lista se puede dejar registrada en una DB
    private final Map<UUID, Integer> timersCombat = new HashMap<>();
    private final Map<UUID, Integer> timersPenalize = new HashMap<>();

    public int getCombatTime(UUID uuid) {
        return timersCombat.getOrDefault(uuid, 0);
    }

    public void setPlayerState(UUID uuid, PlayerState state) {
        playerStates.put(uuid, state);
    }

    public PlayerState getPlayerState(UUID uuid) {
        return playerStates.getOrDefault(uuid, PlayerState.IDLE);
    }

    public boolean hasState(UUID uuid) {
        return playerStates.containsKey(uuid);
    }

    private void startTimerCombat(UUID uuid) {
        timersCombat.put(uuid, 60); // 60 segundos
    }

    private void startTimerPenalized(UUID uuid) {
        timersPenalize.put(uuid, 300); // 300 segundos
    }

    public void activateCombat(UUID uuid){
        setPlayerState(uuid, PlayerState.ACTIVE);
        startTimerCombat(uuid);

    }

    public void deactivateCombat(UUID uuid){
        setPlayerState(uuid, PlayerState.IDLE);
    }

    private void penalize(UUID uuid){
        setPlayerState(uuid, PlayerState.PENALIZED);
    }

    public void disconnectOnCombat(UUID uuid){
        setPlayerState(uuid, PlayerState.DISCONNECTED);
        startTimerPenalized(uuid);
    }

    public void cancelTimerCombat(UUID uuid){
        timersCombat.remove(uuid);
    }

    public void cancelTimerPenalize(UUID uuid){
        timersPenalize.remove(uuid);
    }

    public void udapteTimerCombat(){
        timersCombat.entrySet().forEach(entry -> {
            int time = entry.getValue();
            if (time <= 0) {
                deactivateCombat(entry.getKey());
                //llamar a apagar scoreboard
            } else {
                entry.setValue(time - 1);
                //llamar a actualizar scoreboard
            }
        });
    }

    public void updateTimerPenalize(){
        timersPenalize.entrySet().forEach(entry -> {
            int time = entry.getValue();
            if (time <= 0) {
                penalize(entry.getKey());
            } else {
                entry.setValue(time - 1);
            }
        });
    }

    //check estatus
    public boolean isPenalized(UUID uuid){
        return getPlayerState(uuid)==PlayerState.PENALIZED;
    }

    public boolean isIdle(UUID uuid){
        return getPlayerState(uuid)==PlayerState.IDLE;
    }
    public boolean isActive(UUID uuid){
        return getPlayerState(uuid)==PlayerState.ACTIVE;
    }

    public boolean isDisconnected(UUID uuid){
        return getPlayerState(uuid)==PlayerState.DISCONNECTED;
    }
}