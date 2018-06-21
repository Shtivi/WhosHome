package sensorclient;

import sensorclient.commands.RequestPresentEntitiesCommand;
import sensorclient.commands.ShutdownEngineCommand;
import sensorclient.commands.StartEngineCommand;
import sensorclient.entities.LanEntity;
import sensorclient.events.DeviceConnectionEventArgs;
import sensorclient.events.ErrorEventArgs;
import sensorclient.events.StatusChangeEventArgs;
import sensorclient.exceptions.InvalidOperationException;

import java.util.Date;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static ISensorClient client = null;

    public static void main(String args[]) {
        System.out.println("Initializing");

        if (args.length == 0) {
            System.out.println("unspecified sensor service url");
            System.exit(1);
        }

        String url = args[0];
        client = new SensorClient(url);

        client.listen(new ISensorListener() {
            @Override
            public void onError(ErrorEventArgs args) {
                error(args.getTime(), String.format("%s - %s",
                        args.getError().getClass().getSimpleName(),
                        args.getError().getMessage()));
            }

            @Override
            public void onStatusChange(StatusChangeEventArgs args) {
                info(args.getTime(), String.format(
                        "status changed from '%s' to '%s': %s",
                        args.getOldStatus(), args.getNewStatus(), args.getReason()));
            }

            @Override
            public void onDeviceEvent(DeviceConnectionEventArgs args) {
                info(args.getTime(), String.format(
                        "device %s - %s, %s, %s, %s",
                        defaultIfNull(args.activityType().toString()),
                        defaultIfNull(args.getEntity().getIP()),
                        defaultIfNull(args.getEntity().getHostname()),
                        defaultIfNull(args.getEntity().getMAC()),
                        defaultIfNull(args.getEntity().getVendor())
                        ));
            }

            @Override
            public void allEntitiesFetched(Iterable<LanEntity> entities) {
                String rowFormat = "%-20s%-16s%-20s%-30s";
                System.out.println();
                System.out.println(String.format(rowFormat, "IP", "Hostname", "MAC", "Vendor"));
                System.out.println("--------------------------------------------------------------------------");

                entities.forEach(entity -> {
                    System.out.println(String.format(rowFormat,
                            defaultIfNull(entity.getIP()),
                            defaultIfNull(entity.getHostname()),
                            defaultIfNull(entity.getMAC()),
                            defaultIfNull(entity.getVendor())));
                });
                System.out.println();
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            info(new Date(), "cleaning up resources");

            if (client.getStatus() == SensorClientStatus.CONNECTED) {
                info(new Date(), "disconnecting from the server...");
                client.disconnect();
            }

            System.out.println("Bye!");
        }));

        System.out.println(String.format("Connecting to: %s...", url));
        client.connect();

        System.out.println("INITIALIZATION COMPLETED");
        System.out.println("========================");
        System.out.println();

        help();
        while (true) {
            if (input.hasNext()) {
                String userInput = input.nextLine();
                try {
                    handleUserInput(userInput);
                } catch (InvalidOperationException e) {
                    error(new Date(), "Invalid operation: " + e.getMessage());
                } catch (Exception e) {
                    error(new Date(), "Failure: " + e.getMessage());
                }
            }
        }
    }

    private static void info(Date time, String message) {
        System.out.println(String.format(
                "%s [INFO]  %s",
                time.toString(), message
        ));
    }

    private static void error(Date time, String errorMsg) {
        System.out.println(String.format(
                "%s [ERROR]  %s",
                time.toString(), errorMsg
        ));
    }

    private static void help() {
        System.out.println();
        System.out.println("connect - connects the server");
        System.out.println("disconnect - disconnects from the server");
        System.out.println("exit - disconnects & exits the application");
        System.out.println();
        System.out.println("Available sensor commands:");
        System.out.println("shutdownEngine - shuts down the sensor engine activity");
        System.out.println("startEngine - boots the sensor engine");
    }

    private static String defaultIfNull(String nullableString) {
        if (nullableString == null) {
            return "NULL";
        } else {
            return nullableString;
        }
    }

    private static void handleUserInput(String cmd) {
        switch (cmd) {
            case "help":
                help();
                break;
            case "connect":
                client.connect();
                break;
            case "disconnect":
                client.disconnect();
                break;
            case "exit":
                System.exit(0);
                break;
            case "shutdownEngine":
                client.sendCommand(new ShutdownEngineCommand());
                break;
            case "startEngine":
                client.sendCommand(new StartEngineCommand());
                break;
            case "allEntities":
                client.sendCommand(new RequestPresentEntitiesCommand());
                break;
            default:
                System.out.println("unknown command: " + cmd);
                break;
        }
    }
}
