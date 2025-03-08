package org.CombatLog.Commands;

import org.CombatLog.CombatLog;
import org.CombatLog.State.PlayerState;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CombatLogCommand implements CommandExecutor {
    private final CombatLog plugin;
    private final PlayerStateHandler stateHandler;

    public CombatLogCommand(CombatLog plugin) {
        this.plugin = plugin;
        this.stateHandler = plugin.getStateHandler();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /combatlog <enable|disable|changestate>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "enable":
                if (sender.hasPermission("combatlog.command.enable")) {
                    plugin.enable();
                    sender.sendMessage("§aEl plugin CombatLog ha sido habilitado.");
                } else {
                    sender.sendMessage("§cNo tienes permisos para ejecutar este comando.");
                }
                break;
            case "disable":
                if (sender.hasPermission("combatlog.command.disable")) {
                    plugin.disable();
                    sender.sendMessage("§aEl plugin CombatLog ha sido deshabilitado.");
                } else {
                    sender.sendMessage("§cNo tienes permisos para ejecutar este comando.");
                }
                break;
            case "changestate":
                if (sender.hasPermission("combatlog.command.states")) {
                    if (args.length < 4) {
                        sender.sendMessage("§cUsage: /combatlog changestate <jugador> <estado> <tiempo>");
                    } else {
                        // Handle changeState logic here
                        // Example: stateHandler.changeState(args[1], args[2], args[3]);
                        OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);

                        if(player==null) {
                            sender.sendMessage("§cEl jugador no existe.");
                            return false;
                        }

                        PlayerState state;
                        try {
                            state = PlayerState.valueOf(args[2].toUpperCase());
                        }catch (IllegalArgumentException e){
                            sender.sendMessage("§cEstado no válido. los estados son IDLE, COMBAT, DISCONNECTED, PENALIZED");
                            return false;
                        }

                        int time;
                        try {
                            time = Integer.parseInt(args[3]);
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

                        sender.sendMessage("§aEstado cambiado para el jugador " + args[1]);
                        return true;
                    }
                } else {
                    sender.sendMessage("§cNo tienes permisos para ejecutar este comando.");
                }
                break;
            default:
                sender.sendMessage("§cUsage: /combatlog <enable|disable|changestate>");
                break;
        }
        return true;
    }
}
