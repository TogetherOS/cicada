package top.crossoverjie.cicada.example.intercept;

import org.slf4j.Logger;
import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.annotation.Interceptor;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.intercept.CicadaInterceptor;
import top.crossoverjie.cicada.server.util.LoggerBuilder;

/**
 * Function: common interceptor
 *
 * @author crossoverJie
 *         Date: 2018/9/2 14:39
 * @since JDK 1.8
 */
@Interceptor
public class LoggerInterceptorAbstract extends CicadaInterceptor {

    private static final Logger LOGGER = LoggerBuilder.getLogger(LoggerInterceptorAbstract.class) ;

    @Override
    public boolean before(CicadaContext context, Param param) throws Exception {
        return super.before(context, param);
    }

    @Override
    public void after(CicadaContext context, Param param) {
        LOGGER.info("logger param=[{}]",param.toString());
    }
}
