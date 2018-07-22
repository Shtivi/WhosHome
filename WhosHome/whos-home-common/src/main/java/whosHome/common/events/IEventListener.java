package whosHome.common.events;

public interface IEventListener<T> {
    void update(T eventArg);
}
