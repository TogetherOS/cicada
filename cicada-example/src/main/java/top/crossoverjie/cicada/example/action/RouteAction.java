package top.crossoverjie.cicada.example.action;

import org.slf4j.Logger;
import top.crossoverjie.cicada.example.req.DemoReq;
import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.annotation.CicadaRoute;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.util.LoggerBuilder;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/13 01:12
 * @since JDK 1.8
 */
@CicadaAction("routeAction")
public class RouteAction {

    private static final Logger LOGGER = LoggerBuilder.getLogger(RouteAction.class);


    @CicadaRoute("getUser")
    public void getUser(){
        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("hello");
        CicadaContext.getContext().json(reqWorkRes) ;
    }

    private void test(){
        LOGGER.info("test");
    }
}
