package top.crossoverjie.cicada.server.route;

import io.netty.handler.codec.http.QueryStringDecoder;
import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.annotation.CicadaRoute;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.enums.HttpMethod;
import top.crossoverjie.cicada.server.enums.StatusEnum;
import top.crossoverjie.cicada.server.exception.CicadaException;
import top.crossoverjie.cicada.server.reflect.ClassScanner;
import top.crossoverjie.cicada.server.route.register.RouterMethodInfo;
import top.crossoverjie.cicada.server.route.register.RequestRouteInfo;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/13 01:48
 * @since JDK 1.8
 */
public class RouterScanner {

    //private static Map<String, Method> routes = null;

    private static Map<RequestRouteInfo, Method> routes = new HashMap<>(16);


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

    private RouterScanner(){
        try {
            registerRouter(appConfig.getRootPackageName());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *  get route method
     *
     * @param method http method
     * @param queryStringDecoder
     * @return
     * @throws Exception
     */
    public Method routeMethod(String method, QueryStringDecoder queryStringDecoder) throws Exception {

        //default response
        boolean defaultResponse = defaultResponse(queryStringDecoder.path());

        if (defaultResponse) {
            return null;
        }

        RequestRouteInfo info = new RequestRouteInfo(queryStringDecoder.path(), HttpMethod.resolve(method));
        boolean isContains = routes.containsKey(info);
        if (!isContains){
            throw new CicadaException(StatusEnum.NOT_FOUND);
        }
        return routes.get(info);
    }

    private boolean defaultResponse(String path) {
        if (appConfig.getRootPath().equals(path)) {
            CicadaContext.getContext().html("<center> Hello Cicada <br/><br/>" +
                    "Power by <a href='https://github.com/TogetherOS/cicada'>@Cicada</a> </center>");
            return true;
        }
        return false;
    }

    private void registerRouter(String packageName) throws Exception {
        Set<Class<?>> classes = ClassScanner.getClasses(packageName);
        if (routes == null){
            routes = new HashMap<>(16);
        }

        Set<RouterMethodInfo> defaultRouterMethod = new HashSet<>(16);

        for (Class<?> aClass : classes) {
            Method[] declaredMethods = aClass.getMethods();
            CicadaAction cicadaAction = aClass.getAnnotation(CicadaAction.class);

            for (Method method : declaredMethods) {
                CicadaRoute annotation = method.getAnnotation(CicadaRoute.class);
                if (annotation == null) {
                    continue;
                }
                HttpMethod[] methods = annotation.method();
                String url = appConfig.getRootPath() + "/" + cicadaAction.value() + "/" + annotation.value();

                if (methods.length <= 0){
                    RouterMethodInfo info = new RouterMethodInfo(url,method);
                    //判断是否重复标记
                    if (defaultRouterMethod.contains(info)){
                        throw new CicadaException(StatusEnum.REPEAT_ROUTE);
                    }
                    defaultRouterMethod.add(info);
                }else {
                    for (HttpMethod httpMethod : methods) {

                        RequestRouteInfo info = new RequestRouteInfo(url,httpMethod);
                        if (routes.containsKey(info)){
                            throw new CicadaException(StatusEnum.REPEAT_ROUTE);
                        }
                        routes.put(info,method);
                    }
                }
            }
        }

        for (RouterMethodInfo routerMethodInfo : defaultRouterMethod) {
            String url = routerMethodInfo.getPath();
            Method method = routerMethodInfo.getMethod();
            HttpMethod[] methods = HttpMethod.values();

            for (HttpMethod httpMethod : methods) {
                RequestRouteInfo info = new RequestRouteInfo(url,httpMethod);
                if (routes.containsKey(info)){
                    continue;
                }
                routes.put(info,method);
            }
        }
    }
}
