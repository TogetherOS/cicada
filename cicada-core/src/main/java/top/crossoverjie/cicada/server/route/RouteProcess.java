package top.crossoverjie.cicada.server.route;

import io.netty.handler.codec.http.QueryStringDecoder;
import top.crossoverjie.cicada.server.enums.StatusEnum;
import top.crossoverjie.cicada.server.exception.CicadaException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    public void invoke(Method method, QueryStringDecoder queryStringDecoder) throws IllegalAccessException, NoSuchFieldException, InstantiationException, InvocationTargetException {
        Object object = parseRouteParameter(method, queryStringDecoder);
        method.invoke(method.getDeclaringClass().newInstance(), object);
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
    private Object parseRouteParameter(Method method, QueryStringDecoder queryStringDecoder) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new CicadaException(StatusEnum.ILLEGAL_PARAMETER);
        }

        Class<?> parameterType = parameterTypes[0];
        Object instance = parameterType.newInstance();

        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        for (Map.Entry<String, List<String>> param : parameters.entrySet()) {
            Field field = parameterType.getDeclaredField(param.getKey());
            field.setAccessible(true);
            field.set(instance, parseFieldValue(field, param.getValue().get(0)));
        }

        return instance;
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
