package firok.tiths.util.reg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在主世界生成矿物
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenOre
{
	int minY() default 5;
	int maxY() default 128;
	int times() default 4;
	double timeRate() default 1;
	int size() default 15;
	int[] dimsBanned() default { 1, -1 };
}
