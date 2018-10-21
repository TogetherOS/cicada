package top.crossoverjie.cicada.example.intercept;

import org.slf4j.Logger;
import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.annotation.Interceptor;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.intercept.CicadaInterceptor;
import top.crossoverjie.cicada.server.util.LoggerBuilder;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/2 15:21
 * @since JDK 1.8
 */
@Interceptor(order = 1)
public class ExecuteTimeInterceptor extends CicadaInterceptor {

    private static final Logger LOGGER = LoggerBuilder.getLogger(ExecuteTimeInterceptor.class);

    private Long start;

    private Long end;

    @Override
    public boolean before(CicadaContext context,Param param) {
        start = System.currentTimeMillis();
        LOGGER.info("拦截请求");
        context.text("拦截请求");
        return false;
    }

    @Override
    public void after(CicadaContext context,Param param) {
        end = System.currentTimeMillis();

        LOGGER.info("cast [{}] times", end - start);
    }
}
