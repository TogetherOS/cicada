package top.crossoverjie.cicada.server.exception;

import top.crossoverjie.cicada.server.context.CicadaContext;

/**
 * Function: global exception handle
 *
 * @author crossoverJie
 * Date: 2019-07-10 17:12
 * @since JDK 1.8
 */
public interface GlobalHandelException {

    /**
     * exception handle
     * @param context
     * @param e
     */
    void resolveException(CicadaContext context,Exception e) ;
}
