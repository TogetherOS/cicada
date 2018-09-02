package top.crossoverjie.cicada.server.action;

import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.action.res.WorkRes;

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
     * @param param
     * @return
     */
    WorkRes execute(Param param) ;
}
