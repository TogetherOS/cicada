package top.crossoverjie.cicada.server.thread;

import top.crossoverjie.cicada.server.context.CicadaContext;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/10 19:50
 * @since JDK 1.8
 */
public class ThreadLocalHolder {

    private static final ThreadLocal<Long> LOCAL_TIME= new ThreadLocal() ;

    private static final ThreadLocal<CicadaContext> CICADA_CONTEXT= new ThreadLocal() ;


    /**
     * set cicada context
     * @param context current context
     */
    public static void setCicadaContext(CicadaContext context){
        CICADA_CONTEXT.set(context) ;
    }

    /**
     * remove cicada context
     */
    public static void removeCicadaContext(){
        CICADA_CONTEXT.remove();
    }

    /**
     * @return get cicada context
     */
    public static CicadaContext getCicadaContext(){
        return CICADA_CONTEXT.get() ;
    }

    /**
     * Set time
     * @param time current time
     */
    public static void setLocalTime(long time){
        LOCAL_TIME.set(time) ;
    }

    /**
     * Get time and remove value
     * @return get local time
     */
    public static Long getLocalTime(){
        Long time = LOCAL_TIME.get();
        LOCAL_TIME.remove();
        return time;
    }

}
