package top.crossoverjie.cicada.server.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Interceptor {
    int order() default 0 ;
}
