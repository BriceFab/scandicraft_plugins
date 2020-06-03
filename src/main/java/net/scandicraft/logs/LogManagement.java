package net.scandicraft.logs;

import net.scandicraft.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogManagement {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void info(String message) {
        if (canLog()) {
            LOGGER.info(format(message));
        }
    }

    public static void warn(String message) {
        if (canLog()) {
            LOGGER.warn(format(message));
        }
    }

    public static void error(String message) {
        if (canLog()) {
            LOGGER.error(format(message));
        }
    }

    private static String format(String messsage) {
        return String.format("[%s] %s", Config.SERVER_NAME, messsage);
    }

    private static boolean canLog() {
        return Config.ENV != Config.ENVIRONNEMENT.PROD;
    }

}
