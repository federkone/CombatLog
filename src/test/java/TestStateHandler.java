
import org.CombatLog.State.PlayerState;
import org.CombatLog.State.PlayerStateHandler;


import java.util.UUID;



/*class TestStateHandler {

    private PlayerStateHandler stateHandler;
    private UUID playerUUID;

    @BeforeEach
    void setUp() {
        stateHandler = new PlayerStateHandler();
        playerUUID = UUID.randomUUID();
        //stateHandler.setPlayerState(playerUUID, PlayerState.IDLE);
    }

    @Test
    void activateCombat_setsPlayerStateToActive() {
        stateHandler.activateCombat(playerUUID);
        assertTrue(stateHandler.isActive(playerUUID));
    }

    @Test
    void deactivateCombat_setsPlayerStateToIdle() {
        stateHandler.activateCombat(playerUUID);
        stateHandler.deactivateCombat(playerUUID);
        assertTrue(stateHandler.isIdle(playerUUID));
    }

    @Test
    void disconnectOnCombat_setsPlayerStateToDisconnected() {
        stateHandler.activateCombat(playerUUID);
        stateHandler.disconnectOnCombat(playerUUID);
        assertTrue(stateHandler.isDisconnected(playerUUID));
    }

    @Test
    void penalize_setsPlayerStateToPenalized() {
        stateHandler.disconnectOnCombat(playerUUID);
        stateHandler.updateTimerPenalize();
        assertFalse(stateHandler.isPenalized(playerUUID));
    }

    @Test
    void updateTimerCombat_deactivatesCombatWhenTimeExpires() {
        stateHandler.activateCombat(playerUUID);
        for (int i = 0; i < 61; i++) {
            stateHandler.udapteTimerCombat();
        }
        assertTrue(stateHandler.isIdle(playerUUID));
    }

    @Test
    void updateTimerPenalize_penalizesWhenTimeExpires() {
        stateHandler.disconnectOnCombat(playerUUID);
        for (int i = 0; i < 301; i++) {
            stateHandler.updateTimerPenalize();
        }
        assertTrue(stateHandler.isPenalized(playerUUID));
    }

    @Test
    void getCombatTime_returnsCorrectTime() {
        stateHandler.activateCombat(playerUUID);
        assertEquals(60, stateHandler.getCombatTime(playerUUID));
    }

    @Test
    void cancelTimerCombat_removesCombatTimer() {
        stateHandler.activateCombat(playerUUID);
        stateHandler.cancelTimerCombat(playerUUID);
        assertEquals(0, stateHandler.getCombatTime(playerUUID));
    }

    @Test
    void cancelTimerPenalize_removesPenalizeTimer() {
        stateHandler.disconnectOnCombat(playerUUID);
        stateHandler.cancelTimerPenalize(playerUUID);
        assertFalse(stateHandler.isPenalized(playerUUID));
    }
}*/