package org.CombatLog.PlaceHolderApiExpAnsion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.CombatLog.State.PlayerStateHandler;
import org.CombatLog.Utils.TranslateStates;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CombatLogPlaceHolder  extends PlaceholderExpansion {
    private final PlayerStateHandler playerStateHandler;
    public CombatLogPlaceHolder(PlayerStateHandler playerStateHandler) {
        this.playerStateHandler = playerStateHandler;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "CombatLog";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Nullplague";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("status")) {
            return TranslateStates.translateState(playerStateHandler.getPlayerState(player.getUniqueId()));
        }

        if (identifier.equals("time")) {
           return String.valueOf(playerStateHandler.getTimer(player.getUniqueId()));
        }

        return null;
    }
}