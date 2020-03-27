package firok.tiths.util.reg;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@SuppressWarnings("all")
public interface FilterHandler<TypeEntry,TypeAnnotation extends Annotation>
{
	boolean test(Field field, TypeAnnotation annotation, TypeEntry obj);
}
