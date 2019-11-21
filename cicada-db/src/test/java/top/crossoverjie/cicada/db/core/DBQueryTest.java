package top.crossoverjie.cicada.db.core;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.crossoverjie.cicada.db.reflect.User;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class DBQueryTest {

    @Test
    public void query(){
        DBOrigin.init("root","root","jdbc:mysql://localhost:3306/ssm?charset=utf8mb4");
        DBOrigin origin = DBOrigin.getInstance();

        List<User> all = new DBQuery<User>().query(User.class).all();
        for (User user : all) {
            log.info(user.toString());
        }
    }

}