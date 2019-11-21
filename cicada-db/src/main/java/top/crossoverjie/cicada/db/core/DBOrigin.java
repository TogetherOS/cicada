package top.crossoverjie.cicada.db.core;

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

    public static DBOrigin getInstance() {
        return origin ;
    }

    public static void init(String userName, String pwd, String url) {
        origin = new DBOrigin(userName, pwd, url);
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


}
