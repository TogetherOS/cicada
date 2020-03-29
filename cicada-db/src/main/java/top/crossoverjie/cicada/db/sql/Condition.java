package top.crossoverjie.cicada.db.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-28 00:49
 * @since JDK 1.8
 */
public abstract class Condition {

    public Condition(String property, Object value){

    }

    public int type(){
        return 0 ;
    }

    public Condition process(String property, Object value){
        return this ;
    }


    public Filter getCondition(){
        return null ;
    }

    @Data
    @AllArgsConstructor
    public static class Filter{
        private String filed ;
        private Object value ;
    }

}
