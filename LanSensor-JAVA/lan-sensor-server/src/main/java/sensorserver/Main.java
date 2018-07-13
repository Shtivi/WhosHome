package sensorserver;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import sensorserver.commandExecutors.CommandsManager;
import sensorserver.dataProviders.vendors.*;
import sensorserver.engine.ArpTable;
import sensorserver.engine.Engine;
import sensorserver.engine.events.EntityEventArgs;
import sensorserver.engine.entities.LanEntitiesHolder;
import sensorserver.engine.tasks.ITasksSupplier;
import sensorserver.engine.tasks.NetScanTasksSupplier;
import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.workers.IScannerListener;
import sensorserver.engine.workers.IWorkersFactory;
import sensorserver.utils.FileUtils;
import sensorserver.utils.mocks.ArpTableMock;
import sensorserver.utils.mocks.NetScannerMock;
import sensorserver.engine.workers.WorkersFactory;
import sensorserver.server.*;
import sensorserver.utils.NetworkUtils;
import sensorserver.utils.mocks.VendorsProviderMock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Logger logger = Logger.getLogger("LanSensorLogger");
    private static SensorRuntimeContext context;
    private static CommandsManager commandExecutor;
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        logger.info("Initializing...");
        Config config = ConfigFactory.load();
        CommandLine cli = null;

        try {
            cli = parseArgs(args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        SensorRuntimeContext.Environment environment =
                SensorRuntimeContext.Environment.valueOf(cli.getOptionValue("environment").toUpperCase());
        logger.info("environment: " + environment);

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

        if (environment == SensorRuntimeContext.Environment.PROD) {
            try {
                String opeartionSysetem = cli.getOptionValue("os", "windows");
                arpTable = new ArpTable(config.getString(String.format("network.arp-command.%s", opeartionSysetem)));
                arpTable.refresh();
            } catch (IOException e) {
                logger.error("startup error: initializing arp table produces an error", e);
                System.exit(1);
            }
            String cachePath = config.getString("vendorsProvider.cachePath");
            IVendorsProvider mac2vendorProvider = new Mac2VendorProvider(config.getString("vendorsProvider.url"));
            try {
                vendorsProvider = new VendorsManager(new VendorsFileCache(cachePath, new FileUtils()), mac2vendorProvider);
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage(), e);
                System.exit(1);
            }
            workersFactory = new WorkersFactory();
        } else if (environment == SensorRuntimeContext.Environment.DEBUG) {
            try {
                arpTable = new ArpTableMock();
            } catch (IOException e) {
                logger.error("startup error: initializing mocked arp table produces an error", e);
                System.exit(1);
            }
            IVendorsCache cache = new IVendorsCache() {
                @Override
                public String lookup(String mac) {
                    return null;
                }

                @Override
                public void saveEntry(String mac, String vendor) {
                }
            };
            vendorsProvider = new VendorsManager(cache , new VendorsProviderMock());
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
        context = new SensorRuntimeContext(config, engine, server, environment, cli);
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

    private static CommandLine parseArgs(String[] args) throws ParseException {
        Option environmentOption = Option.builder("environment")
                .desc("Set the environment configuration [prod / debug}")
                .hasArg()
                .required()
                .build();
        Option osOption = Option.builder("os")
                .desc("Set the operation system name you are currently running [windows / linux]")
                .hasArg()
                .build();

        Options cliOptions = new Options()
                .addOption(environmentOption)
                .addOption(osOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(cliOptions, args);
        return cmd;
    }
}