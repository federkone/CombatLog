package org.CombatLog.Commands;

import org.CombatLog.State.PlayerState;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandStates implements CommandExecutor {
    PlayerStateHandler stateHandler;

    public CommandStates( PlayerStateHandler stateHandler){
        this.stateHandler = stateHandler;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("combatlog.command.states")) {
            sender.sendMessage("§cNo tienes permisos para ejecutar este comando.");
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage("§cUso: /changeState <jugador> <estado> <tiempo>");
            return false;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

        if(player==null) {
            sender.sendMessage("§cEl jugador no existe.");
            return false;
        }

        //capturar estado valido
        PlayerState state;
        try {
            state = PlayerState.valueOf(args[1].toUpperCase());
        }catch (IllegalArgumentException e){
            sender.sendMessage("§cEstado no válido. los estados son IDLE, COMBAT, DISCONNECTED, PENALIZED");
            return false;
        }

        int time;
        try {
            time = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cEl tiempo debe ser un número entero.");
            return false;
        }

        switch (state){
            case IDLE:
                stateHandler.activateIDLE(player.getUniqueId());
                break;
            case COMBAT:
                stateHandler.activateCombat(player.getUniqueId());
                break;
            case DISCONNECTED:
                stateHandler.activateDisconnected(player.getUniqueId());
                break;
            case PENALIZED:
                stateHandler.activatePenalize(player.getUniqueId());
                break;
        }

        return true;
    }
}
