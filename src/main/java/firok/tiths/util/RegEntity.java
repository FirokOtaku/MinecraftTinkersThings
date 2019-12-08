package firok.tiths.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RegEntity
{
	int network();
	int tracker() default 48;
	int updateFrequency() default 3;
	boolean sendUpdates() default true;
}
