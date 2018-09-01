package top.crossoverjie.cicada.example.action;

import org.slf4j.Logger;
import top.crossoverjie.cicada.example.enums.StatusEnum;
import top.crossoverjie.cicada.example.req.DemoReq;
import top.crossoverjie.cicada.example.res.DemoResVO;
import top.crossoverjie.cicada.server.action.WorkAction;
import top.crossoverjie.cicada.server.action.req.WorkReq;
import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.util.LoggerBuilder;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/31 18:52
 * @since JDK 1.8
 */
public class DemoAction implements WorkAction {


    private static final Logger LOGGER = LoggerBuilder.getLogger(DemoAction.class) ;

    private static AtomicLong index = new AtomicLong() ;

    @Override
    public WorkRes<DemoResVO> execute(WorkReq workReq) {
        DemoReq req = (DemoReq) workReq ;
        LOGGER.info("request=[{}]" , req.toString());

        DemoResVO demoResVO = new DemoResVO() ;
        demoResVO.setIndex(index.incrementAndGet());
        WorkRes<DemoResVO> res = new WorkRes();
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        res.setDataBody(demoResVO) ;
        return res;
    }
}
