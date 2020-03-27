package firok.tiths.util.reg;

import firok.tiths.TinkersThings;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 类字段流
 */
@SuppressWarnings("all")
public class FieldStream<TypeEntry,TypeAnnotation extends Annotation>
{
	private List<Field> fields;
	private List<TypeEntry> entries;
	private List<TypeAnnotation> annotations;
	private Consumer<Exception> callbackException;

	private FieldStream()
	{
		fields=new ArrayList<>();
		entries=new ArrayList<>();
		annotations=new ArrayList<>();
		callbackException=TinkersThings::log;
	}

	public FieldStream<TypeEntry,TypeAnnotation> whenFail(Consumer<Exception> callbackException)
	{
		Objects.requireNonNull(callbackException);

		this.callbackException=callbackException;
		return this;
	}
	public FieldStream<TypeEntry,TypeAnnotation> filter(FilterHandler<TypeEntry,TypeAnnotation> filter)
	{
		Objects.requireNonNull(filter);

		Iterator<Field> iterField=fields.iterator();
		Iterator<TypeEntry> iterEntry=entries.iterator();
		Iterator<TypeAnnotation> iterAnno=annotations.iterator();

		while(iterEntry.hasNext() && iterField.hasNext() && iterAnno.hasNext())
		{
			if(!filter.test(iterField.next(),iterAnno.next(),iterEntry.next()))
			{
				iterEntry.remove();
				iterAnno.remove();
				iterField.remove();
			}
		}

		return this;
	}
	public void forEach(FieldHandler<TypeEntry,TypeAnnotation> handler)
	{
		Objects.requireNonNull(handler);

		Iterator<Field> iterField=fields.iterator();
		Iterator<TypeEntry> iterEntry=entries.iterator();
		Iterator<TypeAnnotation> iterAnno=annotations.iterator();

		while(iterEntry.hasNext() && iterField.hasNext())
		{
			try
			{
				handler.handle(iterField.next(),iterAnno.next(),iterEntry.next());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				callbackException.accept(e);
			}
		}
	}

	public static <TypeEntry,TypeAnnotation extends Annotation>
	FieldStream<TypeEntry,TypeAnnotation>
	of(Class clasz, Object object, Class<TypeEntry> claszFlag, Class<TypeAnnotation> claszAnnotation)
	{
		FieldStream<TypeEntry,TypeAnnotation> ret=new FieldStream<>();

		Field[] fieldsClasz=clasz.getDeclaredFields();
		for(Field fieldClasz:fieldsClasz)
		{
			try
			{
				Object obj=fieldClasz.get(object);
				TypeAnnotation anno=fieldClasz.getAnnotation(claszAnnotation);
				if(!claszFlag.isInstance(obj) || anno==null) continue;

				ret.fields.add(fieldClasz);
				ret.entries.add((TypeEntry)obj);
				ret.annotations.add(anno);
			}
			catch (Exception ignore) {}
		}

		return ret;
	}
	public static <TypeEntry,TypeAnnotation extends Annotation>
	FieldStream<TypeEntry,TypeAnnotation>
	of(Class clasz, Object object, Class<TypeEntry> claszFlag)
	{
		FieldStream<TypeEntry,TypeAnnotation> ret=new FieldStream<>();

		Field[] fieldsClasz=clasz.getDeclaredFields();
		for(Field fieldClasz:fieldsClasz)
		{
			try
			{
				Object obj=fieldClasz.get(object);
				TypeAnnotation anno=null;
				if(!claszFlag.isInstance(obj)) continue;

				ret.fields.add(fieldClasz);
				ret.entries.add((TypeEntry)obj);
				ret.annotations.add(anno);
			}
			catch (Exception ignore) {}
		}

		return ret;
	}
}
