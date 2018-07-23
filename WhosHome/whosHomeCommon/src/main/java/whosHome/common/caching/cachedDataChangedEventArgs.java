package whosHome.common.caching;

import whosHome.common.events.AbstractEventArgs;

public class cachedDataChangedEventArgs<T> extends AbstractEventArgs {
    private T _data;

    public cachedDataChangedEventArgs(T data) {
        this._data = data;
    }

    public T getData() {
        return _data;
    }
}
