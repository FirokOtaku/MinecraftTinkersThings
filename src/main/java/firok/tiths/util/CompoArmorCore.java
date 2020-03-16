package firok.tiths.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 护甲核心属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CompoArmorCore
{
	/**
	 * @return 耐久
	 */
	double durability();

	/**
	 * @return 护甲
	 */
	double defense();

	String[] traits() default {};
}
