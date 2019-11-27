package top.crossoverjie.cicada.db.reflect;

import lombok.Data;
import top.crossoverjie.cicada.db.annotation.FieldName;
import top.crossoverjie.cicada.db.annotation.OriginName;
import top.crossoverjie.cicada.db.model.Model;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-11-20 11:26
 * @since JDK 1.8
 */
@Data
@OriginName("user")
public class User extends Model {
    private Integer id ;
    private String name ;
    private String password ;

    @FieldName(value = "city_id")
    private Integer cityId ;

    private String description ;

}
