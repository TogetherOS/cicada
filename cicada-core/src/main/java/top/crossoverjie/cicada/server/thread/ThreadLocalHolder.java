package top.crossoverjie.cicada.server.thread;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/10 19:50
 * @since JDK 1.8
 */
public class ThreadLocalHolder {

    private static final ThreadLocal<Long> LOCAL_TIME= new ThreadLocal() ;

    /**
     * Set time
     * @param time
     */
    public static void setLocalTime(long time){
        LOCAL_TIME.set(time) ;
    }

    public static Long getLocalTime(){
        return LOCAL_TIME.get() ;
    }

    /**
     * Clear time
     */
    public static void clearTime(){
        LOCAL_TIME.remove();
    }
}
