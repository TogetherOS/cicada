package top.crossoverjie.cicada.bean.ioc;

import org.slf4j.Logger;
import top.crossoverjie.cicada.base.bean.CicadaBeanFactory;
import top.crossoverjie.cicada.base.log.LoggerBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/14 01:14
 * @since JDK 1.8
 */
public class CicadaIoc implements CicadaBeanFactory {

    private static final Logger LOGGER = LoggerBuilder.getLogger(CicadaIoc.class) ;

    private static Map<String,Object> beans = new HashMap<>(16) ;

    @Override
    public void register(Object object) {
        beans.put(object.getClass().getName(),object) ;
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz.getName());
    }

    @Override
    public void releaseBean() {
        beans = null ;
        LOGGER.info("release all bean success.");
    }
}
