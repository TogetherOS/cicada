package top.crossoverjie.cicada.example.exception;

import org.slf4j.Logger;

import top.crossoverjie.cicada.base.log.LoggerBuilder;
import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.annotation.CicadaBean;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.exception.CustomizeHandleException;

/**
 * Function:
 *	customize route exception
 * @author hugui
 * Date: 2019-11-17 12:07
 * @since JDK 1.8
 */

@CicadaBean("RouteExceptionHandle")
public class RouteExceptionHandle implements CustomizeHandleException {
	private static final  Logger LOGGER = LoggerBuilder.getLogger(RouteExceptionHandle.class);

    @Override
    public void resolveException(CicadaContext context, Exception e) {
    	
        // handle your customize error
        LOGGER.error("Exception", e);
        WorkRes workRes = new WorkRes();
        workRes.setCode("500");
        workRes.setMessage("Route Error : " + e.getClass().getName() + "系统运行出现异常");
        context.json(workRes);
    }
}
