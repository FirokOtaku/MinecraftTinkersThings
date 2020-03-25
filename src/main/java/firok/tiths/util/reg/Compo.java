package firok.tiths.util.reg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标记指定材料可用于工具部件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Compo
{
	String value();

	/**
	 * 工具属性
	 */
	String[] traitsTool() default {};

	/**
	 * 护甲属性
	 */
	String[] traitsArmor() default {};
}
