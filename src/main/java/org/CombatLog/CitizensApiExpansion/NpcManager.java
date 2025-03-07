package org.CombatLog.CitizensApiExpansion;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.trait.HologramTrait;
import org.CombatLog.State.PlayerStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class NpcManager {
    private final NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());
    private final Map<UUID, NPC> npcs= new HashMap<>();

    private final PlayerStateHandler stateHandler;

    public NpcManager(PlayerStateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }
    /**
     * Crea un NPC en las coordenadas del jugador con su nombre.
     *
     * @param uuid UUID del jugador.
     */
    public void createNpc(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null || !player.isOnline()) {
            return;
        }

        NPC npc = registry.createNPC(EntityType.PIGLIN, player.getName()); //tipo y nombre

        HologramTrait hologram = npc.getOrAddTrait(HologramTrait.class);
        hologram.addLine("&cDesconectado");  //linea adicional

        npc.spawn(player.getLocation());
        npc.getEntity().setSilent(true); //muted

        // Opcional: Asignar la skin del jugador al NPC
        //npc.getOrAddTrait(SkinTrait.class).setSkinName(player.getName());
        npcs.put(uuid, npc);
    }
    /**
     * Elimina el NPC asociado al jugador.
     *
     * @param uuid UUID del jugador.
     */
    public void removeNpc(UUID uuid) {
        NPC npc = npcs.remove(uuid);
        if (npc != null) {
            npc.destroy();
            registry.deregister(npc);
        }
    }

    public void updateNpcs() {
        for (Map.Entry<UUID, NPC> entry : npcs.entrySet()) {
            UUID uuid = entry.getKey();
            NPC npc = entry.getValue();
            Integer timeOffline = stateHandler.getTimer(uuid);

            HologramTrait hologram = npc.getOrAddTrait(HologramTrait.class);
            hologram.setLine(0, "&cDesconectado " +"&f\uD83D\uDD51"+ timeOffline);
        }
    }

    public void clearAll(){
        npcs.forEach((key, npc) -> {
            npc.destroy();
            registry.deregister(npc);
        });
    }

}
