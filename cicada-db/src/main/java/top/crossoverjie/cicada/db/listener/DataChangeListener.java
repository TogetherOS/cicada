package top.crossoverjie.cicada.db.listener;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-12-23 02:25
 * @since JDK 1.8
 */
public interface DataChangeListener {

    /**
     * When db changed, we will callback this method to execute custom business.
     * Warning: be careful blocking thread.
     * @param obj Model of db declare
     */
    void listener(Object obj) ;
}
