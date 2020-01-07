package firok.tiths.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 弓臂属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CompoBow
{
	float drawSpeed(); // 拉弓速度
	float range(); // 射程系数
	float bonusDamage(); // 额外伤害
}
