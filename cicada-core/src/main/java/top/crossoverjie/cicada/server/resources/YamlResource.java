package top.crossoverjie.cicada.server.resources;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.reader.UnicodeReader;
import top.crossoverjie.cicada.server.exception.GenericException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;

/**
 * @Author liwenguang
 * @Date 2018/9/6 下午10:47
 * @Description
 * 参考：org.springframework.beans.factory.config.YamlProcessor
 */
public class YamlResource<T> implements Resource<T>{

    private static Yaml yaml = new Yaml();

    private static Resource yamlResource;

    public static Resource getInstance() {
        if (yamlResource == null) {
            yamlResource = new YamlResource();
        }
        return yamlResource;
    }

    @Override
    public Properties file2Properties(String url) {
        if (!url.endsWith(".yml") && !url.endsWith(".yaml")) {
            throw new GenericException(new FileNotFoundException(), "文件没找着");
        }
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(url);
        Reader reader = new UnicodeReader(inputStream);
        Properties properties = new Properties();
        for (Object object :  yaml.loadAll(reader)) {
            properties.putAll(getFlattenedMap(asMap(object)));
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    protected final Map<String, Object> getFlattenedMap(Map<String, Object> source) {
        Map<String, Object> result = new LinkedHashMap<>();
        buildFlattenedMap(result, source, null);
        return result;
    }

    private Map<String, Object> asMap(Object object) {
        // YAML can have numbers as keys
        Map<String, Object> result = new LinkedHashMap<>();
        if (!(object instanceof Map)) {
            // A document can be a text literal
            result.put("document", object);
            return result;
        }

        Map<Object, Object> map = (Map<Object, Object>) object;
        map.forEach((key, value) -> {
            if (value instanceof Map) {
                value = asMap(value);
            }
            if (key instanceof CharSequence) {
                result.put(key.toString(), value);
            }
            else {
                // It has to be a map key in this case
                result.put("[" + key.toString() + "]", value);
            }
        });
        return result;
    }

    private void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, String path) {
        source.forEach((key, value) -> {
            if (path != null && !path.isEmpty()) {
                if (key.startsWith("[")) {
                    key = path + key;
                }
                else {
                    key = path + '.' + key;
                }
            }
            if (value instanceof String) {
                result.put(key, value);
            }
            else if (value instanceof Map) {
                // Need a compound key
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) value;
                buildFlattenedMap(result, map, key);
            }
            else if (value instanceof Collection) {
                // Need a compound key
                @SuppressWarnings("unchecked")
                Collection<Object> collection = (Collection<Object>) value;
                if (collection.isEmpty()) {
                    result.put(key, "");
                }
                else {
                    int count = 0;
                    for (Object object : collection) {
                        buildFlattenedMap(result, Collections.singletonMap(
                                "[" + (count++) + "]", object), key);
                    }
                }
            }
            else {
                result.put(key, (value != null ? value : ""));
            }
        });
    }
}