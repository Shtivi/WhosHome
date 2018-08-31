package sensorserver.engine.workers;

import sensorserver.exceptions.InvalidOperationException;

public interface IContractor<T> {
    Runnable assign(T taskParameter) throws InvalidOperationException;
    boolean hasAvailableWorkers();
}
