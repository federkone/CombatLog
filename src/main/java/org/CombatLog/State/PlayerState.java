package org.CombatLog.State;
public enum PlayerState {
    COMBAT,  // Estado cuando el jugador recibe daño o ataca
    IDLE,     // Estado cuando el jugador no ha recibido daño ni ha atacado durante 1 minuto
    DISCONNECTED, // Estado cuando el jugador se desconecta en combate y no termina la cuenta regresiva
    //DEAD, // Estado cuando el jugador ha muerto estando desconectado
    PENALIZED // Estado cuando el jugador ha sido penalizado
}

