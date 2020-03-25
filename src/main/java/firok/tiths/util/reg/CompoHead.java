package firok.tiths.util.reg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CompoHead
{
	int durability();
	double miningspeed();
	double attack();
	int harvestLevel();

	String[] traits() default {};
}
