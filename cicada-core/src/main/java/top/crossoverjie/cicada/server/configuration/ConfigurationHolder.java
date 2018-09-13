package top.crossoverjie.cicada.server.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/9 20:05
 * @since JDK 1.8
 */
public class ConfigurationHolder {

    private static Map<String,AbstractCicadaConfiguration> config = new HashMap<>(8) ;

    /**
     * Add holder cache
     * @param key
     * @param configuration
     */
    public static void addConfiguration(String key,AbstractCicadaConfiguration configuration){
        config.put(key, configuration);
    }


    /**
     * Get class from cache by class name
     * @param clazz
     * @return
     */
    public static AbstractCicadaConfiguration getConfiguration(Class<? extends AbstractCicadaConfiguration> clazz){
        return config.get(clazz.getName()) ;
    }
}
