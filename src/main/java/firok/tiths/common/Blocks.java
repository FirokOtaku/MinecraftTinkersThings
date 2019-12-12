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
	public static final BlockOre oreCorundum = new BlockOre(Items.corundum,1,1,1,2,4); // 刚玉矿
	@Reg(Keys.oreRutile)
	public static final BlockOre oreTitanium = new BlockOre(); // 金红石矿
	@Reg(Keys.orePolarium)
	public static final BlockOre orePolarium = new BlockOre(); // 勾陈矿
	@Reg(Keys.oreHalleium)
	public static final BlockOre oreHalleium = new BlockOre(); // 哈雷矿
	@Reg(Keys.oreAltairium)
	public static final BlockOre oreAltairium = new BlockOre(); // 河鼓矿

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
	@Reg(Keys.blockCorundum)
	public static final Block blockCorumdum = new BlockCompressed(); // 钢玉块
	@Reg(Keys.blockRuby)
	public static final Block blockRuby = new BlockCompressed(); // 红宝石块
	@Reg(Keys.blockTitanium)
	public static final Block blockTitanium = new BlockCompressed(); // 钛块
	@Reg(Keys.blockPolarium)
	public static final Block blockPolarium = new BlockCompressed(); // 勾陈块
	@Reg(Keys.blockHalleium)
	public static final Block blockHalleium = new BlockCompressed(); // 哈雷块
	@Reg(Keys.blockAltairium)
	public static final Block blockAltairium = new BlockCompressed(); // 河鼓块
	@Reg(Keys.blockCocoa)
	public static final Block blockCocoa = new BlockCompressed(); // 可可块

	@Reg(Keys.blockStellariumObsidian)
	public static final Block blockStellariumObsidian = new Block(Material.ROCK); // 恒星黑曜石
	@Reg(Keys.blockBloodPumpkin)
	public static final Block blockBloodPumpkin = new Block(Material.WOOD); // 血南瓜
	@Reg(Keys.blockWeepingPumpkin)
	public static final Block blockWeepingPumpkin = new Block(Material.WOOD); // 哭泣的南瓜

	// 植物
	@Reg(Keys.saplingHura)
	public static final Block blockSaplingHura = new BlockSapling(GenTreeHura::generate,3);
	@Reg(Keys.logHura)
	public static final Block blockLogHura = new BlockWood();
	@Reg(Keys.leafHura)
	public static final Block blockLeafHura = new BlockLeaf(blockSaplingHura,60);
}
