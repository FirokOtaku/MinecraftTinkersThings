package firok.mtim.common;

import firok.mtim.block.BlockCompressed;
import firok.mtim.block.BlockOre;
import firok.mtim.util.Keys;
import firok.mtim.util.Reg;
import firok.mtim.util.RegOre;
import net.minecraft.block.Block;

@SuppressWarnings("all")
public class Blocks
{
	// 矿石
	@Reg(Keys.oreCinnabar)
	@RegOre(
			minWorldAmount = 8,maxWorldAmount = 15
	)
	public static final BlockOre oreCinnabar = new BlockOre(Items.cinnabar,1,5,2,3,5); // 辰砂矿石
	@Reg(Keys.oreInkPowder)
	@RegOre(
			minWorldAmount = 8,maxWorldAmount = 15
	)
	public static final BlockOre oreInkPowder = new BlockOre(Items.inkPowder,4,8,2,2,7); // 墨粉矿石
	@Reg(Keys.oreImmersedSilver)
	@RegOre(
			minWorldAmount = 8,maxWorldAmount = 20
	)
	public static final BlockOre oreImmersedSilver = new BlockOre(); // 沉银矿石
	@Reg(Keys.oreMithril)
	public static final BlockOre oreMithril = new BlockOre(); // 秘银矿石
	@Reg(Keys.oreAdamantine)
	public static final BlockOre oreAdamantine = new BlockOre(); // 精金矿石
	@Reg(Keys.oreBlackrock)
	public static final BlockOre oreBlackrock = new BlockOre(Items.blackrock,2,4,2,1,6); // 黑石矿石
	@Reg(Keys.oreInertWitherium)
	public static final BlockOre oreInertWitherium = new BlockOre(); // 惰性凋零矿
	@Reg(Keys.oreWitherium)
	public static final BlockOre oreWitherium = new BlockOre(); // 凋零矿

	// 矿块
	@Reg(Keys.blockCinnabar)
	public static final Block blockCinnabar = new BlockCompressed(); // 辰砂块
	@Reg(Keys.blockInkPowder)
	public static final Block blockInkPowder = new BlockCompressed(); // 墨粉块
	@Reg(Keys.blockStellar)
	public static final Block blockStellar = new BlockCompressed(); // 恒星金属块
	@Reg(Keys.blockImmersedSilver)
	public static final Block blockImmersedSilver = new BlockCompressed(); // 沉银块
	@Reg(Keys.blockMithril)
	public static final Block blockMithril = new BlockCompressed(); // 秘银块
	@Reg(Keys.blockAdamantine)
	public static final Block blockAdamantine = new BlockCompressed(); // 精金块
	@Reg(Keys.blockBlackrock)
	public static final Block blockBlackrock = new BlockCompressed(); // 黑石块
	@Reg(Keys.blockInertWitherium)
	public static final Block blockInertWitherium = new BlockCompressed(); // 惰性凋零块
	@Reg(Keys.blockWitherium)
	public static final Block blockWitherium = new BlockCompressed(); // 凋零块

}
