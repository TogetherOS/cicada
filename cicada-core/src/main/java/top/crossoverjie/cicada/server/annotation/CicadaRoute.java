package top.crossoverjie.cicada.server.annotation;


import top.crossoverjie.cicada.server.enums.HttpMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CicadaRoute {

    String value() default "" ;

    HttpMethod[] method() default {};
}
