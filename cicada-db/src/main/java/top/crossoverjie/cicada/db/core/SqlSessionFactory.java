package top.crossoverjie.cicada.db.core;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-12-23 15:41
 * @since JDK 1.8
 */
public abstract class SqlSessionFactory {

    private SqlSession origin;

    public SqlSessionFactory() {
        origin = SqlSession.getInstance();
    }

    public SqlSession origin(){
        return this.origin ;
    }

}
