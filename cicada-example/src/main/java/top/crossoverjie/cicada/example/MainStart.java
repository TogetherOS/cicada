package top.crossoverjie.cicada.example;

import top.crossoverjie.cicada.server.CicadaServer;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/31 16:27
 * @since JDK 1.8
 */
public class MainStart {

    public static void main(String[] args) throws Exception {
        CicadaServer.start(MainStart.class, args) ;
    }
}
