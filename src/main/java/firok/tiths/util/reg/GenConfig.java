package firok.tiths.util.reg;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记生成矿物<br>
 * 通过这个注解的值, 读取Config内的数据, 创建世界生成器
 * @see firok.tiths.common.Blocks
 * @see firok.tiths.common.Configs.WorldGens
 * @see firok.tiths.common.WorldGens
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenConfig
{
	String value();
}
