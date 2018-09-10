package top.crossoverjie.cicada.server.config;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/1 14:00
 * @since JDK 1.8
 */
public class AppConfig {


    private AppConfig() {
    }


    /**
     * simple singleton Object
     */
    private static AppConfig config;

    public static AppConfig getInstance() {
        if (config == null) {
            config = new AppConfig();
        }
        return config;
    }

    private String rootPackageName;

    private String rootPath;

    private Integer port = 7317;

    public String getRootPackageName() {
        return rootPackageName;
    }

    public void setRootPackageName(String rootPackageName) {
        this.rootPackageName = rootPackageName;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
