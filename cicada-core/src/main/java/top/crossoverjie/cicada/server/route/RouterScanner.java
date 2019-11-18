package top.crossoverjie.cicada.server.route;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import io.netty.handler.codec.http.QueryStringDecoder;
import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.annotation.CicadaRoute;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.enums.StatusEnum;
import top.crossoverjie.cicada.server.exception.CicadaException;
import top.crossoverjie.cicada.server.reflect.ClassScanner;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/13 01:48
 * @since JDK 1.8
 */
public class RouterScanner {

    private static Map<String, Method> methodRoutes = null;
    private static Map<String, Class<?>> classRoutes = null;

    private volatile static RouterScanner routerScanner;

    private AppConfig appConfig = AppConfig.getInstance();

    /**
     * get single Instance
     *
     * @return
     */
    public static RouterScanner getInstance() {
        if (routerScanner == null) {
            synchronized (RouterScanner.class) {
                if (routerScanner == null) {
                    routerScanner = new RouterScanner();
                }
            }
        }
        return routerScanner;
    }

    private RouterScanner() {
    }

    @PostConstruct
    public void routeMapping() throws Exception {
        if (classRoutes == null || methodRoutes == null) {
        	methodRoutes = new HashMap<>(16);
        	classRoutes = new HashMap<>(16);
            loadRouteMapping(appConfig.getRootPackageName());
        }
    }
    
    /**
     * get route method
     *
     * @param queryStringDecoder
     * @return
     * @throws Exception
     */
    public Method routeMethod(QueryStringDecoder queryStringDecoder) throws Exception {
        if (methodRoutes == null) {
        	routeMapping();
        }

        //default response
        boolean defaultResponse = defaultResponse(queryStringDecoder.path());

        if (defaultResponse) {
            return null;
        }

        Method method = methodRoutes.get(queryStringDecoder.path());

        if (method == null) {
            throw new CicadaException(StatusEnum.NOT_FOUND);
        }

        return method;
    }
    
    /**
     * get route class
     *
     * @param queryStringDecoder
     * @return
     * @throws Exception
     */
    public Class<?> routeClass(String url) throws Exception {
        if (classRoutes == null) {
        	routeMapping();
        }
        
        //default response
        boolean defaultResponse = defaultResponse(url);
        if (defaultResponse) {
            return null;
        }

        String[] routePaths = url.split("/");
        if(routePaths == null || routePaths.length < 3) {
        	return null;
        }

        Class<?> clazz = classRoutes.get("/"+routePaths[1]+"/"+routePaths[2]);

        if (clazz == null) {
            throw new CicadaException(StatusEnum.NOT_FOUND);
        }

        return clazz;
    }

    private boolean defaultResponse(String path) {
        if (appConfig.getRootPath().equals(path)) {
            CicadaContext.getContext().html("<center> Hello Cicada <br/><br/>" +
                    "Power by <a href='https://github.com/TogetherOS/cicada'>@Cicada</a> </center>");
            return true;
        }
        return false;
    }
    

    /**
     * load class & method mapping
     * @param packageName
     * @throws Exception
     */
    public void loadRouteMapping(String packageName) throws Exception {
    	Set<Class<?>> classes = ClassScanner.getClasses(packageName);

        for (Class<?> aClass : classes) {
            CicadaAction cicadaAction = aClass.getAnnotation(CicadaAction.class);
            if(cicadaAction == null) {
            	continue;
            }
            
        	classRoutes.put(appConfig.getRootPath() + "/" + cicadaAction.value(), aClass);
        	
            Method[] declaredMethods = aClass.getMethods();
            for (Method method : declaredMethods) {
                CicadaRoute routeAnnotation = method.getAnnotation(CicadaRoute.class);
                if (routeAnnotation == null) {
                    continue;
                }

                methodRoutes.put(appConfig.getRootPath() + "/" + cicadaAction.value() + "/" + routeAnnotation.value(), method);
            }
        }
    }
}
