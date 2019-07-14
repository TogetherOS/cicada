package top.crossoverjie.cicada.server.bean;

import org.slf4j.Logger;
import top.crossoverjie.cicada.base.bean.CicadaBeanFactory;
import top.crossoverjie.cicada.base.log.LoggerBuilder;
import top.crossoverjie.cicada.server.exception.GlobalHandelException;
import top.crossoverjie.cicada.server.reflect.ClassScanner;

import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/14 01:41
 * @since JDK 1.8
 */
public final class CicadaBeanManager {
    private final static Logger LOGGER = LoggerBuilder.getLogger(CicadaBeanManager.class);

    private CicadaBeanManager(){
    }

    private static volatile CicadaBeanManager cicadaBeanManager;

    private static CicadaBeanFactory cicadaBeanFactory ;

    private GlobalHandelException handelException ;

    public static CicadaBeanManager getInstance() {
        if (cicadaBeanManager == null) {
            synchronized (CicadaBeanManager.class) {
                if (cicadaBeanManager == null) {
                    cicadaBeanManager = new CicadaBeanManager();
                }
            }
        }
        return cicadaBeanManager;
    }

    /**
     * initBean route bean factory
     * @param packageName
     * @throws Exception
     */
    public void initBean(String packageName) throws Exception {
        Map<String, Class<?>> cicadaBean = ClassScanner.getCicadaBean(packageName);

        Class<?> bean = ClassScanner.getBeanFactory();
        cicadaBeanFactory = (CicadaBeanFactory) bean.newInstance() ;

        for (Map.Entry<String, Class<?>> classEntry : cicadaBean.entrySet()) {
            Object instance = classEntry.getValue().newInstance();
            cicadaBeanFactory.register(instance) ;

            //set exception handle
            if (ClassScanner.isInterface(classEntry.getValue(), GlobalHandelException.class)){
                GlobalHandelException exception = (GlobalHandelException) instance;
                CicadaBeanManager.getInstance().exceptionHandle(exception);
            }
        }

    }


    /**
     * get route bean
     * @param name
     * @return
     * @throws Exception
     */
    public Object getBean(String name) {
        try {
            return cicadaBeanFactory.getBean(name) ;
        } catch (Exception e) {
            LOGGER.error("get bean error",e);
        }
        return null ;
    }

    /**
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T getBean(Class<T> clazz) {
        try {
            return cicadaBeanFactory.getBean(clazz) ;
        } catch (Exception e) {
            LOGGER.error("get bean error",e);
        }
        return null ;
    }

    /**
     * release all beans
     */
    public void releaseBean(){
        cicadaBeanFactory.releaseBean();
    }

    public void exceptionHandle(GlobalHandelException ex){
        handelException = ex ;
    }

    public GlobalHandelException exceptionHandle(){
        return handelException ;
    }
}
