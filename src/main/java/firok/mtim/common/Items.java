package firok.mtim.common;

import firok.mtim.util.Reg;
import net.minecraft.item.Item;

@SuppressWarnings("all")
public class Items
{
	// 原材料 - 矿物

	@Reg(tn="cinnabar",un="cinnabar")
	public static final Item cinnabar; // 辰砂

	@Reg(tn="ink_powder",un="ink_powder")
	public static final Item inkPowder; // 墨粉
	static
	{
		cinnabar=new Item();
		inkPowder=new Item();
	}

	// 原材料 - 矿
	@Reg(tn="sky_crystal",un="sky_crystal")
	public static final Item skyCrystal; // 天空水晶
	@Reg(tn="storm_crystal",un="storm_crystal")
	public static final Item stormCrystal; // 风暴水晶
	@Reg(tn="phantom_crystal",un="phantom_crystal")
	public static final Item phantomCrystal; // 异象水晶
	static
	{
		skyCrystal=new Item();
		stormCrystal=new Item();
		phantomCrystal=new Item();
	}

	// 原材料 - 金属锭
	@Reg(tn="ingot_stellar",un="ingot_stellar")
	public static final Item ingotStellar; // 恒星金属锭
	@Reg(tn="ingot_royal_alloy",un="ingot_royal_alloy")
	public static final Item ingotRoyalAlloy; // 皇家合金锭
	static
	{
		ingotStellar=new Item();
		ingotRoyalAlloy=new Item();
	}
}
