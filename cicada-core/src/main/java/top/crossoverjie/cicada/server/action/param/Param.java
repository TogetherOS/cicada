package top.crossoverjie.cicada.server.action.param;

import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/2 11:24
 * @since JDK 1.8
 */
public interface Param extends Map<String, Object> {

    String getString(String param);

    Integer getInteger(String param);

    Long getLong(String param);

    Double getDouble(String param);

    Float getFloat(String param);

    Boolean getBoolean(String param) ;
}
