package firok.mtim.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RegOre
{
	int maxWorldAmount() default 0;
	int minWorldAmount() default 0;
	short minWorldY() default 3;
	short maxWorldY() default 128;

	int maxNetherAmount() default 0;
	int minNetherAmount() default 0;
	short minNetherY() default 3;
	short maxNetherY() default 128;

	int maxEndAmount() default 0;
	int minEndAmount() default 0;
	short minEndY() default 3;
	short maxEndY() default 128;

	int maxOtherAmount() default 0;
	int minOtherAmount() default 0;
	short minOtherY() default 3;
	short maxOtherY() default 128;
}
