package top.crossoverjie.cicada.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-19 17:34
 * @since JDK 1.8
 */
public class DBConnection {
    private static final String URL="jdbc:mysql://localhost:3306/ssm?charset=utf8mb4";
    private static final String NAME="root";
    private static final String PASSWORD="root";

    public static void main(String[] args) throws Exception{

        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");

        //2.获得数据库的连接
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        //3.通过数据库的连接操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        //选择import java.sql.ResultSet;
        ResultSet rs = stmt.executeQuery("select * from user");
        //如果对象中有数据，就会循环打印出来
        while(rs.next()){
            System.out.println(rs.getInt("id")+","+rs.getString("name"));
        }
        rs.close();
        stmt.close();
    }
}
