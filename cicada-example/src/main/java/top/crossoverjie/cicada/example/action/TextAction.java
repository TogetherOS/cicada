package top.crossoverjie.cicada.example.action;

import top.crossoverjie.cicada.server.action.WorkAction;
import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.context.CicadaContext;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/5 02:34
 * @since JDK 1.8
 */
@CicadaAction("textAction")
public class TextAction implements WorkAction {
    @Override
    public void execute(CicadaContext context, Param param) throws Exception {
        context.text("hello world");
    }
}
