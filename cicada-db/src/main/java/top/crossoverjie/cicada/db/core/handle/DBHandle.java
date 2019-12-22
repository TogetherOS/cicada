package top.crossoverjie.cicada.db.core.handle;

import top.crossoverjie.cicada.db.model.Model;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-12-03 23:35
 * @since JDK 1.8
 */
public interface DBHandle {

    /**
     * update model
     * @param clazz
     * @param filed
     * @return
     */
    int update(Class<? extends Model> clazz, int filed) ;

    int update(Object obj) ;

}
