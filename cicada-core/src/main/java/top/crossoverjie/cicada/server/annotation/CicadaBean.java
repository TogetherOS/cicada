package top.crossoverjie.cicada.server.annotation;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CicadaBean {

    String value() default "" ;
}
