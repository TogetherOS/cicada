package top.crossoverjie.cicada.db.core;

import java.sql.Connection;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020-02-28 00:35
 * @since JDK 1.8
 */
public interface ConnectionFactory {

    /**
     * get db connection
     * @param sqlSession
     * @return
     */
    Connection getConnection(SqlSession sqlSession) ;
}
