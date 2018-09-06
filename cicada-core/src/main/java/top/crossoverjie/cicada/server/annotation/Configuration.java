package top.crossoverjie.cicada.server.annotation;

/**
 * @Author liwenguang
 * @Date 2018/9/7 上午2:27
 * @Description
 */

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configuration {
    String prefix() default "" ;
}