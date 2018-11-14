package top.crossoverjie.cicada.example.action;

import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.annotation.CicadaRoute;
import top.crossoverjie.cicada.server.context.CicadaContext;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/5 02:34
 * @since JDK 1.8
 */
@CicadaAction("textAction")
public class TextAction  {


    @CicadaRoute("hello")
    public void hello() throws Exception {
        CicadaContext context = CicadaContext.getContext();

        String url = context.request().getUrl();
        String method = context.request().getMethod();
        context.text("hello world url=" + url + " method=" + method);
    }

    @CicadaRoute("hello2")
    public void hello2(CicadaContext context) throws Exception {

        String url = context.request().getUrl();
        String method = context.request().getMethod();
        context.text("hello world2 url=" + url + " method=" + method);
    }
}
