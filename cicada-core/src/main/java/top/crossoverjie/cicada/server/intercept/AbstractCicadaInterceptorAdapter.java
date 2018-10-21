package top.crossoverjie.cicada.server.intercept;

import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.context.CicadaContext;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/2 15:40
 * @since JDK 1.8
 */
public abstract class AbstractCicadaInterceptorAdapter extends CicadaInterceptor{

    @Override
    public boolean before(CicadaContext context, Param param) {
        return true;
    }

    @Override
    public void after(CicadaContext context,Param param) {

    }
}
