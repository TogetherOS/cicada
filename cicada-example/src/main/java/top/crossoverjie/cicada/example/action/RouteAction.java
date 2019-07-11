package top.crossoverjie.cicada.example.action;

import org.slf4j.Logger;
import top.crossoverjie.cicada.base.log.LoggerBuilder;
import top.crossoverjie.cicada.example.exception.ExceptionHandle;
import top.crossoverjie.cicada.example.req.DemoReq;
import top.crossoverjie.cicada.server.action.req.Cookie;
import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.annotation.CicadaRoute;
import top.crossoverjie.cicada.server.bean.CicadaBeanManager;
import top.crossoverjie.cicada.server.context.CicadaContext;

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

    @CicadaRoute("getUserText")
    public void getUserText(DemoReq req){

        LOGGER.info(req.toString());
        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("hello =" + req.getName());
        Cookie cookie = new Cookie() ;
        cookie.setName("cookie");
        cookie.setValue(req.getName());
        CicadaContext.getResponse().setCookie(cookie);
        CicadaContext.getContext().text(req.toString());
    }

    @CicadaRoute("getInfo")
    public void getInfo(DemoReq req){

        Cookie cookie = CicadaContext.getRequest().getCookie("cookie");
        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("getInfo =" + req.toString() + "cookie=" + cookie.toString());
        CicadaContext.getContext().json(reqWorkRes) ;
    }

    @CicadaRoute("getReq")
    public void getReq(CicadaContext context,DemoReq req){
        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("getReq =" + req.toString());
        context.json(reqWorkRes) ;
    }


    @CicadaRoute("test")
    public void test(CicadaContext context){
        ExceptionHandle bean = CicadaBeanManager.getInstance().getBean(ExceptionHandle.class);
        LOGGER.info("====" +bean.getClass());
        context.html("<p>12345</p>");
    }

}
