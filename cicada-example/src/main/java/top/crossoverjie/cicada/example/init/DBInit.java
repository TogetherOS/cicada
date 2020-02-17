package top.crossoverjie.cicada.example.init;

import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.cicada.db.core.SqlSession;
import top.crossoverjie.cicada.server.bootstrap.InitializeHandle;
import top.crossoverjie.cicada.server.configuration.ApplicationConfiguration;
import top.crossoverjie.cicada.server.configuration.ConfigurationHolder;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020-02-18 01:40
 * @since JDK 1.8
 */
@Slf4j
public class DBInit extends InitializeHandle {

    @Override
    public void handle() throws Exception {
        ApplicationConfiguration configuration = (ApplicationConfiguration) ConfigurationHolder.getConfiguration(ApplicationConfiguration.class);
        String username = configuration.get("db.username");
        String pwd = configuration.get("db.pwd");
        String url = configuration.get("db.url");
        SqlSession.init(username, pwd, url);
        log.info("db init success!!");
    }
}
