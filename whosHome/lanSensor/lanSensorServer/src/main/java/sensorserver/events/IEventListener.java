package sensorserver.events;

public interface IEventListener<T> {
    void update(T eventArg);
}
