package top.crossoverjie.cicada.server.exception;

import top.crossoverjie.cicada.server.context.CicadaContext;

/**
 * Function: global exception handle
 *
 * @author hugui
 * Date: 2019-11-17 17:12
 * @since JDK 1.8
 */

public interface CustomizeHandleException {

	/**
     * exception handle
     * @param context
     * @param e
     */
    void resolveException(CicadaContext context,Exception e) ;
	
}
