package top.crossoverjie.cicada.server.route;

import io.netty.handler.codec.http.QueryStringDecoder;
import top.crossoverjie.cicada.server.bean.CicadaBeanManager;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.enums.StatusEnum;
import top.crossoverjie.cicada.server.exception.CicadaException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/13 21:18
 * @since JDK 1.8
 */
public class RouteProcess {

    private volatile static RouteProcess routeProcess;

    private final CicadaBeanManager cicadaBeanManager = CicadaBeanManager.getInstance() ;

    public static RouteProcess getInstance() {
        if (routeProcess == null) {
            synchronized (RouteProcess.class) {
                if (routeProcess == null) {
                    routeProcess = new RouteProcess();
                }
            }
        }
        return routeProcess;
    }

    /**
     * invoke route method
     * @param method
     * @param queryStringDecoder
     * @throws Exception
     */
    public void invoke(Method method, QueryStringDecoder queryStringDecoder) throws Exception {
        Object[] object = parseRouteParameter(method, queryStringDecoder);
        Object bean = cicadaBeanManager.getBean(method.getDeclaringClass().getName());
        if (object == null){
            method.invoke(bean) ;
        }else {
            method.invoke(bean, object);
        }
    }

    /**
     * parse route's parameter
     *
     * @param method
     * @param queryStringDecoder
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    private Object[] parseRouteParameter(Method method, QueryStringDecoder queryStringDecoder) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class<?>[] parameterTypes = method.getParameterTypes();

        if (parameterTypes.length == 0){
            return null;
        }

        if (parameterTypes.length > 2) {
            throw new CicadaException(StatusEnum.ILLEGAL_PARAMETER);
        }

        Object[] instances = new Object[parameterTypes.length] ;

        for (int i = 0; i < instances.length; i++) {
            //inject cicada context instance
            if (parameterTypes[i] == CicadaContext.class){
                instances[i] = CicadaContext.getContext() ;
            }else {
                //inject custom pojo
                Class<?> parameterType = parameterTypes[i];
                Object instance = parameterType.newInstance();

                Map<String, List<String>> parameters = queryStringDecoder.parameters();
                for (Map.Entry<String, List<String>> param : parameters.entrySet()) {
                    Field field = parameterType.getDeclaredField(param.getKey());
                    field.setAccessible(true);
                    field.set(instance, parseFieldValue(field, param.getValue().get(0)));
                }
                instances[i] = instance ;
            }
        }

        return instances;
    }


    private Object parseFieldValue(Field field, String value) {
        if (value == null) {
            return null;
        }

        Class<?> type = field.getType();
        if ("".equals(value)) {
            boolean base = type.equals(int.class) || type.equals(double.class) ||
                    type.equals(short.class) || type.equals(long.class) ||
                    type.equals(byte.class) || type.equals(float.class);
            if (base) {
                return 0;
            }
        }
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(String.class)) {
            return value;
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(Float.class) || type.equals(float.class)) {
            return Float.parseFloat(value);
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            return Long.parseLong(value);
        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (type.equals(Short.class) || type.equals(short.class)) {
            return Short.parseShort(value);
        } else if (type.equals(Byte.class) || type.equals(byte.class)) {
            return Byte.parseByte(value);
        } else if (type.equals(BigDecimal.class)) {
            return new BigDecimal(value);
        }

        return null;
    }

}
