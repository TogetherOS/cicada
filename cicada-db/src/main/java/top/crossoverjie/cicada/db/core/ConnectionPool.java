package top.crossoverjie.cicada.db.core;

import java.sql.Connection;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020-02-28 00:42
 * @since JDK 1.8
 */
public class ConnectionPool implements ConnectionFactory {
    @Override
    public Connection getConnection(SqlSession sqlSession) {
        return null;
    }
}
