package top.crossoverjie.cicada.example.Intercept;

import org.slf4j.Logger;
import top.crossoverjie.cicada.server.intercept.CicadaInterceptor;
import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.annotation.Interceptor;
import top.crossoverjie.cicada.server.util.LoggerBuilder;

/**
 * Function: common interceptor
 *
 * @author crossoverJie
 *         Date: 2018/9/2 14:39
 * @since JDK 1.8
 */
@Interceptor(value = "loggerInterceptor")
public class LoggerInterceptor implements CicadaInterceptor{

    private static final Logger LOGGER = LoggerBuilder.getLogger(Interceptor.class) ;

    @Override
    public void before(Param param) {
        LOGGER.info("param=[{}]",param.toString());
    }

    @Override
    public void after(Param param) {

    }
}
