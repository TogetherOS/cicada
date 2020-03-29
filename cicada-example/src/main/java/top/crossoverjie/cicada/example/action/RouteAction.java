package top.crossoverjie.cicada.example.action;

import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.cicada.db.core.DBQuery;
import top.crossoverjie.cicada.db.core.handle.DBHandle;
import top.crossoverjie.cicada.db.core.handle.HandleProxy;
import top.crossoverjie.cicada.db.sql.EqualToCondition;
import top.crossoverjie.cicada.example.exception.ExceptionHandle;
import top.crossoverjie.cicada.example.listener.UserSaveListener;
import top.crossoverjie.cicada.example.listener.UserUpdateListener;
import top.crossoverjie.cicada.example.model.User;
import top.crossoverjie.cicada.example.req.DemoReq;
import top.crossoverjie.cicada.server.action.req.Cookie;
import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.annotation.CicadaRoute;
import top.crossoverjie.cicada.server.bean.CicadaBeanManager;
import top.crossoverjie.cicada.server.context.CicadaContext;

import java.util.List;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/11/13 01:12
 * @since JDK 1.8
 */
@CicadaAction("routeAction")
@Slf4j
public class RouteAction {


    @CicadaRoute("getUser")
    public void getUser(DemoReq req) {
        log.info(req.toString());
        WorkRes<List> reqWorkRes = new WorkRes<>();

        List<User> all = new DBQuery<User>().query(User.class)
                .addCondition(new EqualToCondition("password", "abc123"))
                .addCondition(new EqualToCondition("id", req.getId())).all();
        reqWorkRes.setDataBody(all);
        CicadaContext.getContext().json(reqWorkRes);
    }


    @CicadaRoute("saveUser")
    public void saveUser(DemoReq req) {
        DBHandle handle = (DBHandle) new HandleProxy(DBHandle.class).getInstance(new UserSaveListener());
        User user = new User();
        user.setName(req.getName());
        handle.insert(user);
        WorkRes workRes = new WorkRes();
        workRes.setCode("200");
        workRes.setMessage("success");
        CicadaContext.getContext().json(workRes);
    }

    @CicadaRoute("updateUser")
    public void updateUser(DemoReq req) {
        DBHandle handle = (DBHandle) new HandleProxy(DBHandle.class).getInstance(new UserUpdateListener());
        User user = new User();
        user.setId(req.getId());
        user.setName(req.getName());
        int count = handle.update(user);
        WorkRes workRes = new WorkRes();
        workRes.setCode("200");
        workRes.setMessage("success");
        workRes.setDataBody(count);
        CicadaContext.getContext().json(workRes);
    }

    @CicadaRoute("getUserText")
    public void getUserText(DemoReq req) {

        log.info(req.toString());
        WorkRes<DemoReq> reqWorkRes = new WorkRes<>();
        reqWorkRes.setMessage("hello =" + req.getName());
        Cookie cookie = new Cookie();
        cookie.setName("cookie");
        cookie.setValue(req.getName());
        CicadaContext.getResponse().setCookie(cookie);
        CicadaContext.getContext().text(req.toString());
    }

    @CicadaRoute("getInfo")
    public void getInfo(DemoReq req) {

        Cookie cookie = CicadaContext.getRequest().getCookie("cookie");
        WorkRes<DemoReq> reqWorkRes = new WorkRes<>();
        reqWorkRes.setMessage("getInfo =" + req.toString() + "cookie=" + cookie.toString());
        CicadaContext.getContext().json(reqWorkRes);
    }

    @CicadaRoute("getReq")
    public void getReq(CicadaContext context, DemoReq req) {
        WorkRes<DemoReq> reqWorkRes = new WorkRes<>();
        reqWorkRes.setMessage("getReq =" + req.toString());
        context.json(reqWorkRes);
    }


    @CicadaRoute("test")
    public void test(CicadaContext context) {
        ExceptionHandle bean = CicadaBeanManager.getInstance().getBean(ExceptionHandle.class);
        log.info("====" + bean.getClass());
        context.html("<p>12345</p>");
    }

}
