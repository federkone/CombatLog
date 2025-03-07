package org.CombatLog.Commands;

import org.CombatLog.CombatLog;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ResetAllStates implements CommandExecutor {
    private final CombatLog combatLog;

    public ResetAllStates(CombatLog combatLog){
        this.combatLog=combatLog;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("combatlog.command.states")) {
            sender.sendMessage("§cNo tienes permisos para ejecutar este comando.");
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage("§cUso: /combatLog enable/disable");
            return false;
        }

        if (args[0].equals("enable")){
            combatLog.enable();
        }else  if (args[0].equals("disable")){
            combatLog.disable();
        }else{
            sender.sendMessage("§cUso: /combatLog enable/disable");
        }





        return true;
    }
}
