package at.fhhgb.mc.snake.log;

import java.util.logging.Level;


public class GLog {
    private static boolean enableLogging = false;

    public static void enableLogging() {
        GLog.enableLogging = true;
    }

    public static void disableLogging() {
        GLog.enableLogging = false;
    }

    public static void info(String message, Object... args) {
        GLog.write(Level.INFO, message, args);
    }


    public static void error(String message, Object... args) {
        GLog.write(Level.SEVERE, message, args);
    }

    public static void debug(String message, Object... args) {
        GLog.write(Level.FINE, message, args);
    }

    private static void write(Level level, String message, Object... args) {
        if (GLog.enableLogging) {
            System.out.printf("[%s] " + String.format(message, args) + System.lineSeparator(), level.getName());
        }
    }
}
