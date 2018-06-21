package sensorserver;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.log4j.Logger;
import sensorserver.commandExecutors.CommandsManager;
import sensorserver.dataProviders.IVendorsProvider;
import sensorserver.dataProviders.MacVendorsComProvider;
import sensorserver.engine.ArpTable;
import sensorserver.engine.Engine;
import sensorserver.engine.events.EntityEventArgs;
import sensorserver.engine.entities.LanEntitiesHolder;
import sensorserver.engine.tasks.NetScanTasksSupplier;
import sensorserver.engine.workers.WorkersFactory;
import sensorserver.server.*;
import sensorserver.utils.NetworkUtils;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Logger logger = Logger.getLogger("LanSensorLogger");
    private static SensorRuntimeContext context;
    private static CommandsManager commandExecutor;
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        // TODO: add a debug mode which mocking the workers contractor

        logger.info("Initializing...");
        Config config = ConfigFactory.load();

        // Arp table
        ArpTable arpTable = null;
        try {
            arpTable = new ArpTable();
        } catch (IOException e) {
            logger.error("startup error: initializing arp table produces an error", e);
            System.exit(1);
        }

        // Tasks & workers managers
        int pingTimeout = config.getInt("network.ping-timeout");
        NetScanTasksSupplier tasksSupplier = new NetScanTasksSupplier(NetworkUtils.getLanIpsList(), pingTimeout);
        WorkersFactory workersFactory = new WorkersFactory();

        // Lan entities holder
        LanEntitiesHolder entitiesHolder = new LanEntitiesHolder();
        entitiesHolder.entityIn().listen(Main::onEntityIn);
        entitiesHolder.entityOut().listen(Main::onEntityOut);

        // Providers
        IVendorsProvider vendorsProvider = new MacVendorsComProvider(config.getString("vendorsProvider.url"));

        // Engine object
        int numOfWorkers = config.getInt("engine.workers");
        int arpRefreshInterval = config.getInt("network.arp-refresh-interval");
        Engine engine = new Engine(
                tasksSupplier,
                workersFactory,
                entitiesHolder,
                arpTable,
                vendorsProvider,
                numOfWorkers,
                arpRefreshInterval);

        // Server object
        int serverPort = config.getInt("server.port");
        ISensorService server = new SensorService(serverPort);
        server.onClientConnection().listen(Main::clientConnectionHook);

        // Create the context object
        context = new SensorRuntimeContext(config, engine, server);
        commandExecutor = new CommandsManager(context);
        server.onMessageReceived().listen(Main::commandsHandler);

        // Set shut down hook for system close
        Runtime.getRuntime().addShutdownHook(new Thread(Main::shutdownHook));

        logger.info("initialization completed.");

        // ****** Start *******
        context.start();
        System.out.println("Press ENTER to shutdown.");
        input.nextLine();

        System.exit(0);
    }

    private static void onEntityIn(EntityEventArgs eventArgs) {
        context.getSensorService().broadcastEvent("entityEvent", eventArgs);
    }

    private static void onEntityOut(EntityEventArgs eventArgs) {
        context.getSensorService().broadcastEvent("entityEvent", eventArgs);
    }

    private static void commandsHandler(MessageReceivedEventArgs args) {
        String cmd = args.getMessage();
        logger.info(String.format("Command received: '%s'", cmd));
        try {
            commandExecutor.executeCommand(cmd, args.getAddress());
        } catch (Exception ex){
            logger.error("execution of command " + cmd + " failed", ex);
        }
    }

    private static void clientConnectionHook(ClientConnectionEventArgs args) {
        context.getSensorService().broadcastEvent(
                args.getAddress(),
                "allEntities",
                context.getEngine().getEntitiesHolder().getPresentEntities()
        );
    }

    private static void shutdownHook() {
        logger.info("Shutting down resources...");
        input.close();
        try {
            context.shutdown();
        } catch (Exception e) {
            logger.error("Error shutting down the server", e);
        }
    }
}

