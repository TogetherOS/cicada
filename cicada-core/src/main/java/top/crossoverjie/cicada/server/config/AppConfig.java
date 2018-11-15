package top.crossoverjie.cicada.server.config;

import io.netty.handler.codec.http.QueryStringDecoder;
import top.crossoverjie.cicada.server.enums.StatusEnum;
import top.crossoverjie.cicada.server.exception.CicadaException;
import top.crossoverjie.cicada.server.util.PathUtil;

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

    public void setRootPackageName(Class<?> clazz) {
        if (clazz.getPackage() == null) {
            throw new CicadaException(StatusEnum.NULL_PACKAGE, "[" + clazz.getName() + ".java]:" + StatusEnum.NULL_PACKAGE.getMessage());
        }
        this.rootPackageName = clazz.getPackage().getName();
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


    /**
     * check Root Path
     *
     * @param uri
     * @param queryStringDecoder
     * @return
     */
    public void checkRootPath(String uri, QueryStringDecoder queryStringDecoder) {
        if (!PathUtil.getRootPath(queryStringDecoder.path()).equals(this.getRootPath())) {
            throw new CicadaException(StatusEnum.REQUEST_ERROR, uri);
        }
    }
}
