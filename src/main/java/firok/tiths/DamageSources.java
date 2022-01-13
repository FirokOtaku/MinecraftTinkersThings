package firok.tiths;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class DamageSources
{
	// 精神伤害 // 目前仅用于血沙 非实体伤害源
	public static final String TypeSan = "san";
	public static final DamageSource SanDamage = new DamageSource(TypeSan)
			.setMagicDamage()
			.setDamageBypassesArmor();
	// 测试伤害 // 仅能使用指令给予
	public static final String TypeTest = "test";
	public static final String TypePunji = "punji";
	// 影火伤害
	public static final String TypeShadowFire = "shadow_fire";
	// 双折伤害
	public static final String TypeBirefringent = "birefringent";
	// 热释电伤害
	public static final String TypePyroelectric = "pyroelectric";
	// 星绽伤害
	public static final String TypeDashingStat = "dashing_star";
	// 雷鸣伤害
	public static final String TypeThundering = "thundering";
	// 屠龙伤害
	public static final String TypeDragonKiller = "dragon_killer";
	public static final String TypeDiffuseReflecting = "diffuse_reflecting";
	public static final String TypeEmpathic = "empathic";
	public static final String TypeOracular = "oracular";
	public static DamageSource TestDamage = new DamageSource(TypeTest);
	public static DamageSource PunjiDamage = new DamageSource(TypePunji);
	public static DamageSource ShadowFireDamage = new DamageSource(TypeShadowFire)
			.setFireDamage()
			.setMagicDamage();

	private DamageSources()
	{
	}

	public static EntityDamageSource Birefringent(Entity source)
	{
		EntityDamageSource ret = new EntityDamageSource(TypeBirefringent, source);
		ret.setDifficultyScaled();
		ret.setMagicDamage();
		return ret;
	}

	public static EntityDamageSource Pyroelectric(Entity source)
	{
		EntityDamageSource ret = new EntityDamageSource(TypePyroelectric, source);
		ret.setMagicDamage();
		return ret;
	}

	public static EntityDamageSource DashingStar(Entity source, Entity indirect)
	{
		IndirectEntityDamageSource ret = new IndirectEntityDamageSource(TypeDashingStat, indirect, source);
		ret.setFireDamage();
		return ret;
	}

	public static EntityDamageSource Thundering(Entity source)
	{
		EntityDamageSource ret = new EntityDamageSource(TypeThundering, source);
		ret.setDamageBypassesArmor();
		return ret;
	}

	public static EntityDamageSource DragonKiller(Entity source)
	{
		return new EntityDamageSource(TypeDragonKiller, source);
	}

	// 漫反射伤害
	public static EntityDamageSource DiffuseReflecting(Entity source)
	{
		EntityDamageSource ret = new EntityDamageSource(TypeDiffuseReflecting, source);
		ret.setDifficultyScaled();
		ret.setMagicDamage();
		return ret;
	}

	// 共感伤害
	public static EntityDamageSource Empathic(Entity source)
	{
		EntityDamageSource ret = new EntityDamageSource(TypeEmpathic, source);
		ret.setDamageBypassesArmor();
		return ret;
	}

	// 神谕伤害
	public static EntityDamageSource Oracular(Entity source)
	{
		EntityDamageSource ret = new EntityDamageSource(TypeOracular, source);
		ret.setDamageBypassesArmor();
		ret.setMagicDamage();
		return ret;
	}
}
