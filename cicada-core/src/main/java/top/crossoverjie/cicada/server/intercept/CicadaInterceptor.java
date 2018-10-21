package top.crossoverjie.cicada.server.intercept;

import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.context.CicadaContext;

/**
 * Function: common interceptor
 *
 * @author crossoverJie
 *         Date: 2018/9/2 14:39
 * @since JDK 1.8
 */
public abstract class CicadaInterceptor {


    private int order ;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * before
     * @param context
     * @param param
     * @return
     * true if the execution chain should proceed with the next interceptor or the handler itself
     * @throws Exception
     */
    public boolean before(CicadaContext context,Param param) throws Exception{
        return true;
    }


    /**
     * after
     * @param context
     * @param param
     * @throws Exception
     */
    public void after(CicadaContext context,Param param) throws Exception{}
}
