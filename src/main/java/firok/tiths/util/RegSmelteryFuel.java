package firok.tiths.util;

import java.lang.annotation.*;

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
