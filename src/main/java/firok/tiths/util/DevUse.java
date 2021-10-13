package firok.tiths.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * all contents marked with {@code @DevOnly} are for dev-use only. <br>
 *
 * those contents typically have not bean well-tested. <br>
 * before being accessed by players,
 * they need to be rewrote completely or deleted to improve code quality.
 *
 * @author Firok
 * @since 0.4.2
 */
@Retention(RetentionPolicy.SOURCE)
public @interface DevUse
{
	/**
	 * @return whether this thing will be implemented in the future, or this is totally a dev-use placeholder
	 */
	boolean isPlaceholder() default false;
}
