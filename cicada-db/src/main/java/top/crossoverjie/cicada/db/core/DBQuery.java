package top.crossoverjie.cicada.db.core;


import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.cicada.db.annotation.FieldName;
import top.crossoverjie.cicada.db.annotation.OriginName;
import top.crossoverjie.cicada.db.model.Model;
import top.crossoverjie.cicada.db.reflect.Instance;
import top.crossoverjie.cicada.db.reflect.MethodTools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-19 19:40
 * @since JDK 1.8
 */
@Slf4j
public final class DBQuery<T extends Model> {

    private Class<T> targetClass;

    private Map<String, String> conditions = new HashMap<>();

    private DBOrigin origin;

    public DBQuery query(Class<T> clazz) {
        origin = DBOrigin.getInstance();
        this.targetClass = clazz;
        return this;
    }

    public DBQuery filter(String filed, String condition) {
        conditions.put(filed, condition);
        return this;
    }

    public List<T> all() {
        List<T> result = null;
        String sql = buildSQL();
        Statement statement = null;
        try {
            statement = origin.getConnection().createStatement();
            log.debug("execute sql>>>>>{}", sql);
            ResultSet resultSet = statement.executeQuery(sql);
            result = new ArrayList<>();

            Map<String, Object> fields = new HashMap<>(8);
            while (resultSet.next()) {
                for (Field field : targetClass.getDeclaredFields()) {
                    String dbField = "";
                    FieldName fieldAnnotation = field.getAnnotation(FieldName.class);
                    if (fieldAnnotation != null) {
                        dbField = fieldAnnotation.value();
                    } else {
                        dbField = field.getName();
                    }

                    //get value from db
                    Method method = resultSet.getClass().getMethod(MethodTools.getMethod(field.getType().getName()), String.class);

                    Object value = method.invoke(resultSet, dbField);

                    fields.put(field.getName(), value);

                }

                T transfer = Instance.transfer(targetClass, fields);
                result.add(transfer);
            }
            resultSet.close();
        } catch (Exception e) {
            log.error("Exception", e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("Exception", e);
            }
        }


        return result;
    }

    private String buildSQL() {
        StringBuilder sql = new StringBuilder("select * from ");
        String tableName = targetClass.getAnnotation(OriginName.class).value();
        sql.append(tableName);
        if (conditions.size() > 0) {
            sql.append(" where ");
        }

        for (Map.Entry<String, String> condition : conditions.entrySet()) {
            sql.append(condition.getKey())
                    .append(" = ")
                    .append(condition.getValue())
            ;
        }

        return sql.toString();
    }

    public T first() {
        return this.all().get(0);
    }
}
