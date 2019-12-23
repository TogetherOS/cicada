package top.crossoverjie.cicada.db;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import org.junit.Assert;
import org.junit.Test;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-24 01:13
 * @since JDK 1.8
 */
public class SQLBuilder {

    @Test
    public void test() {
        DbSpec _spec = new DbSpec();
        DbSchema _schema1 = _spec.addSchema("Schema1");
        DbTable _table1 = _schema1.addTable("Table1");
        DbColumn _table1_col1 = _table1.addColumn("col1", "VARCHAR", 213);
        DbColumn _table1_col2 = _table1.addColumn("col2", "VARCHAR", 213);
        SelectQuery selectQuery1 = new SelectQuery()
                .addColumns(_table1_col1, _table1_col2);

        String selectStr1 = selectQuery1.toString();
        Assert.assertEquals(selectStr1,
                "SELECT t0.col1,t0.col2 FROM Schema1.Table1 t0");
    }


    @Test
    public void update(){
        DbSpec _spec = new DbSpec();
        DbSchema _schema1 = _spec.addSchema("Schema1");
        DbTable _table1 = _schema1.addTable("Table1");
        DbColumn _table1_col1 = _table1.addColumn("col1", "VARCHAR", 213);
        DbColumn _table1_col2 = _table1.addColumn("col2", "VARCHAR", 213);
        UpdateQuery updateQuery = new UpdateQuery(_table1)
                .addSetClause(_table1_col1,"abc2")
                .addCondition(BinaryCondition.equalTo(_table1_col1,"abc")) ;
        System.out.println(updateQuery.toString());
    }

    @Test
    public void insert(){
        DbSpec _spec = new DbSpec();
        DbSchema _schema1 = _spec.addSchema("Schema1");
        DbTable _table1 = _schema1.addTable("Table1");
        DbColumn _table1_col1 = _table1.addColumn("col1", "VARCHAR", 213);
        DbColumn _table1_col2 = _table1.addColumn("col2", "VARCHAR", 213);
        InsertQuery insertSelectQuery = new InsertQuery(_table1) ;
        insertSelectQuery.addPreparedColumns(_table1_col1,_table1_col2) ;

        System.out.println(insertSelectQuery.toString());
    }
}
