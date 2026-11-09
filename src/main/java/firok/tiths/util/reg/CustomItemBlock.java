package firok.tiths.util.reg;

import net.minecraft.item.ItemBlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为方块字段指定自定义 ItemBlock 实现类.
 * 目标类必须有单参构造 {@code (Block)}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomItemBlock
{
    Class<? extends ItemBlock> value();
}
