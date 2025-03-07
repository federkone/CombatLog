package org.CombatLog.Logs;

public class Debug {

    public static void log(String message){
        System.out.println( message);
    }

    public static void logError(String message){
        System.err.println( message);
    }
}
