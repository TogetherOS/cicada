package top.crossoverjie.cicada.base.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/31 19:35
 * @since JDK 1.8
 */
public class LoggerBuilder {


    /**
     * get static Logger
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class clazz){
        return LoggerFactory.getLogger(clazz);
    }
}
