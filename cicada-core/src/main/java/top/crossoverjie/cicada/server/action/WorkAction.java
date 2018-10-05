package top.crossoverjie.cicada.server.action;

import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.context.CicadaContext;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/31 15:58
 * @since JDK 1.8
 */
public interface WorkAction {

    /**
     * abstract execute method
     * @param context current context
     * @param param request params
     * @throws Exception throw exception
     */
    void execute(CicadaContext context ,Param param) throws Exception;
}
