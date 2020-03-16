package firok.tiths.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 护甲护甲板属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CompoArmorPlate
{
	/**
	 * @return 耐久
	 */
	double modifier();

	/**
	 * @return 耐久
	 */
	double durability();

	/**
	 * @return 韧性
	 */
	double toughness();

	String[] traits() default {};
}
