package firok.tiths.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;


public final class Selectors
{
	private Selectors(){}

	public static final Predicate<? super Entity> mobAlive= en->en instanceof EntityMob && en.isEntityAlive();

}
