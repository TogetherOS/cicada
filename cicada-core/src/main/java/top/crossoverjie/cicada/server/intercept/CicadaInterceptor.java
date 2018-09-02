package top.crossoverjie.cicada.server.intercept;

import top.crossoverjie.cicada.server.action.param.Param;

/**
 * Function: common interceptor
 *
 * @author crossoverJie
 *         Date: 2018/9/2 14:39
 * @since JDK 1.8
 */
public interface CicadaInterceptor {

    /**
     * before
     * @param param
     */
    void before(Param param) ;


    /**
     * after
     * @param param
     */
    void after(Param param) ;
}
