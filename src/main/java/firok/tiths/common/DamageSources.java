package firok.tiths.common;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public final class DamageSources
{
	private DamageSources() {}

	// 精神伤害 // 目前仅用于血沙 非实体伤害源
	public static final String TypeSan="san";
	public static final DamageSource SanDamage=new DamageSource(TypeSan)
			.setMagicDamage()
			.setDamageBypassesArmor();

	// 测试伤害 // 仅能使用指令给予
	public static final String TypeTest="test";
	public static DamageSource TestDamage=new DamageSource(TypeTest);

	public static final String TypePunji="punji";
	public static DamageSource PunjiDamage=new DamageSource(TypePunji);

	// 双折伤害
	public static final String TypeBirefringent="birefringent";
	public static EntityDamageSource Birefringent(Entity source)
	{
		EntityDamageSource ret=new EntityDamageSource(TypeBirefringent,source);
		ret.setDifficultyScaled();
		ret.setMagicDamage();
		return ret;
	}

	// 热释电伤害
	public static final String TypePyroelectric="pyroelectric";
	public static EntityDamageSource Pyroelectric(Entity source)
	{
		EntityDamageSource ret=new EntityDamageSource(TypePyroelectric,source);
		ret.setMagicDamage();
		return ret;
	}

	// 雷鸣伤害
	public static final String TypeThundering="thundering";
	public static EntityDamageSource Thundering(Entity source)
	{
		EntityDamageSource ret=new EntityDamageSource(TypeThundering,source);
		ret.setDamageBypassesArmor();
		return ret;
	}

	// 屠龙伤害
	public static final String TypeDragonKiller="dragon_killer";
	public static EntityDamageSource DragonKiller(Entity source)
	{
		return new EntityDamageSource(TypeDragonKiller,source);
	}

	public static final String TypeDiffuseReflecting="diffuse_reflecting";
	// 漫反射伤害
	public static EntityDamageSource DiffuseReflecting(Entity source)
	{
		EntityDamageSource ret=new EntityDamageSource(TypeDiffuseReflecting,source);
		ret.setDifficultyScaled();
		ret.setMagicDamage();
		return ret;
	}

	public static final String TypeEmpathic="empathic";
	// 共感伤害
	public static EntityDamageSource Empathic(Entity source)
	{
		EntityDamageSource ret=new EntityDamageSource(TypeEmpathic,source);
		ret.setDamageBypassesArmor();
		return ret;
	}
}
