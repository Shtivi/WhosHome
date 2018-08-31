package sensorserver.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutionUtils {
    private static int DEFAULT_SHUTDOWN_TIMEOUT = 5000;

    public static void gracefulShutdown(ExecutorService executorService, int timeoutMiliseconds) {
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(timeoutMiliseconds, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {

        }
    }

    public static void gracefulShutdown(ExecutorService executorService) {
        gracefulShutdown(executorService, DEFAULT_SHUTDOWN_TIMEOUT);
    }
}
