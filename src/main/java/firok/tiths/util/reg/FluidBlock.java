package firok.tiths.util.reg;

import net.minecraft.block.Block;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来手动为流体注册方块的
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FluidBlock
{
	Class<? extends Block> value();
}
