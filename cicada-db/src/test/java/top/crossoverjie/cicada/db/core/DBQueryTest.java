package top.crossoverjie.cicada.db.core;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.crossoverjie.cicada.db.reflect.User;
import top.crossoverjie.cicada.db.sql.EqualToCondition;

import java.util.List;

@Slf4j
public class DBQueryTest {

    @Test
    public void query(){
        DBOrigin.init("root","root","jdbc:mysql://localhost:3306/ssm?charset=utf8mb4");
        List<User> all = new DBQuery<User>().query(User.class).all();
        for (User user : all) {
            log.info(user.toString());
        }
    }
    @Test
    public void query2(){
        DBOrigin.init("root","root","jdbc:mysql://localhost:3306/ssm?charset=utf8mb4");
        List<User> all = new DBQuery<User>().query(User.class).addCondition(new EqualToCondition("password","abc123"))
                .addCondition(new EqualToCondition("id",1)).all();
        for (User user : all) {
            log.info(user.toString());
        }
    }

}