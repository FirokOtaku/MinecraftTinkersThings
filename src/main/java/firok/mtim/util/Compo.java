package firok.mtim.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Compo
{
	String name();
	String item() default "";
	String[] items() default {};
	String fluid();
	boolean craftable() default true;
	boolean castatble() default true;
}
