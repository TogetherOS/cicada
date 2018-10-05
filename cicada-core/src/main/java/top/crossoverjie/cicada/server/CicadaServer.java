package top.crossoverjie.cicada.server;

import top.crossoverjie.cicada.server.bootstrap.NettyBootStrap;
import top.crossoverjie.cicada.server.config.CicadaSetting;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/30 12:48
 * @since JDK 1.8
 */
public class CicadaServer {


    /**
     * Start cicada server by path
     * @param clazz
     * @param path
     * @throws Exception
     */
    public static void start(Class<?> clazz,String path) throws Exception {
        CicadaSetting.setting(clazz,path) ;
        NettyBootStrap.startCicada();
    }


    /**
     * Start the service through the port in the configuration file
     * @param clazz
     * @throws Exception
     */
    public static void start(Class<?> clazz) throws Exception {
        start(clazz,null);
    }

}
