package firok.tiths.util.reg;

import net.minecraft.block.Block;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FluidBlock
{
	Class<? extends Block> value();
}
