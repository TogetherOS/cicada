package top.crossoverjie.server.action;

import top.crossoverjie.server.action.res.WorkRes;
import top.crossoverjie.server.action.req.WorkReq;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/31 15:58
 * @since JDK 1.8
 */
public interface WorkAction<T> {

    WorkRes<T> process(WorkReq workReq) ;
}
