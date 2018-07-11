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
import sensorserver.engine.tasks.ITasksSupplier;
import sensorserver.engine.tasks.NetScanTasksSupplier;
import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.workers.IScannerListener;
import sensorserver.engine.workers.IWorkersFactory;
import sensorserver.utils.mocks.ArpTableMock;
import sensorserver.utils.mocks.NetScannerMock;
import sensorserver.engine.workers.WorkersFactory;
import sensorserver.server.*;
import sensorserver.utils.NetworkUtils;
import sensorserver.utils.mocks.VendorsProviderMock;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Main {
    private static Logger logger = Logger.getLogger("LanSensorLogger");
    private static SensorRuntimeContext context;
    private static CommandsManager commandExecutor;
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        logger.info("Initializing...");
        Config config = ConfigFactory.load();

        SensorRuntimeContext.RunEnvironment runEnvironment = extractRunEnvironment(args);
        logger.info("environment: " + runEnvironment);

        ArpTable arpTable = null;
        IVendorsProvider vendorsProvider = null;
        IWorkersFactory<Runnable, ScanningTask> workersFactory = null;

        // Tasks & workers managers
        int pingTimeout = config.getInt("network.ping-timeout");
        ITasksSupplier<ScanningTask> tasksSupplier = new NetScanTasksSupplier(NetworkUtils.getLanIpsList(), pingTimeout);;

        // Lan entities holder
        LanEntitiesHolder entitiesHolder = new LanEntitiesHolder();
        entitiesHolder.entityIn().listen(Main::onEntityIn);
        entitiesHolder.entityOut().listen(Main::onEntityOut);

        if (runEnvironment == SensorRuntimeContext.RunEnvironment.PROD) {
            try {
                arpTable = new ArpTable();
                arpTable.refresh();
            } catch (IOException e) {
                logger.error("startup error: initializing arp table produces an error", e);
                System.exit(1);
            }
            vendorsProvider = new MacVendorsComProvider(config.getString("vendorsProvider.url"), Executors.newSingleThreadScheduledExecutor());
            workersFactory = new WorkersFactory();
        } else if (runEnvironment == SensorRuntimeContext.RunEnvironment.DEBUG) {
            try {
                arpTable = new ArpTableMock();
            } catch (IOException e) {
                logger.error("startup error: initializing mocked arp table produces an error", e);
                System.exit(1);
            }
            vendorsProvider = new VendorsProviderMock();
            workersFactory = new IWorkersFactory<Runnable, ScanningTask>() {
                @Override
                public Runnable create(IScannerListener listener, ScanningTask task) {
                    return new NetScannerMock(listener, task);
                }
            };
        }

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
        context = new SensorRuntimeContext(config, engine, server, runEnvironment);
        commandExecutor = new CommandsManager(context);
        server.onMessageReceived().listen(Main::commandsHandler);

        // Set shut down hook for system close
        Runtime.getRuntime().addShutdownHook(new Thread(Main::shutdownHook));

        logger.info("initialization completed.");
        System.out.println("Press ENTER to shutdown.");
        System.out.println("============================================================\n");

        // ****** Start *******
        context.start();
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

    private static SensorRuntimeContext.RunEnvironment extractRunEnvironment(String[] args) {
        if (Arrays.asList(args).contains("--debug")) {
            return SensorRuntimeContext.RunEnvironment.DEBUG;
        } else {
            return SensorRuntimeContext.RunEnvironment.PROD;
        }
    }
}