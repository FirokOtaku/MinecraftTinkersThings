package firok.tiths.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于在主世界生成陨石
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenMeteoWorld
{
	/**
	 * @return 每区块生成几率
	 */
	float rateChunk() default 0.0128f;

	/**
	 * @return 矿石组成几率
	 */
	float rateOre() default 0.225f;
}
