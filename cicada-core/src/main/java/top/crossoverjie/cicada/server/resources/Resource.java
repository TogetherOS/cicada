package top.crossoverjie.cicada.server.resources;

import java.util.Properties;

public interface Resource<T> {
    Properties file2Properties(String url);
}
