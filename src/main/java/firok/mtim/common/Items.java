package firok.mtim.common;

import firok.mtim.util.Keys;
import firok.mtim.util.Reg;
import net.minecraft.item.Item;

import static firok.mtim.util.Keys.*;

@SuppressWarnings("all")
public class Items
{
	// 原材料 - 怪物掉落
	@Reg(nameSpiderLeg)
	public static final Item spiderLeg = new Item(); // 蛛腿
	@Reg(nameHardSpiderLeg)
	public static final Item hardSpiderLeg = new Item(); // 坚硬蛛腿

	// 原材料 - 各种
	@Reg(nameCinnabar)
	public static final Item cinnabar = new Item(); // 辰砂
	@Reg(nameInkPowder)
	public static final Item inkPowder = new Item(); // 墨粉
	@Reg(nameBrokenIce)
	public static final Item brokenIce=new Item(); // 碎冰

	// 原材料 - 矿
	@Reg(nameSkyCrystal)
	public static final Item skyCrystal = new Item(); // 天空水晶
	@Reg(nameStormCrystal)
	public static final Item stormCrystal = new Item(); // 风暴水晶
	@Reg(namePhantomCrystal)
	public static final Item phantomCrystal = new Item(); // 异象水晶
	@Reg(nameAntiGravCrystal)
	public static final Item antiGravCrystal = new Item(); // 反重力水晶
	@Reg(nameBlackrock)
	public static final Item blackrock = new Item(); // 黑石

	// 原材料 - 金属锭
	@Reg(Keys.ingotStellarium)
	public static final Item ingotStellarium = new Item(); // 恒星金属锭
	@Reg(Keys.ingotRoyalAlloy)
	public static final Item ingotRoyalAlloy = new Item(); // 皇家合金锭
	@Reg(Keys.ingotImmersedSilver)
	public static final Item ingotImmersedSilver = new Item(); // 沉银锭
	@Reg(Keys.ingotMithril)
	public static final Item ingotMithril = new Item(); // 秘银锭
	@Reg(Keys.ingotAdamantine)
	public static final Item ingotAdamantine = new Item(); // 精金锭
	@Reg(Keys.ingotInertWitherium)
	public static final Item ingotInertWitherium = new Item(); // 惰性凋零锭
	@Reg(Keys.ingotWitherium)
	public static final Item ingotWitherium = new Item(); // 凋零锭
}
