package top.crossoverjie.cicada.db.core.handle;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.UpdateQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.cicada.db.annotation.OriginName;
import top.crossoverjie.cicada.db.annotation.PrimaryId;
import top.crossoverjie.cicada.db.core.DBOrigin;
import top.crossoverjie.cicada.db.model.Model;
import top.crossoverjie.cicada.db.reflect.Instance;
import top.crossoverjie.cicada.db.reflect.ReflectTools;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-12-03 23:53
 * @since JDK 1.8
 */
@Slf4j
public class DBHandleImpl implements DBHandle {

    private DbTable dbTable ;

    private DBOrigin origin;

    public DBHandleImpl() {
        origin = DBOrigin.getInstance();

    }

    @Override
    public int update(Class<? extends Model> clazz, int filed) {
        log.info("update from database") ;
        return 0;
    }

    @Override
    public int update(Object obj) {
        if (obj instanceof Model){
            dbTable = origin.addTable(obj.getClass().getAnnotation(OriginName.class).value()) ;

            Map<DbColumn,Integer> primaryCondition = new HashMap<>(1);
            UpdateQuery updateQuery = new UpdateQuery(dbTable) ;
            for (Field field : obj.getClass().getDeclaredFields()) {
                String dbField = ReflectTools.getDbField(field);

                Object filedValue = Instance.getFiledValue(obj, field);
                if (null == filedValue){
                    continue;
                }

                if (field.getAnnotation(PrimaryId.class) != null){
                    primaryCondition.put(dbTable.addColumn(dbField), (Integer) filedValue) ;
                }else {
                    DbColumn dbColumn = dbTable.addColumn(dbField);
                    updateQuery.addSetClause(dbColumn, filedValue) ;
                }

            }
            for (Map.Entry<DbColumn, Integer> entry : primaryCondition.entrySet()) {
                updateQuery.addCondition(BinaryCondition.equalTo(entry.getKey(),entry.getValue())) ;
            }

            Statement statement = null;
            try {
                statement = origin.getConnection().createStatement();
                log.debug("execute sql>>>>>{}", updateQuery.validate().toString());
                return statement.executeUpdate(updateQuery.toString());
            } catch (SQLException e) {
                log.error("SQLException",e);
            }finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("SQLException",e);
                }
            }
            return 0 ;
        }else {
            return 0;
        }
    }
}
