package firok.tiths.util.reg;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@SuppressWarnings("all")
@FunctionalInterface
public interface FieldHandler<TypeEntry,TypeAnnotation extends Annotation>
{
	void handle(Field field, TypeAnnotation annotation, TypeEntry entry) throws Exception;
}
