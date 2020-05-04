package firok.tiths.util.reg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标记注册匠魂燃料
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RegSmelteryFuel
{
	int amount() default 50; // 多少液体
	int duration() default 100; // 对应多少持续时间
}
