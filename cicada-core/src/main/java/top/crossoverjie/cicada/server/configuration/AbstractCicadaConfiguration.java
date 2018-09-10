package top.crossoverjie.cicada.server.configuration;

import java.util.Properties;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/8 16:28
 * @since JDK 1.8
 */
public abstract class AbstractCicadaConfiguration {

    /**
     * file name
     */
    private String propertiesName;

    private Properties properties;


    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public String getPropertiesName() {
        return propertiesName;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String get(String key) {
        return properties.get(key) == null ? null : properties.get(key).toString();
    }

    @Override
    public String toString() {
        return "AbstractCicadaConfiguration{" +
                "propertiesName='" + propertiesName + '\'' +
                ", properties=" + properties +
                '}';
    }
}
