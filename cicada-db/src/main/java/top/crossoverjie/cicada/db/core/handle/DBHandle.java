package top.crossoverjie.cicada.db.core.handle;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-12-03 23:35
 * @since JDK 1.8
 */
public interface DBHandle {

    /** update model
     * @param obj model of db entity
     * @return
     */
    int update(Object obj) ;

    int insert(Object obj) ;

}
