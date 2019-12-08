package firok.tiths.common;

import firok.tiths.block.*;
import firok.tiths.util.Keys;
import firok.tiths.util.Reg;
import firok.tiths.util.RegOre;
import firok.tiths.world.GenTreeHura;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

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
	@Reg(Keys.oreRuby)
	public static final BlockOre oreRuby = new BlockOre(Items.ruby,1,1,1,6,10); // 红宝石矿
	@Reg(Keys.oreShell)
	public static final BlockOreShell oreShell = new BlockOreShell(Items.shell,3,6,1,4,6); // 散贝壳方块
	@Reg(Keys.oreCorundum)
	public static final BlockOre oreCorundum = new BlockOre(Items.corundum,1,1,1,2,4);

	// 矿块
	@Reg(Keys.blockCinnabar)
	public static final Block blockCinnabar = new BlockCompressed(); // 辰砂块
	@Reg(Keys.blockInkPowder)
	public static final Block blockInkPowder = new BlockCompressed(); // 墨粉块
	@Reg(Keys.blockStellarium)
	public static final Block blockStellarium = new BlockCompressed(); // 恒星金属块
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

	@Reg(Keys.blockStellariumObsidian)
	public static final Block blockStellariumObsidian = new Block(Material.ROCK); // 恒星黑曜石

	// 植物
	@Reg(Keys.saplingHura)
	public static final Block blockSaplingHura = new BlockSapling(GenTreeHura::generate,3);
	@Reg(Keys.logHura)
	public static final Block blockLogHura = new BlockWood();
	@Reg(Keys.leafHura)
	public static final Block blockLeafHura = new BlockLeaf(blockSaplingHura,60);
}
