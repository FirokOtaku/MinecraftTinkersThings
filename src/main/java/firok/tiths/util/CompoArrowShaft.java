package firok.tiths.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 箭杆属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CompoArrowShaft
{
	double modifier(); // 品质系数
	int bonusAmmo(); // 额外弹药
}
