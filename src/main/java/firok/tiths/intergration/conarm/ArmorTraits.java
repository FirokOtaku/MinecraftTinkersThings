package firok.tiths.intergration.conarm;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.intergration.conarm.traits.*;

// ABCDE FGHIJ KLMNO PQRST UVWXY Z
public class ArmorTraits
{
	public static AbstractArmorTrait carbonizing; // 碳化
	public static AbstractArmorTrait chemicalInstable; // 化学不稳定
	public static AbstractArmorTrait dichroic; // 二色性
	public static AbstractArmorTrait endothermic; // 吸热
	public static AbstractArmorTrait extremeFreezing; // 极寒
	public static AbstractArmorTrait fading; // 影淡
	public static AbstractArmorTrait gorgeous; // 斑斓
	public static AbstractArmorTrait hemolytic; // 溶血
	public static AbstractArmorTrait hiding; // 匿踪
	public static AbstractArmorTrait hyper; // 振奋
	public static AbstractArmorTrait icy; // 冰凉
	public static AbstractArmorTrait lifting; // 扬升
	public static AbstractArmorTrait luxurious; // 奢华
	public static AbstractArmorTrait moonPower; // 月之力量
	public static AbstractArmorTrait overHeavy; // 沉重
	public static AbstractArmorTrait radiant; // 辉耀
	public static AbstractArmorTrait sliding; // 软滑
	public static AbstractArmorTrait sunPower; // 日之力量
	public static AbstractArmorTrait thresholdLimiting; // 阈限

	public static void init()
	{
		carbonizing=new TraitArmorCarbonizing();
		chemicalInstable=new TraitArmorChemicalInstable();
		dichroic=new TraitArmorDichroic();
		endothermic=new TraitArmorEndothermic();
		extremeFreezing=new TraitArmorExtremeFreezing();
		fading=new TraitArmorFading();
		gorgeous=new TraitArmorGorgeous();
		hemolytic=new TraitArmorHemolytic();
		hiding=new TraitArmorHiding();
		hyper=new TraitArmorHyper();
		icy=new TraitArmorIcy();
		lifting=new TraitArmorLifting();
		luxurious=new TraitArmorLuxurious();
		moonPower=new TraitArmorMoonPower();
		overHeavy=new TraitArmorOverHeavy();
		radiant=new TraitArmorRadiant();
		sliding=new TraitArmorSliding();
		sunPower=new TraitArmorSunPower();
		thresholdLimiting=new TraitArmorThresholdLimiting();
	}
	public static void postinit()
	{
		;
	}
}
