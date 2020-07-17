package firok.tiths.util;

import com.google.common.base.Predicate;
import firok.tiths.entity.special.EnderBeacon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.*;

@SuppressWarnings("all")
public final class Selectors
{
	private Selectors(){}

	public static final Predicate<? super Entity> mobAlive = en -> (en instanceof EntityLivingBase) && (en instanceof IMob) && en.isEntityAlive();

	public static final Predicate<? super Entity> livingBaseAlive = en -> (en instanceof EntityLivingBase) && en.isEntityAlive();

	public static final Predicate<? super Entity> beaconActive = en -> en instanceof EnderBeacon;

	public static final Predicate<? super Entity> pigAlive = en -> en instanceof EntityPig && en.isEntityAlive();

	public static final Predicate<? super Entity> sheepAlive = en -> en instanceof EntitySheep && en.isEntityAlive();

	public static final Predicate<? super Entity> cowAlive = en -> en instanceof EntityCow && en.isEntityAlive();

	public static final Predicate<? super Entity> chickenAlive = en -> en instanceof EntityChicken && en.isEntityAlive();

	public static final Predicate<? super Entity> catAlive = en -> en instanceof EntityOcelot && en.isEntityAlive();

	public static final Predicate<? super Entity> rabbitAlive = en -> en instanceof EntityRabbit && en.isEntityAlive();
}
