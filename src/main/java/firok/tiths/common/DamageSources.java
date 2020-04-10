package firok.tiths.common;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

@SuppressWarnings("all")
public class DamageSources
{
	// 精神伤害
	public static final DamageSource SanDamage=new DamageSource("san")
			.setMagicDamage()
			.setDamageBypassesArmor();
	// 双折伤害
	public static final DamageSource BirefringentDamage=new DamageSource("birefringent");
	// 热释电伤害
	public static final DamageSource PyroelectricDamage=new DamageSource("pyroelectric");
	public static final DamageSource ThunderingDamage=new DamageSource("thundering")
			.setDamageBypassesArmor();
	// 屠龙伤害
	public static final DamageSource DragonKillerDamage=new DamageSource("dragon_killer");

	public static final String TypeDiffuseReflecting="diffuse_reflecting";
	// 漫反射伤害
	public static EntityDamageSource DiffuseReflecting(Entity source)
	{
		EntityDamageSource ret=new EntityDamageSource(TypeDiffuseReflecting,source);
		ret.setDifficultyScaled();
		ret.setMagicDamage();
		return ret;
	}
}
