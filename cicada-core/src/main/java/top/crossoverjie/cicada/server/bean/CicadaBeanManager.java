package top.crossoverjie.cicada.server.bean;

import top.crossoverjie.cicada.base.bean.CicadaBeanFactory;
import top.crossoverjie.cicada.server.reflect.ClassScanner;
import top.crossoverjie.cicada.server.route.RouteProcess;

import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/14 01:41
 * @since JDK 1.8
 */
public class CicadaBeanManager {


    private CicadaBeanManager(){
    }

    private static volatile CicadaBeanManager cicadaBeanManager;

    private static CicadaBeanFactory cicadaBeanFactory ;

    public static CicadaBeanManager getInstance() {
        if (cicadaBeanManager == null) {
            synchronized (RouteProcess.class) {
                if (cicadaBeanManager == null) {
                    cicadaBeanManager = new CicadaBeanManager();
                }
            }
        }
        return cicadaBeanManager;
    }


    public void init(String packageName) throws Exception {
        Map<String, Class<?>> cicadaAction = ClassScanner.getCicadaAction(packageName);

        Class<?> bean = ClassScanner.getCustomBean();
        cicadaBeanFactory = (CicadaBeanFactory) bean.newInstance() ;

        for (Map.Entry<String, Class<?>> classEntry : cicadaAction.entrySet()) {
            Object instance = classEntry.getValue().newInstance();
            cicadaBeanFactory.register(instance) ;
        }

    }


    public Object getBean(String name) throws Exception {
        return cicadaBeanFactory.getBean(name) ;
    }
}
