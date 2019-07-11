package top.crossoverjie.cicada.example.exception;

import org.slf4j.Logger;
import top.crossoverjie.cicada.base.log.LoggerBuilder;
import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.annotation.CicadaBean;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.exception.GlobalHandelException;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-07-11 12:07
 * @since JDK 1.8
 */

@CicadaBean
public class ExceptionHandle implements GlobalHandelException {
    private final static Logger LOGGER = LoggerBuilder.getLogger(ExceptionHandle.class);

    @Override
    public void resolveException(CicadaContext context, Exception e) {
        LOGGER.error("Exception", e);
        WorkRes workRes = new WorkRes();
        workRes.setCode("500");
        workRes.setMessage(e.getClass().getName());
        context.json(workRes);
    }
}
