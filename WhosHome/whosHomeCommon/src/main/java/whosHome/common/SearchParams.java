package whosHome.common;

import java.util.HashMap;
import java.util.Map;

public class SearchParams {
    private Map<String, Object> _params;

    public SearchParams() {
        _params = new HashMap<>();
    }

    public SearchParams addContext(String paramName, Object value) {
        _params.put(paramName, value);
        return this;
    }

    public SearchParams deleteContext(String paramName) {
        _params.remove(paramName);
        return this;
    }

    public Map<String, Object> asMap() {
        return _params;
    }
}
