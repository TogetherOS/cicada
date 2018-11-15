package top.crossoverjie.cicada.bean.ioc;

import top.crossoverjie.cicada.base.bean.CicadaBeanFactory;

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

    private static Map<String,Object> beans = new HashMap<>(16) ;

    @Override
    public void register(Object object) {
        beans.put(object.getClass().getName(),object) ;
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }
}
