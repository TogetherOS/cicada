package top.crossoverjie.cicada.db.core;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-19 22:57
 * @since JDK 1.8
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DBOrigin {

    private Connection connection;

    private String userName;

    private String pwd;

    private String url;

    private static DBOrigin origin;

    private static DbSchema schema;

    public static DBOrigin getInstance() {
        return origin ;
    }

    public static void init(String userName, String pwd, String url) {
        origin = new DBOrigin(userName, pwd, url);
        String database = getDataBaseName(url);
        schema = new DbSpec().addSchema(database);
    }


    private DBOrigin(String userName, String pwd, String url) {
        this.userName = userName;
        this.pwd = pwd;
        this.url = url;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, userName, pwd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
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
