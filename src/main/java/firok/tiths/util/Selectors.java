package firok.tiths.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;

@SuppressWarnings("all")
public final class Selectors
{
	private Selectors(){}

	public static final Predicate<? super Entity> mobAlive = en -> (en instanceof IMob) && en.isEntityAlive();

	public static final Predicate<? super Entity> livingBaseAlive = en -> (en instanceof EntityLivingBase) && en.isEntityAlive();

}
