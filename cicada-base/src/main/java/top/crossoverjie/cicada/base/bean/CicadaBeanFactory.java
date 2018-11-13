package top.crossoverjie.cicada.base.bean;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/14 01:06
 * @since JDK 1.8
 */
public interface CicadaBeanFactory {

    void register(Object object);

    Object getBean(String name) throws Exception;
}
