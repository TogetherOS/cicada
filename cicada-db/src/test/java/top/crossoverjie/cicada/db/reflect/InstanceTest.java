package top.crossoverjie.cicada.db.reflect;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.crossoverjie.cicada.db.annotation.FieldName;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class InstanceTest {

    @Test
    public void transfer() {
        User user = Instance.transfer(User.class, "id", 123);
        log.info("user={}", user.toString());
    }

    @Test
    public void transfers(){
        Map<String,Object> fields = new HashMap<>() ;
        fields.put("id" ,100) ;
        fields.put("name", "张三") ;
        User user = Instance.transfer(User.class, fields);
        log.info("user={}", user.toString());
    }

    @Test
    public void filed(){
        Field[] fields = User.class.getDeclaredFields();
        for (Field field : fields) {
            FieldName annotation = field.getAnnotation(FieldName.class);
            String fieldName = "" ;
            if (annotation != null){
                fieldName = annotation.value() ;
            }

            log.info(field.getName() + " fieldName="+fieldName );
        }
    }
}