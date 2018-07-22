package whosHome.common.events;

import java.util.ArrayList;
import java.util.List;

public class Event<T> {
    private List<IEventListener<T>> _pernamentListeners;
    private List<IEventListener<T>> _tempListeners;

    public Event() {
        _pernamentListeners = new ArrayList<>();
        _tempListeners = new ArrayList<>();
    }

    public void listen(IEventListener<T> callback) {
        this._pernamentListeners.add(callback);
    }

    public void once(IEventListener<T> callback) {
        this._tempListeners.add(callback);
    }

    public boolean removeListener(IEventListener<T> callback) {
        return this._pernamentListeners.remove(callback);
    }

    public void dispatch(T eventArg) {
        this.dispatch(_pernamentListeners, eventArg);
        this.dispatch(_tempListeners, eventArg);

        this._tempListeners.clear();
    }

    private void dispatch(List<IEventListener<T>> listenersGroup, T eventArg) {
        for (IEventListener<T> listener : listenersGroup) {
            listener.update(eventArg);;
        }
    }
}
