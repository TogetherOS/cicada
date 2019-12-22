package top.crossoverjie.cicada.db.reflect;

import top.crossoverjie.cicada.db.annotation.FieldName;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-22 00:54
 * @since JDK 1.8
 */
public class ReflectTools {

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


    /**
     * Convert model field to DB field
     * @param field
     * @return
     */
    public static String getDbField(Field field) {
        String dbField;
        FieldName fieldAnnotation = field.getAnnotation(FieldName.class);
        if (fieldAnnotation != null) {
            dbField = fieldAnnotation.value();
        } else {
            dbField = field.getName();
        }
        return dbField;
    }
}
