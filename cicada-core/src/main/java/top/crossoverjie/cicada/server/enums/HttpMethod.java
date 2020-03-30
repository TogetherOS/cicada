package top.crossoverjie.cicada.server.enums;

import java.util.HashMap;
import java.util.Map;

public enum HttpMethod {

    POST,

    GET,

    PUT,

    PATCH,

    DELETED;

    private static final Map<String, HttpMethod> mappings = new HashMap<>(16);

    static {
        for (HttpMethod httpMethod : values()) {
            mappings.put(httpMethod.name(), httpMethod);
        }
    }
    
    public static HttpMethod resolve(String method) {
        return (method != null ? mappings.get(method) : null);
    }

}
