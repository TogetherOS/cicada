package top.crossoverjie.cicada.db.sql;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-28 01:29
 * @since JDK 1.8
 */
public class EqualToCondition extends Condition {

    private Filter filter = null ;
    private int type = 1;

    public EqualToCondition(String property, Object value) {
        super(property, value);
        filter = new Filter(property,value) ;
    }

    @Override
    public Condition process(String property, Object value) {
        filter = new Filter(property,value) ;
        return this ;
    }

    @Override
    public Filter getCondition() {
        return filter ;
    }

    @Override
    public int type() {
        return type ;
    }
}
