package firok.tiths.util.reg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <code>FieldStream</code>会略过被<code>@Indev</code>标记的字段
 * @see FieldStream
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Indev
{

}
