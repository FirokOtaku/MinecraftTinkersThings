package firok.mtim.util;

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
	/**
	 * texture name
	 */
	String tn();

	/**
	 * unlocalized name
	 */
	String un();
}
