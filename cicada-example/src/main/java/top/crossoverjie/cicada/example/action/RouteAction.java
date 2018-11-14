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
    public void getUser(DemoReq req){

        LOGGER.info(req.toString());
        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("hello =" + req.getName());
        CicadaContext.getContext().json(reqWorkRes) ;
    }

    @CicadaRoute("getInfo")
    public void getInfo(DemoReq req){

        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("getInfo =" + req.toString());
        CicadaContext.getContext().json(reqWorkRes) ;
    }

    @CicadaRoute("getReq")
    public void getReq(CicadaContext context,DemoReq req){

        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("getReq =" + req.toString());
        context.json(reqWorkRes) ;
    }



}
