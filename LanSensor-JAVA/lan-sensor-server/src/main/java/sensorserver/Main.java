package sensorserver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import sensorserver.commandExecutors.CommandsManager;
import sensorserver.dataProviders.vendors.*;
import sensorserver.engine.ArpTable;
import sensorserver.engine.Engine;
import sensorserver.engine.entities.IEntitiesHolder;
import sensorserver.engine.entities.LanEntity;
import sensorserver.engine.events.EntityEventArgs;
import sensorserver.engine.tasks.ITasksSupplier;
import sensorserver.engine.tasks.NetScanTasksSupplier;
import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.workers.IWorkersFactory;
import sensorserver.utils.HibernateUtils;
import sensorserver.server.*;
import sensorserver.utils.NetworkUtils;

import java.util.Scanner;

public class Main {
    private static Logger logger = Logger.getLogger("LanSensorLogger");
    private static SensorRuntimeContext context;
    private static CommandsManager commandExecutor;
    private static Scanner input = new Scanner(System.in);

    // TODO: 7/14/2018 add ignored devices list
    // TODO: 7/16/2018 Fix engine main algo to stop when pressing enter
    // TODO: 7/16/2018 add history of recognized and unknown devices

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
        String operatingSystem = cli.getOptionValue("os");
        logger.info("environment: " + environment);
        logger.info("operating system configuration: " + operatingSystem);

        Injector injector = Guice.createInjector(new SensorSeverModule(config, environment, operatingSystem));

        // Tasks & workers managers
        int pingTimeout = config.getInt("network.ping-timeout");
        ITasksSupplier<ScanningTask> tasksSupplier = new NetScanTasksSupplier(NetworkUtils.getLanIpsList(), pingTimeout);

        // Lan entities holder
        IEntitiesHolder<LanEntity> entitiesHolder = injector.getInstance(IEntitiesHolder.class);
        entitiesHolder.entityIn().listen(Main::onEntityIn);
        entitiesHolder.entityOut().listen(Main::onEntityOut);

        ArpTable arpTable = injector.getInstance(ArpTable.class);
        VendorsManager vendorsManager = injector.getInstance(VendorsManager.class);
        IWorkersFactory<Runnable, ScanningTask> workersFactory = injector.getInstance(IWorkersFactory.class);

        // Engine object
        int numOfWorkers = config.getInt("engine.workers");
        int arpRefreshInterval = config.getInt("network.arp-refresh-interval");
        Engine engine = new Engine(
                tasksSupplier,
                workersFactory,
                entitiesHolder,
                arpTable,
                vendorsManager,
                numOfWorkers,
                arpRefreshInterval);

        // Server object
        ISensorService server = injector.getInstance(ISensorService.class);
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
        HibernateUtils.getSessionFactory().getCurrentSession().close();
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