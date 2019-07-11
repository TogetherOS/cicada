package top.crossoverjie.cicada.server.bean;

import top.crossoverjie.cicada.base.bean.CicadaBeanFactory;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/14 01:26
 * @since JDK 1.8
 */
public class CicadaDefaultBean implements CicadaBeanFactory {

    @Override
    public void register(Object object) {

    }

    @Override
    public Object getBean(String name) throws Exception {
        Class<?> aClass = Class.forName(name);
        return aClass.newInstance();
    }

    @Override
    public <T> T getBean(Class<T> clazz) throws Exception {
        return clazz.newInstance();
    }

    @Override
    public void releaseBean() {
    }
}
