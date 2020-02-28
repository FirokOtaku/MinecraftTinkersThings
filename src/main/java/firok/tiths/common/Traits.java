package firok.tiths.common;

import firok.tiths.traits.*;
import net.minecraft.init.MobEffects;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitAntiPoisonous;
import static firok.tiths.common.Keys.nameTraitAntiPoisonous;

@SuppressWarnings("all")
public class Traits
{
	public static final AbstractTrait maiming=new TraitMaiming(); // 致残
	public static final AbstractTrait lionheart=new TraitLionheart(); // 狮心
	public static final AbstractTrait terrifying=new TraitTerrifying(); // 恐吓
	public static final AbstractTrait thundering=new TraitThundering(); // 雷鸣
	public static final AbstractTrait carbonizing=new TraitCarbonizing(); // 碳化
	public static final AbstractTrait gluttonic=new TraitGluttonic(); // 暴食
	public static final AbstractTrait antiPoisonous=new AbstractTraitAntiEffect(nameTraitAntiPoisonous,colorTraitAntiPoisonous,80,3,0.6f, MobEffects.POISON);
	public static final AbstractTrait sunPower=new TraitSunPower(); // 日之力量
	public static final AbstractTrait moonPower=new TraitMoonPower(); // 月之力量
	public static final AbstractTrait moonlight=new TraitMoonlight(); // 月光
	public static final AbstractTrait natualBlessing=new TraitNatureBlessing(); // 自然祝福
	public static final AbstractTrait withering=new TraitWithering(); // 凋零
	public static final AbstractTrait luxurious=new TraitLuxurious(); // 奢华
	public static final AbstractTrait retrospective=new TraitRetrospective(); // 后知
	public static final AbstractTrait radiant=new TraitRadiant(); // 辉耀
	public static final AbstractTrait switching=new TraitSwitching(); // 换位
	public static final AbstractTrait icy=new TraitIcy(); // 冰冷
	public static final AbstractTrait clustering=new TraitClustering(); // 群簇
	public static final AbstractTrait starDashing=new TraitStarDashing(); // 星绽
	public static final AbstractTrait soluble=new TraitSoluble(); // 可溶
	public static final AbstractTrait birefringent=new TraitBirefringent(); // 双折
	public static final AbstractTrait pyroelectric=new TraitPyroelectric(); // 热释电
	public static final AbstractTrait shaking=new TraitShaking(); // 撼击
	public static final AbstractTrait inky=new TraitInky(); // 墨染
	static{inky.addItem(Items.inkySlime);}
	public static final AbstractTrait gorgeous=new TraitGorgeous(); // 斑斓
	public static final AbstractTrait peaceEnergetic=new TraitPeaceEnergetic(); // 平和能量
	public static final AbstractTrait hyper=new TraitHyper(); // 振奋
	public static final AbstractTrait dichroic=new TraitDichroic(); // 二色性
	public static final AbstractTrait lifeInspiring=new TraitLifeInspiring(); // 生命激发
	public static final AbstractTrait chemicalInstable=new TraitChemicalInstable(); // 化学不稳定
	public static final AbstractTrait infernalBlazing=new TraitInfernalBlazing(); // 狱炎
	public static final AbstractTrait dragonKiller=new TraitDragonKiller(); // 屠龙者
	public static final AbstractTrait midasDesiring=new TraitMidasDesiring(); // 迈达斯之欲
	public static final AbstractTrait oracular=new TraitOracular(); // 神谕
	public static final AbstractTrait hemolytic=new TraitHemolytic(); // 溶血
	public static final AbstractTrait extremeFreezing=new TraitExtremeFreezing(); // 极寒
	public static final AbstractTrait antiGrav=new TraitAntiGrav(); // 反重力
	public static final AbstractTrait stonePhasing=new TraitStonePhasing(); // 石之相变
	public static final AbstractTrait thermalGathering=new TraitThermalGathering(); // 热力聚集
	static{stonePhasing.addItem(Items.phasingGem);}
	public static final AbstractTrait steamy=new TraitSteamy(); // 气动
	public static final AbstractTrait treasureDetecting=new TraitTreasureDetecting(); // 宝藏感知
	public static final AbstractTrait creaky=new TraitCreaky(); // 喀嚓
	public static final AbstractTrait undeadCalling=new TraitUndeadCalling(); // 亡灵呼唤
	public static final AbstractTrait repressing=new TraitRepressing(); // 压制
}
