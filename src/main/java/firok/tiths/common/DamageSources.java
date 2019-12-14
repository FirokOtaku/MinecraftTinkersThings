package firok.tiths.common;

import net.minecraft.util.DamageSource;

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
}
