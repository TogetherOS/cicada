package top.crossoverjie.cicada.example.listener;

import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.cicada.db.listener.DataChangeListener;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020-03-29 01:33
 * @since JDK 1.8
 */
@Slf4j
public class UserSaveListener implements DataChangeListener {
    @Override
    public void listener(Object obj) {
        log.info("user save data={}", obj.toString());
    }
}
