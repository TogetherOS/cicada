package top.crossoverjie.cicada.server.intercept;

import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.util.ClassScanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/21 18:42
 * @since JDK 1.8
 */
public class InterceptProcess {

    private InterceptProcess(){}

    private volatile static InterceptProcess process ;

    private static List<CicadaInterceptor> interceptors ;

    /**
     * get single Instance
     * @return
     */
    public static InterceptProcess getInstance(){
        if (process == null){
            synchronized (InterceptProcess.class){
                if (process == null){
                    process = new InterceptProcess() ;
                }
            }
        }
        return process ;
    }


    public void loadInterceptors(AppConfig appConfig) throws Exception {

        if (interceptors != null){
            return;
        }else {
            interceptors = new ArrayList<>(10) ;
            Map<Integer, Class<?>> cicadaInterceptor = ClassScanner.getCicadaInterceptor(appConfig.getRootPackageName());
            for (Map.Entry<Integer, Class<?>> classEntry : cicadaInterceptor.entrySet()) {
                Class<?> interceptorClass = classEntry.getValue();
                CicadaInterceptor interceptor = (CicadaInterceptor) interceptorClass.newInstance();
                interceptor.setOrder(classEntry.getKey());
                interceptors.add(interceptor);
            }
            Collections.sort(interceptors,new OrderComparator());
        }
    }


    /**
     * execute before
     * @param param
     * @throws Exception
     */
    public boolean processBefore(Param param) throws Exception {
        for (CicadaInterceptor interceptor : interceptors) {
            boolean access = interceptor.before(CicadaContext.getContext(), param);
            if (!access){
                return access ;
            }
        }
        return true;
    }

    /**
     * execute after
     * @param param
     * @throws Exception
     */
    public void processAfter(Param param) throws Exception{
        for (CicadaInterceptor interceptor : interceptors) {
            interceptor.after(CicadaContext.getContext(),param) ;
        }
    }
}
