package top.crossoverjie.cicada.example.action;

import org.junit.Test;
import org.slf4j.Logger;
import top.crossoverjie.cicada.server.action.WorkAction;
import top.crossoverjie.cicada.server.annotation.CicadaRoute;
import top.crossoverjie.cicada.server.util.LoggerBuilder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RouteActionTest {

    private static final Logger LOGGER = LoggerBuilder.getLogger(RouteAction.class);

    @Test
    public void reflect() throws Exception {

        Map<Class<?>,Method> routes = new HashMap<>() ;

        Class<?> aClass = Class.forName("top.crossoverjie.cicada.example.action.RouteAction");

        Method[] declaredMethods = aClass.getMethods();

        for (Method method : declaredMethods) {


            CicadaRoute annotation = method.getAnnotation(CicadaRoute.class) ;
            if (annotation == null){
                continue;
            }

            routes.put(aClass,method) ;
        }

        LOGGER.info(routes.toString());

    }

    @Test
    public void reflect2() throws Exception{
        Class<?> aClass = Class.forName("top.crossoverjie.cicada.example.action.DemoAction");
        String name = aClass.getName();
        Class<?>[] interfaces = aClass.getInterfaces() ;
        LOGGER.info((interfaces[0].getName() == WorkAction.class.getName()) + "");
    }

    @Test
    public void reflect3(){
        try {
            Class<?> aClass = Class.forName("top.crossoverjie.cicada.bean.ioc.CicadaIoc");
            LOGGER.info(aClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void costTest(){
        Map<Integer,Integer> hashmap = new HashMap<>(16);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            hashmap.put(i,i) ;
        }
        long end = System.currentTimeMillis();
        LOGGER.info("hashmap cost time=[{}] size=[{}]",(end -start),hashmap.size());

        hashmap=null;


        Map<Integer,Integer> concurrentHashMap = new ConcurrentHashMap<>(16);
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            concurrentHashMap.put(i,i) ;
        }
        end = System.currentTimeMillis();
        LOGGER.info("hashmap cost time=[{}] size=[{}]",(end -start),concurrentHashMap.size());
    }
}