package sensorserver.engine.tasks;

import sensorserver.exceptions.InvalidOperationException;

public interface ITasksSupplier<T> {
    boolean hasAvailableTasks() throws InvalidOperationException;
    T pullTask();
    void pushTask(T task);
}
