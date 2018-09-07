package top.crossoverjie.cicada.server.util;

import top.crossoverjie.cicada.server.exception.CicadaException;

import java.util.Properties;

/**
 * 属性文件获取类 liwenguang 2018-09-07 13:17:49
 */
public class PropertiesUtil {

    public static String getString(Properties properties, Object key) {
        String string = null;
        Object object = properties.get(key);
        if (object != null) {
            string = (String) properties.get(key);
        }
        return string;
    }

    public static Integer getInteger(Properties properties, Object key) {
        Integer integer = null;
        Object object = properties.get(key);
        if (object != null) {
            integer = (Integer) properties.get(key);
        }
        return integer;
    }

    public static String getString(Properties properties, Object key, String defaultVal) {
        throw new CicadaException("没实现呢");
    }

}
