package org.CombatLog;

import org.CombatLog.CombatLog;
import org.CombatLog.Commands.CommandStates;
import org.CombatLog.Commands.ResetAllStates;

public class RegisterCommands {

    public static void register(CombatLog combatLog){
        combatLog.getCommand("changeState").setExecutor(new CommandStates(combatLog.getStateHandler()));
        combatLog.getCommand("combatLog").setExecutor(new ResetAllStates(combatLog));
    }

}
