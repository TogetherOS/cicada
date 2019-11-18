package top.crossoverjie.cicada.server.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Function:
 *	annotation of cicada exception
 * @author hugui
 * Date: 2019-11-17 12:07
 * @since JDK 1.8
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CicadaCustomizeExceptionHandle {

	Class<?> value();
	
}
