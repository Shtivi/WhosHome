package whosHome.whosHomeApp.mocks;

import whosHome.common.exceptions.InvalidOperationException;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.common.sensors.client.ISensorListener;
import whosHome.common.sensors.client.SensorConnectionState;
import whosHome.common.sensors.client.commands.SensorCommand;
import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.common.sensors.client.events.StatusChangeEventArgs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SensorConnectionMock implements ISensorConnection<IdentificationDataMock> {
    private SensorConnectionState _status;
    private SensorConnectionMetadata _connectionMetadata;
    private Collection<ISensorListener<IdentificationDataMock>> _listeners;
    private ScheduledExecutorService _scanningEventsDispatcher;

    public SensorConnectionMock(SensorConnectionMetadata connectionMetadata) {
        _listeners = new ArrayList<>();
        _connectionMetadata = connectionMetadata;
        _scanningEventsDispatcher = Executors.newSingleThreadScheduledExecutor();

        this.setStatus(SensorConnectionState.INITIALIZED, "sensor waiting to be connected");
    }

    @Override
    public void connect() {
        if (_status == SensorConnectionState.INITIALIZED || _status == SensorConnectionState.CONNECTING) {
            setStatus(SensorConnectionState.CONNECTING, "attempting to connect");

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    //startEventsGeneration();
                    setStatus(SensorConnectionState.CONNECTED, "connection established");
                }
            }, 10);
        }
    }

    @Override
    public void disconnect() {
        if (_status == SensorConnectionState.CONNECTED) {
            setStatus(SensorConnectionState.CLOSED, "disconnected according to user request");
//            try {
//                stopEventsGeneration();
//                setStatus(SensorConnectionState.CLOSED, "disconnected according to user request");
//            } catch (InterruptedException e) {
//                setStatus(SensorConnectionState.ERROR, e.getMessage());
//            }
        } else {
            throw new InvalidOperationException("status is '" + _status.name() + "', expected: '" + SensorConnectionState.CONNECTED.name() + "'");
        }
    }

    @Override
    public SensorConnectionState getStatus() {
        return this._status;
    }

    @Override
    public void sendCommand(SensorCommand command) {
        if (command.name().equals("dispatchDetection") && command.arg().isPresent()) {
            this._listeners.forEach(listener -> listener.onActivityDetection((ActivityDetectionEventArgs)command.arg().get()));
        }
    }

    @Override
    public boolean listen(ISensorListener<IdentificationDataMock> listener) {
        return this._listeners.add(listener);
    }

    @Override
    public boolean removeListener(ISensorListener<IdentificationDataMock> listener) {
        return this._listeners.remove(listener);
    }

    @Override
    public SensorConnectionMetadata getConnectionMetadata() {
        return this._connectionMetadata;
    }

    protected void setStatus(SensorConnectionState newStatus, String reason) {
        StatusChangeEventArgs args = new StatusChangeEventArgs(_status, newStatus, reason, getConnectionMetadata());
        _status = newStatus;
        _listeners.forEach(listener -> listener.onStatusChange(args));
    }

    protected void startEventsGeneration() {
        _scanningEventsDispatcher.scheduleAtFixedRate(this::dispatchRandomDetection, 500, 500, TimeUnit.MILLISECONDS);
    }

    protected void stopEventsGeneration() throws InterruptedException {
        _scanningEventsDispatcher.shutdown();
        if (!_scanningEventsDispatcher.awaitTermination(500, TimeUnit.MILLISECONDS)) {
            _scanningEventsDispatcher.shutdownNow();
        }
    }

    protected void dispatchRandomDetection() {

    }
}
