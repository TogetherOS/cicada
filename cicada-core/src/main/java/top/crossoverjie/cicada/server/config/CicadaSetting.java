package top.crossoverjie.cicada.server.config;

import top.crossoverjie.cicada.server.CicadaServer;
import top.crossoverjie.cicada.server.configuration.AbstractCicadaConfiguration;
import top.crossoverjie.cicada.server.configuration.ApplicationConfiguration;
import top.crossoverjie.cicada.server.configuration.ConfigurationHolder;
import top.crossoverjie.cicada.server.constant.CicadaConstant;
import top.crossoverjie.cicada.server.exception.CicadaException;
import top.crossoverjie.cicada.server.thread.ThreadLocalHolder;
import top.crossoverjie.cicada.server.util.ClassScanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static top.crossoverjie.cicada.server.configuration.ConfigurationHolder.getConfiguration;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/10 20:29
 * @since JDK 1.8
 */
public class CicadaSetting {

    /**
     * @param clazz
     * @param rootPath
     * @throws Exception
     */
    public static void setting(Class<?> clazz, String rootPath) throws Exception {

        //Initialize the application configuration
        initConfiguration(clazz);

        //Set application configuration
        setAppConfig(rootPath);
    }


    /**
     * Set application configuration
     *
     * @param rootPath
     */
    private static void setAppConfig(String rootPath) {
        ApplicationConfiguration applicationConfiguration = (ApplicationConfiguration) getConfiguration(ApplicationConfiguration.class);

        if (rootPath == null) {
            rootPath = applicationConfiguration.get(CicadaConstant.ROOT_PATH);
        }
        String port = applicationConfiguration.get(CicadaConstant.CICADA_PORT);

        if (rootPath == null) {
            throw new CicadaException("No [cicada.root.path] exists ");
        }
        if (port == null) {
            throw new CicadaException("No [cicada.port] exists ");
        }
        AppConfig.getInstance().setRootPath(rootPath);
        AppConfig.getInstance().setPort(Integer.parseInt(port));
    }


    /**
     * Initialize the application configuration
     *
     * @param clazz
     * @throws Exception
     */
    private static void initConfiguration(Class<?> clazz) throws Exception {
        ThreadLocalHolder.setLocalTime(System.currentTimeMillis());
        AppConfig.getInstance().setRootPackageName(clazz.getPackage().getName());

        List<Class<?>> configuration = ClassScanner.getConfiguration(AppConfig.getInstance().getRootPackageName());
        for (Class<?> aClass : configuration) {
            AbstractCicadaConfiguration conf = (AbstractCicadaConfiguration) aClass.newInstance();

            // First read
            InputStream stream ;
            String systemProperty = System.getProperty(conf.getPropertiesName());
            if (systemProperty != null) {
                stream = new FileInputStream(new File(systemProperty));
            } else {
                stream = CicadaServer.class.getClassLoader().getResourceAsStream(conf.getPropertiesName());
            }

            Properties properties = new Properties();
            properties.load(stream);
            conf.setProperties(properties);

            // add configuration cache
            ConfigurationHolder.addConfiguration(aClass.getName(), conf);
        }
    }
}
