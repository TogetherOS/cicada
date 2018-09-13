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

    /**
     * Get time and remove value
     * @return
     */
    public static Long getLocalTime(){
        Long time = LOCAL_TIME.get();
        LOCAL_TIME.remove();
        return time;
    }

}
