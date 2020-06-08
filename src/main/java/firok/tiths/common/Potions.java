package firok.tiths.common;

import firok.tiths.potion.*;
import firok.tiths.util.Selectors;

/*
关于实体属性修饰符operation

若该值为0：将X的增量设为Amount的值，
例如：{Amount:2,Operation:0}和{Amount:4,Operation:0}与基础值3的结果是：9 (3 + 2 + 4 = 9)。

若该值为1：将Y的增量设为X×Amount的值；
例如：修饰符属性：{Amount:2,Operation:1}和{Amount:4,Operation:1}与基础值3的结果是21（3 *（1 + 2 + 4）= 21）。

若该值为2：则计算Y = Y × (1 + Amount的值) (等同于将Y的增量设为 Y × Amount的值)Minecraft会先设 X = Base,
然后执行所有Operation的值为0的修饰符, 紧接着设 Y = X,
之后再执行所有Operation值为1的修饰符, 最后才执行所有Operation的值为2的修饰符。
例如：{Amount:2,Operation:2}和{Amount:4,Operation:2}与3的基础值结果是45（3 *（1 + 2）*（1 + 4）= 45）。[3]

数学行为如下：Operation0：按数量增加X，操作1：增加Y×X *数量，Operation2：Y = Y *（1 +数量）（相当于增量Y减去Y *数量）。
游戏首先设置X = Base，然后执行所有Operation 0修饰符，然后设置Y = X，然后执行所有Operation 1修饰符，最后执行所有Operation 2修饰符。
*/

// 状态效果的注册 reg_name是直接读取field名称
public final class Potions
{
	private Potions() {}

	public static BasePotion heavy = new PotionHeavy(); // 沉重
	public static BasePotion pestilential = new PotionPestilential(Selectors.livingBaseAlive); // 瘟疫
	public static BasePotion weakened_pestilential = new PotionPestilential(Selectors.mobAlive); // 弱化瘟疫
	public static BasePotion estrous = new PotionEstrous(); // 发情
	public static BasePotion avatar = new PotionAvatar(); // 天神下凡
	public static BasePotion rooted = new PotionRooted(); // 缠绕
	public static BasePotion armor_softened = new PotionArmorSoftened(); // 护甲软化
	public static BasePotion void_infected = new PotionVoidInfected(); // 虚空侵蚀
	public static BasePotion acid_wetted = new PotionAcidWetted(); // 酸蚀
	public static BasePotion hidden_hyper = new PotionHiddenHyper(); // 特性 - 振奋 (隐藏状态)
	public static BasePotion lionheart = new PotionLionheart(); // 狮心
	public static BasePotion icy = new PotionIcy(); // 清凉
	public static BasePotion hidden_soluble = new PotionHiddenSoluble(); // 特性 - 可溶 (隐藏状态)
	public static BasePotion fear = new PotionFear(); // 恐惧
	public static BasePotion hidden_buoyant = new PotionHiddenBuoyant(); // 特性 - 浮力 (隐藏状态)
	public static BasePotion bubbling = new PotionBubbling(); // 发泡
	public static BasePotion turbulent = new PotionTurbulent(); // 乱流
	public static BasePotion forcibleFocused = new PotionForcibleFocused(); // 强制专注
	public static BasePotion eddying = new PotionEddying(); // 旋流

}
