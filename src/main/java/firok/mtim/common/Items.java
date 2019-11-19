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
	public static final Item spiderLeg; // 蛛腿
	@Reg(nameHardSpiderLeg)
	public static final Item hardSpiderLeg; // 坚硬蛛腿
	static
	{
		spiderLeg=new Item();
		hardSpiderLeg=new Item();
	}

	// 原材料 - 各种
	@Reg(nameCinnabar)
	public static final Item cinnabar; // 辰砂
	@Reg(nameInkPowder)
	public static final Item inkPowder; // 墨粉
	static
	{
		cinnabar=new Item();
		inkPowder=new Item();
	}

	// 原材料 - 矿
	@Reg(nameSkyCrystal)
	public static final Item skyCrystal; // 天空水晶
	@Reg(nameStormCrystal)
	public static final Item stormCrystal; // 风暴水晶
	@Reg(namePhantomCrystal)
	public static final Item phantomCrystal; // 异象水晶
	@Reg(nameAntiGravCrystal)
	public static final Item antiGravCrystal; // 反重力水晶
	static
	{
		skyCrystal=new Item();
		stormCrystal=new Item();
		phantomCrystal=new Item();
		antiGravCrystal=new Item();
	}

	// 原材料 - 金属锭
	@Reg(Keys.ingotStellar)
	public static final Item ingotStellar; // 恒星金属锭
	@Reg(Keys.ingotRoyalAlloy)
	public static final Item ingotRoyalAlloy; // 皇家合金锭
	static
	{
		ingotStellar=new Item();
		ingotRoyalAlloy=new Item();
	}
}
