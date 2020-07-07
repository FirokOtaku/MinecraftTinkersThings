package firok.tiths.intergration.conarm;

import c4.conarm.lib.traits.AbstractArmorTrait;
import c4.conarm.lib.utils.RecipeMatchHolder;
import firok.tiths.common.Items;
import firok.tiths.intergration.conarm.traits.*;
import firok.tiths.util.reg.Indev;

// ABCDE FGHIJ KLMNO PQRST UVWXY Z
public class ArmorTraits
{
	public static AbstractArmorTrait arsenicPoisonous; // 砷毒
	public static AbstractArmorTrait buoyant; // 浮力
	public static AbstractArmorTrait carbonizing; // 碳化
	public static AbstractArmorTrait cavious; // 洞藏
	public static AbstractArmorTrait chemicalInstable; // 化学不稳定
	@Indev public static AbstractArmorTrait deadening; // 吸音
	public static AbstractArmorTrait deepParasitic; // 深触寄生
	public static AbstractArmorTrait devouring; // 吞噬
	public static AbstractArmorTrait dichroic; // 二色性
	public static AbstractArmorTrait diffuseReflecting; // 漫反射
	public static AbstractArmorTrait endothermic; // 吸热
	public static AbstractArmorTrait extremeFreezing; // 极寒
	public static AbstractArmorTrait fading; // 影淡
	public static AbstractArmorTrait gorgeous; // 斑斓
	public static AbstractArmorTrait hemolytic; // 溶血
	public static AbstractArmorTrait hydrating; // 水合
	public static AbstractArmorTrait hiding; // 匿踪
	public static AbstractArmorTrait hyper; // 振奋
	public static AbstractArmorTrait icy; // 冰凉
	public static AbstractArmorTrait leavesHiding; // 蔽叶
	public static AbstractArmorTrait lifeInspiring; // 生命激发
	public static AbstractArmorTrait lifting; // 扬升
	public static AbstractArmorTrait lingering; // 缭绕
	public static AbstractArmorTrait luxurious; // 奢华
	public static AbstractArmorTrait meshing; // 筛网
	public static AbstractArmorTrait moonPower; // 月之力量
	public static AbstractArmorTrait overHeavy; // 沉重
	public static AbstractArmorTrait panicking; // 威慑
	public static AbstractArmorTrait photosynthetic; // 光合作用
	public static AbstractArmorTrait quickFreezing; // 速冻
	public static AbstractArmorTrait radiant; // 辉耀
	public static AbstractArmorTrait sliding; // 软滑
	public static AbstractArmorTrait smooth; // 光滑
	public static AbstractArmorTrait soluble; // 可溶
	public static AbstractArmorTrait staminaFocusing; // 精力汇聚
	public static AbstractArmorTrait starDashing; // 星绽
	public static AbstractArmorTrait sunPower; // 日之力量
	public static AbstractArmorTrait surging; // 浪涌
	public static AbstractArmorTrait switching; // 换位
	public static AbstractArmorTrait thermalGathering; // 热力聚集
	public static AbstractArmorTrait thresholdLimiting; // 阈限
	public static AbstractArmorTrait tubeUpgraded; // 导管升级
	public static AbstractArmorTrait turbulent; // 扰动
	public static AbstractArmorTrait vibrating; // 震动
	public static AbstractArmorTrait warmSoft1; // 温软1
	public static AbstractArmorTrait warmSoft2; // 温软2
	public static AbstractArmorTrait warmSoft3; // 温软3
	public static AbstractArmorTrait widening; // 广域化
	public static AbstractArmorTrait witherFlowing; // 凋零流动

	public static void init()
	{
		arsenicPoisonous=new TraitArmorArsenicPoisonous();
		buoyant=new TraitArmorBuoyant();
		carbonizing=new TraitArmorCarbonizing();
		cavious=new TraitArmorCavious();
		chemicalInstable=new TraitArmorChemicalInstable();
//		deadening=new TraitArmorDeadening();
		deepParasitic =new TraitArmorDeepParasitic();
		devouring = new TraitArmorDevouring();
		dichroic=new TraitArmorDichroic();
		diffuseReflecting=new TraitArmorDiffuseReflecting();
		endothermic=new TraitArmorEndothermic();
		extremeFreezing=new TraitArmorExtremeFreezing();
		fading=new TraitArmorFading();
		gorgeous=new TraitArmorGorgeous();
		hemolytic=new TraitArmorHemolytic();
		hydrating=new TraitArmorHydrating();
		hiding=new TraitArmorHiding();
		hyper=new TraitArmorHyper();
		icy=new TraitArmorIcy();
		leavesHiding=new TraitArmorLeavesHiding();
		lifeInspiring=new TraitArmorLifeInspiring();
		lifting=new TraitArmorLifting();
		lingering=new TraitArmorLingering();
		luxurious=new TraitArmorLuxurious();
		meshing=new TraitArmorMeshing();
		moonPower=new TraitArmorMoonPower();
		overHeavy=new TraitArmorOverHeavy();
//		panicking=new TraitArmorPanicking();
		photosynthetic=new TraitArmorPhotosynthetic();
		quickFreezing=new TraitArmorQuickFreezing();
		radiant=new TraitArmorRadiant();
		sliding=new TraitArmorSliding();
		smooth=new TraitArmorSmooth();
		soluble=new TraitArmorSoluble();
		staminaFocusing=new TraitArmorStaminaFocusing();
		starDashing=new TraitArmorStarDashing();
		sunPower=new TraitArmorSunPower();
		surging=new TraitArmorSurging();
		switching=new TraitArmorSwitching();
		thermalGathering=new TraitArmorThermalGathering();
		thresholdLimiting=new TraitArmorThresholdLimiting();
		tubeUpgraded=new TraitArmorTubeUpgraded();
		turbulent=new TraitArmorTurbulent();
		vibrating=new TraitArmorVibrating();
		warmSoft1=new TraitArmorWarmSoft(1);
		warmSoft2=new TraitArmorWarmSoft(2);
		warmSoft3=new TraitArmorWarmSoft(3);
		widening=new TraitArmorWidening();
		witherFlowing=new TraitArmorWitherFlowing();
	}
	public static void postinit()
	{
		RecipeMatchHolder.addItem(buoyant, Items.buoy);

		RecipeMatchHolder.addItem(meshing, Items.net);

		RecipeMatchHolder.addItem(deadening, net.minecraft.init.Items.WOODEN_SWORD);

		RecipeMatchHolder.addItem(tubeUpgraded, Items.tube);

		RecipeMatchHolder.addItem(photosynthetic, Items.chloroplastDressing);
	}
}
