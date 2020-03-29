package top.crossoverjie.cicada.db.core;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Connection;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-19 22:57
 * @since JDK 1.8
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlSession {

    private Connection connection;


    @Getter
    private String userName;

    @Getter
    private String pwd;

    @Getter
    private String url;

    private static SqlSession session;

    private static DbSchema schema;

    private static ConnectionFactory connectionFactory ;

    public static SqlSession getInstance() {
        return session;
    }

    public static void init(String userName, String pwd, String url) {
        session = new SqlSession(userName, pwd, url);
        String database = getDataBaseName(url);
        schema = new DbSpec().addSchema(database);

        // FIXME: 2020-02-28 temporary
        connectionFactory = new DefaultConnection() ;
    }


    private SqlSession(String userName, String pwd, String url) {
        this.userName = userName;
        this.pwd = pwd;
        this.url = url;
    }

    public Connection getConnection() {
        return connectionFactory.getConnection(session) ;
    }

    public DbTable addTable(String tableName){
        return schema.addTable(tableName) ;
    }



    private static String getDataBaseName(String url) {
        int i1 = url.lastIndexOf("/") ;
        int i2 = url.indexOf("?") ;
        return url.substring(i1 +1 , i2);
    }
}
