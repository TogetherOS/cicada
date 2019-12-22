package top.crossoverjie.cicada.db.reflect;

import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.cicada.db.model.Model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-20 01:01
 * @since JDK 1.8
 */
@Slf4j
public class Instance {

    public static <T extends Model> T transfer(Class<T> target, String filedName, Object value) {
        if (filedName == null || value == null){
            throw new RuntimeException("argument is null!") ;
        }
        T obj = null;
        try {
            obj = target.newInstance();
            filedName = methodName(filedName) ;
            Method method = target.getMethod("set" + filedName ,value.getClass());
            method.invoke(obj, value);
            return obj;
        } catch (Exception e) {
            log.error("exception",e);
        }

        return obj;
    }


    public static <T extends Model> T transfer(Class<T> target, Map<String,Object> params){
        T obj = null ;
        try {
            obj = target.newInstance() ;
            for (Map.Entry<String, Object> param : params.entrySet()) {
                String filedName = methodName(param.getKey()) ;
                Method method = target.getMethod("set" + filedName ,param.getValue().getClass());
                method.invoke(obj,param.getValue()) ;
            }
        }catch (Exception e){
            log.error("exception",e);
        }
        return obj;
    }


    private static String methodName(String filedName){
        char c = filedName.charAt(0);
        filedName = filedName.replace(c,Character.toUpperCase(c)) ;
        return filedName ;
    }


    /**
     * @param obj
     * @param field
     * @return
     */
    public static Object getFiledValue(Object obj, Field field){
        try {
            Method method = obj.getClass().getDeclaredMethod("get" + methodName(field.getName()));
            return method.invoke(obj) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
}
