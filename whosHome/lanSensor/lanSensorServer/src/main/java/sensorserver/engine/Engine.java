package sensorserver.engine;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import sensorserver.dataProviders.vendors.VendorsManager;
import sensorserver.engine.entities.IEntitiesHolder;
import sensorserver.engine.entities.LanEntity;
import sensorserver.engine.events.ShutdownEventArgs;
import sensorserver.engine.events.StartupEventArgs;
import sensorserver.engine.tasks.ITasksSupplier;
import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.tasks.ScanningTaskResult;
import sensorserver.engine.workers.IContractor;
import sensorserver.engine.workers.IScannerListener;
import sensorserver.engine.workers.IWorkersFactory;
import sensorserver.engine.workers.ScanningWorkersContractor;
import sensorserver.events.Event;
import sensorserver.exceptions.InvalidOperationException;
import sensorserver.utils.ExecutionUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Engine {
    private static final int MAX_THREADS = 12;
    private static final int MIN_ARP_INTERVAL = 100;

    private Logger _logger;
    private ITasksSupplier<ScanningTask> _tasksSupplier;
    private IContractor<ScanningTask> _contractor;
    private IEntitiesHolder<LanEntity> _entitiesHolder;
    private ArpTable _arpTable;
    private VendorsManager _vendorsProvider;
    private EngineStatus _status;
    private ExecutorService _engineRunnerExecutor;
    private ExecutorService _netScanningTasksExecutor;
    private ScheduledExecutorService _arpUpdateExecutor;
    private int _parallelism;
    private int _arpInterval;

    private Event<ShutdownEventArgs> _engineShutdownEvent;
    private Event<StartupEventArgs> _engineStartedEvent;

    public Engine(ITasksSupplier<ScanningTask> tasksSupplier,
                  IWorkersFactory<Runnable, ScanningTask> workersFactory,
                  IEntitiesHolder<LanEntity> entitiesHolder,
                  ArpTable arpTable,
                  VendorsManager vendorsProvider,
                  int parallelism,
                  int arpInterval) {
        this._logger = Logger.getLogger("EngineLogger");
        this._logger.info("engine object creation started");

        this.setParallelism(parallelism);
        this.setArpInterval(arpInterval);
        this._tasksSupplier = tasksSupplier;
        NetScannerListener tasksListener = new NetScannerListener();
        this._contractor = new ScanningWorkersContractor(tasksListener, workersFactory, parallelism);
        this._entitiesHolder = entitiesHolder;
        this._arpTable = arpTable;
        this._vendorsProvider = vendorsProvider;

        this._engineShutdownEvent = new Event<>();
        this._engineStartedEvent = new Event<>();

        this._status = EngineStatus.AVAILABLE;
        this._logger.info("engine created");
    }

    public int getParallelism() {
        return this._parallelism;
    }

    public EngineStatus getStatus() {
        return this._status;
    }

    public void start() {
        this._logger.info("starting engine activity");

        if (this._status != EngineStatus.AVAILABLE) {
            throw new InvalidOperationException("operation not allowed: engine status is " + this._status.name() + ". Excepted: " + EngineStatus.AVAILABLE.name());
        }

        this._engineRunnerExecutor = Executors.newSingleThreadExecutor();
        this._netScanningTasksExecutor = Executors.newFixedThreadPool(this._parallelism);
        this._arpUpdateExecutor = Executors.newSingleThreadScheduledExecutor();

        this._engineRunnerExecutor.submit(this::scanNetworkTask);
        this._arpUpdateExecutor.scheduleAtFixedRate(this::updateArpTask, 100, _arpInterval, TimeUnit.MILLISECONDS);

        this._logger.info("engine activity started");
        this._engineStartedEvent.dispatch(new StartupEventArgs());
    }

    public void stop() {
        this._logger.debug("stopping engine activity");

        if (this._status != EngineStatus.RUNNING) {
            throw new InvalidOperationException("operation not allowed: engine status is " + this._status.name() + ". Excepted: " + EngineStatus.RUNNING.name());
        }

        ExecutionUtils.gracefulShutdown(_engineRunnerExecutor);
        ExecutionUtils.gracefulShutdown(_arpUpdateExecutor);
        this._engineShutdownEvent.dispatch(new ShutdownEventArgs(this._status));
        this._status = EngineStatus.AVAILABLE;
    }

    public IEntitiesHolder<LanEntity> getEntitiesHolder() {
        return _entitiesHolder;
    }

    private void setParallelism(int parallelism) throws IllegalArgumentException {
        if (parallelism <= 0) {
            throw new IllegalArgumentException("number of threads must be greater than zero");
        } else if (parallelism > MAX_THREADS) {
            throw new IllegalArgumentException("number of threads must be less or equal to " + MAX_THREADS);
        } else if (_status == EngineStatus.RUNNING) {
            throw new InvalidOperationException("cannot change 'parallelism' property when the engine is running");
        } else {
            this._parallelism = parallelism;
        }
    }

    private void setArpInterval(int arpRefreshInterval) throws IllegalArgumentException {
        if (arpRefreshInterval <= MIN_ARP_INTERVAL) {
            throw new IllegalArgumentException("arp refresh interval must be greater than " + MIN_ARP_INTERVAL + " ms");
        } else {
            this._arpInterval = arpRefreshInterval;
        }
    }

    private void scanNetworkTask() {
        _logger.debug("starting network scanning...");

        this._status = EngineStatus.RUNNING;

        while (!Thread.currentThread().isInterrupted()) {
            while (_contractor.hasAvailableWorkers() && _tasksSupplier.hasAvailableTasks()) {
                ScanningTask ipToScan = this._tasksSupplier.pullTask();
                Runnable worker = this._contractor.assign(ipToScan);

                this._netScanningTasksExecutor.submit(worker);
            }
        }

        _logger.info("network scanning thread interrupted. Shutting down workers...");
        ExecutionUtils.gracefulShutdown(_netScanningTasksExecutor);
        _logger.info("workers are off");
    }

    private void updateArpTask() {
        try {
            _arpTable.refresh();
            _logger.debug("updating arp table cache");
        } catch (IOException e) {
            _logger.error("error refreshing arp table", e);
        }
    }

    // NetScannerListener internal class
    private class NetScannerListener implements IScannerListener {
        @Override
        public void onScanStarted(Runnable source, ScanningTask task) {
            _logger.info("scanning ip: " + task.getIP());
        }

        @Override
        public void onScanCompleted(Runnable source, ScanningTask task, ScanningTaskResult result) {
            _tasksSupplier.pushTask(task);

            LanEntity.LanEntityBuilder entityBuilder = new LanEntity.LanEntityBuilder(result.getIP(), result.getHostname());

            if (result.isAvailable()) {
                _arpTable.onceDetected(result.getIP(), (mac) -> {
                    entityBuilder.setMAC(mac);
                    String vendor = null;
                    try {
                        vendor = _vendorsProvider.getVendorByMac(mac).get();
                    } catch (Exception e) {
                       _logger.error("error on vendors providing for " + mac, e);
                    }
                    entityBuilder.setVendor(vendor == null ? "NOT_FOUND" : vendor);

                    LanEntity entity = entityBuilder.build();
                    _entitiesHolder.add(entity);

                    _logger.info(String.format(
                        "scan completed '%s' [host: '%s', mac: '%s', vendor: '%s']. started at %s, took %d ms.",
                        entity.getIP(), entity.getHostname(), entity.getMAC(), entity.getVendor(),
                        DateFormatUtils.format(result.getTimeStarted(), "HH:mm:ss"),
                        result.getDuration()));
                });
            } else {
                LanEntity presentEntity = _entitiesHolder.getEntityById(result.getIP());
                if (presentEntity != null) {
                    entityBuilder
                            .setMAC(presentEntity.getMAC())
                            .setVendor(presentEntity.getVendor());
                    _entitiesHolder.remove(entityBuilder.build());
                }

                _logger.info(String.format(
                        "scan completed '%s' ['%s']: UNREACHABLE. started at %s, took %d ms.",
                        result.getIP(), result.getHostname(),
                        DateFormatUtils.format(result.getTimeStarted(), "HH:mm:ss"),
                        result.getDuration()));
            }
        }

        @Override
        public void onScanFailed(Runnable source, ScanningTask task, Exception error) {
            _tasksSupplier.pushTask(task);

            if (error != null) {
                _logger.error("Ip scan failed: " + task.getIP(), error);
            } else {
                _logger.error("Ip scan failed: " + task.getIP() + ". Unknown exception / error.");
            }
        }
    }
}
