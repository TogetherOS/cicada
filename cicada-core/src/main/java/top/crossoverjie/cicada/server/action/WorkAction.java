package top.crossoverjie.cicada.server.action;

import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.action.req.WorkReq;

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
     * @param workReq
     * @return
     */
    WorkRes execute(WorkReq workReq) ;
}
