package firok.tiths.common;

import firok.tiths.traits.*;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitAntiPoisonous;
import static firok.tiths.common.Keys.nameTraitAntiPoisonous;

@SuppressWarnings("all")
public final class Traits
{
	private Traits() {}

//	public static final AbstractTrait wrapping=new TraitWrapping(); // 折跃
//	static{wrapping.addItem(Items.enderGem);}
	public static AbstractTrait ablaze; // 灯明
	public static AbstractTrait angerOfFarmer; // 农夫之怒
	public static AbstractTrait annihilating; // 湮灭
	public static AbstractTrait antiGrav; // 反重力
	public static AbstractTrait aquatic; // 水生
	public static AbstractTrait antiPoisonous;
	public static AbstractTrait battleFocusing; // 战斗专注
	public static AbstractTrait birefringent; // 双折
	public static AbstractTrait blowing; // 吹袭
	public static AbstractTrait carbonizing; // 碳化
	public static AbstractTrait champing1; // 强袭1
	public static AbstractTrait champing2; // 强袭2
	public static AbstractTrait champing3; // 强袭3
	public static AbstractTrait chemicalInstable; // 化学不稳定
	public static AbstractTrait cherishing; // 缅怀
	public static AbstractTrait clustering; // 群簇
	public static AbstractTrait combustionSupporting; // 助燃
	public static AbstractTrait creaky; // 喀嚓
	public static AbstractTrait decoying; // 诱食
	public static AbstractTrait degenerating; // 简并
	public static AbstractTrait dichroic; // 二色性
	public static AbstractTrait dragonKiller; // 屠龙者
	public static AbstractTrait eddying; // 旋流
	public static AbstractTrait eroding; // 侵蚀
	public static AbstractTrait extremeFreezing; // 极寒
	public static AbstractTrait farmer; // 农场主
	public static AbstractTrait gluttonic; // 暴食
	public static AbstractTrait gorgeous; // 斑斓
	public static AbstractTrait green; // 绿意
	public static AbstractTrait hemolytic; // 溶血
	public static AbstractTrait hyper; // 振奋
	public static AbstractTrait icy; // 冰冷
	public static AbstractTrait inductance; // 电感
	public static AbstractTrait infernalBlazing; // 狱炎
	public static AbstractTrait infiltrating; // 透蚀
	public static AbstractTrait inky; // 墨染
	public static AbstractTrait lifeInspiring; // 生命激发
	public static AbstractTrait lionheart; // 狮心
	public static AbstractTrait luxurious; // 奢华
	public static AbstractTrait maiming; // 致残
	public static AbstractTrait midasDesiring; // 迈达斯之欲
	public static AbstractTrait moonPower; // 月之力量
	public static AbstractTrait moonlight; // 月光
	public static AbstractTrait natualBlessing; // 自然祝福
	public static AbstractTrait netting; // 捕网
	public static AbstractTrait oracular; // 神谕
	public static AbstractTrait peaceEnergetic; // 平和能量
	public static AbstractTrait piercing; // 锥心
	public static AbstractTrait pyroelectric; // 热释电
	public static AbstractTrait radiant; // 辉耀
	public static AbstractTrait rancher; // 牧场主
	public static AbstractTrait recombining; // 重组
	public static AbstractTrait respecting; // 尊重
	public static AbstractTrait repressing; // 压制
	public static AbstractTrait retrospective; // 后知
	public static AbstractTrait shaking; // 撼击
	public static AbstractTrait soluble; // 可溶
	public static AbstractTrait sovereign; // 君临
	public static AbstractTrait staminaFocusing; // 精力汇聚
	public static AbstractTrait starDashing; // 星绽
	public static AbstractTrait steamy; // 气动
	public static AbstractTrait stonePhasing; // 石之相变
	public static AbstractTrait sunPower; // 日之力量
	public static AbstractTrait switching; // 换位
	public static AbstractTrait terrifying; // 恐吓
	public static AbstractTrait thermalGathering; // 热力聚集
	public static AbstractTrait thunderWaving; // 雷鸣波动
	public static AbstractTrait tranquilizing; // 镇定
	public static AbstractTrait torrential; // 激流
	public static AbstractTrait treasureDetecting; // 宝藏感知
	public static AbstractTrait turbulent; // 乱流
	public static AbstractTrait undeadCalling; // 亡灵呼唤
	public static AbstractTrait unsettled; // 不安定
	public static AbstractTrait vibrating; // 震动
	public static AbstractTrait watery; // 水化
	public static AbstractTrait wither_flowing; // 凋零流动
//	public static AbstractTrait phantasmic=new TraitPhantasmic(); // 异象
//	static{phantasmic.addItem(Items.phantomCrystal,8,8);}

	public static AbstractProjectileTrait cracking; // 崩裂
	public static AbstractProjectileTrait frost_jack; // 冰霜杰克
	public static AbstractProjectileTrait ponderous; // 重磅
	public static AbstractProjectileTrait stellar_falling; // 金乌坠落

	public static void init()
	{
		ablaze = new TraitAblaze();
		angerOfFarmer = new TraitAngerOfFarmer();
		annihilating = new TraitAnnihilating();
		antiGrav = new TraitAntiGrav();
		aquatic = new TraitAquatic();
		antiPoisonous = new AbstractTraitAntiEffect(nameTraitAntiPoisonous, colorTraitAntiPoisonous, 80, 3, 0.6f, MobEffects.POISON);
		battleFocusing = new TraitBattleFocusing();
		birefringent = new TraitBirefringent();
		blowing = new TraitBlowing();
		carbonizing = new TraitCarbonizing();
		champing1=new TraitChamping(1);
		champing2=new TraitChamping(2);
		champing3=new TraitChamping(3);
		chemicalInstable = new TraitChemicalInstable();
		cherishing = new TraitCherishing();
		clustering = new TraitClustering();
		combustionSupporting=new TraitCombustionSupporting();
		creaky = new TraitCreaky();
		decoying = new TraitDecoying();
		degenerating = new TraitDegenerating();
		dichroic = new TraitDichroic();
		dragonKiller = new TraitDragonKiller();
		eddying = new TraitEddying();
		eroding = new TraitEroding();
		extremeFreezing = new TraitExtremeFreezing();
		farmer = new TraitFarmer();
		gluttonic = new TraitGluttonic();
		gorgeous = new TraitGorgeous();
		green = new TraitGreen();
		hemolytic = new TraitHemolytic();
		hyper = new TraitHyper();
		icy = new TraitIcy();
		inductance = new TraitInductance();
		infernalBlazing = new TraitInfernalBlazing();
		infiltrating = new TraitInfiltrating();
		inky = new TraitInky();
		lifeInspiring = new TraitLifeInspiring();
		lionheart = new TraitLionheart();
		luxurious = new TraitLuxurious();
		maiming = new TraitMaiming();
		midasDesiring = new TraitMidasDesiring();
		moonPower = new TraitMoonPower();
		moonlight = new TraitMoonlight();
		natualBlessing = new TraitNatureBlessing();
		netting = new TraitNetting();
		oracular = new TraitOracular();
		peaceEnergetic = new TraitPeaceEnergetic();
		piercing = new TraitPiercing();
		pyroelectric = new TraitPyroelectric();
		radiant = new TraitRadiant();
		rancher = new TraitRancher();
		recombining = new TraitRecombining();
		respecting = new TraitRespecting();
		repressing = new TraitRepressing();
		retrospective = new TraitRetrospective();
		shaking = new TraitShaking();
		soluble = new TraitSoluble();
		sovereign = new TraitSovereign();
		staminaFocusing = new TraitStaminaFocusing();
		starDashing = new TraitStarDashing();
		steamy = new TraitSteamy();
//		stonePhasing = new TraitStonePhasing();
		sunPower = new TraitSunPower();
		switching = new TraitSwitching();
		terrifying = new TraitTerrifying();
		thermalGathering = new TraitThermalGathering();
		thunderWaving = new TraitThunderWaving();
		tranquilizing = new TraitTranquilizing();
		torrential = new TraitTorrential();
		treasureDetecting = new TraitTreasureDetecting();
		turbulent = new TraitTurbulent();
		undeadCalling = new TraitUndeadCalling();
		unsettled = new TraitUnsettled();
		vibrating = new TraitVibrating();
		watery = new TraitWatery();
		wither_flowing = new TraitWitherFlowing();

		cracking = new TraitCracking();
		frost_jack = new TraitFrostJack();
		ponderous = new TraitPonderous();
		stellar_falling = new TraitStellarFalling();
	}

	public static void postInit()
	{
		aquatic.addItem(new ItemStack(Blocks.blockSeaGrass),1,1);
		inky.addItem(Items.inkySlime);
//		stonePhasing.addItem(Items.phasingGem);
	}
}
