package firok.tiths.common;

import firok.tiths.potion.*;
import firok.tiths.util.Selectors;
import net.minecraft.potion.Potion;

// 状态效果的注册 reg_name是直接读取field名称
public class Potions
{
	public static Potion heavy = new PotionHeavy(); // 沉重
	public static Potion pestilential = new PotionPestilential(Selectors.livingBaseAlive); // 瘟疫
	public static Potion weakenedPestilential = new PotionPestilential(Selectors.mobAlive); // 弱化瘟疫
	public static Potion estrous = new PotionEstrous(); // 发情

//	public static Potion disappear = new PotionDisappear(); // 消失
}
