package firok.tiths.util.reg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于在世界生成陨石
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenMeteo
{
	/**
	 * @return 每区块生成几率
	 */
	float rateChunk() default 0.0128f;

	/**
	 * @return 矿石组成几率
	 */
	float rateOre() default 0.225f;


	/**
	 * @return 默认禁用生成世界列表
	 */
	int[] dimsBanned() default { 1, -1 };
}
