package top.crossoverjie.cicada.server.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CicadaExcute {

    String value() default "" ;
}
