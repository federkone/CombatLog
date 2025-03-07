package org.CombatLog.Utils;

import org.CombatLog.State.PlayerState;

public class TranslateStates {

    public static String translateState(PlayerState state){
        return switch (state) {
            case COMBAT -> "&cEn combate";
            case IDLE -> "&aTranquilo";
            case PENALIZED -> "&cPENALIZADO";
            case DISCONNECTED -> "&cDesconectado";
        };
    }
}
