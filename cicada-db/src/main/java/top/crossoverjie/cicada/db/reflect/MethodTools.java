package top.crossoverjie.cicada.db.reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-22 00:54
 * @since JDK 1.8
 */
public class MethodTools {

    private static final Map<String, String> TYPE_MAPPING = new HashMap<>(8);

    static {
        TYPE_MAPPING.put(Integer.class.getName(), "getInt");
        TYPE_MAPPING.put(String.class.getName(), "getString");
        TYPE_MAPPING.put(Double.class.getName(), "getDouble");
        TYPE_MAPPING.put(Float.class.getName(), "getFloat");
    }

    public static String getMethod(String methodType){
        return TYPE_MAPPING.get(methodType) ;
    }
}
