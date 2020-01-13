package firok.tiths.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * used to register items and blocks or any others.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Reg
{
	String value() default "";
	/**
	 * texture name
	 */
	String tn() default "";

	/**
	 * unlocalized name
	 */
	String un() default "";

	/**
	 * ore dic name
	 */
	String[] od() default {};
}
