package top.crossoverjie.cicada.server.action.req;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/5 00:40
 * @since JDK 1.8
 */
public interface CicadaRequest {

    /**
     * get request method
     * @return
     */
    String getMethod() ;

    /**
     * get request url
     * @return
     */
    String getUrl() ;

}
