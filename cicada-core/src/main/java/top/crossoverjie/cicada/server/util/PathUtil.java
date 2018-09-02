package top.crossoverjie.cicada.server.util;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/1 17:46
 * @since JDK 1.8
 */
public class PathUtil {


    /**
     * Get Root Path
     * /cicada-example/demoAction
     * @param path
     * @return cicada-example
     */
    public static String getRootPath(String path) {
        return "/" + path.split("/")[1];
    }

    /**
     * Get Action Path
     * /cicada-example/demoAction
     * @param path
     * @return demoAction
     */
    public static String getActionPath(String path) {
        return path.split("/")[2];
    }


}
