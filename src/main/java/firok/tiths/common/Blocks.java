package firok.tiths.common;

import firok.tiths.block.*;
import firok.tiths.block.logic.BlockLogicSearing;
import firok.tiths.indev.BlockOreShell;
import firok.tiths.util.GenMeteoWorld;
import firok.tiths.util.GenOreWorld;
import firok.tiths.util.Reg;
import firok.tiths.world.GenTreeHura;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.material.Material;

@SuppressWarnings("all")
public class Blocks
{
	// 算不上矿石的自然生成方块
	@Reg(Keys.blockMeteorolite)
	@GenMeteoWorld
	public static final BlockOre blockMeteorolite = new BlockOre(); // 陨石

	// 矿石
	@Reg(Keys.oreCinnabar)
	@GenOreWorld(times=4,minY=30,maxY=70,size=10)
	public static final BlockOre oreCinnabar = new BlockOre(Items.cinnabar,1,5,2,3,5); // 辰砂矿石
	@Reg(Keys.oreInkPowder)
	@GenOreWorld(times=6,minY=60,maxY=120,size=8)
	public static final BlockOre oreInkPowder = new BlockOre(Items.inkPowder,4,8,2,2,7); // 墨粉矿石
	@Reg(Keys.oreImmersedSilver)
	@GenOreWorld(times=4,size=5,minY = 30,maxY = 80)
	public static final BlockOre oreImmersedSilver = new BlockOre(); // 沉银矿石
	@Reg(Keys.oreMithril)
	@GenOreWorld(times=3,timeRate = 0.5f,size=6,minY = 10,maxY = 50)
	public static final BlockOre oreMithril = new BlockOre(); // 秘银矿石
	@Reg(Keys.oreAdamantine)
	@GenOreWorld(times=3,timeRate = 0.5f,size=6,minY = 10,maxY = 50)
	public static final BlockOre oreAdamantine = new BlockOre(); // 精金矿石
	@Reg(Keys.oreBlackrock)
	@GenOreWorld(times=9,size=6,minY=10,maxY=150)
	public static final BlockOre oreBlackrock = new BlockOre(Items.blackrock,2,4,2,1,6); // 黑石矿石
	@Reg(Keys.oreInertWitherium)
	@GenOreWorld(times=3,size=5)
	public static final BlockOre oreInertWitherium = new BlockOre(); // 惰性凋零矿
	@Reg(Keys.oreWitherium)
	public static final BlockOre oreWitherium = new BlockOre(); // 凋零矿
	@Reg(Keys.oreRuby)
	@GenOreWorld(times = 5,timeRate = 0.6f,size = 4,minY = 10,maxY = 60)
	public static final BlockOre oreRuby = new BlockOre(Items.ruby,1,1,1,6,10); // 红宝石矿
//	@Reg(Keys.oreShell)
//	public static final BlockOreShell oreShell = new BlockOreShell(Items.shell,3,6,1,4,6); // 散贝壳方块
	@Reg(Keys.oreCorundum)
	@GenOreWorld(times = 5,timeRate = 0.8f,size = 4,minY = 10,maxY = 80)
	public static final BlockOre oreCorundum = new BlockOre(Items.corundum,1,1,1,2,4); // 刚玉矿
	@Reg(Keys.oreNitre)
	@GenOreWorld(times=6,timeRate=0.6f,size=9,minY=35,maxY=120)
	public static final BlockOre oreNitre = new BlockOre(Items.nitre,3,5,1,3,5); // 硝石矿
	@Reg(Keys.orePyrophyllite)
	@GenOreWorld(times=3,minY=40,maxY=70,size=8)
	public static final BlockOre orePyrophyllite = new BlockOre(Items.pyrophyllite,3,5,1,3,5); // 叶蜡石矿
	@Reg(Keys.oreIcelandSpar)
	@GenOreWorld(times=4,size=6,minY = 40,maxY = 80)
	public static final BlockOre oreIcelandSpar = new BlockOre(Items.icelandSpar,3,5,1,3,5); // 冰洲石矿
	@Reg(Keys.oreSpinel)
	@GenOreWorld(times=5,timeRate = 0.6f,size=4,minY=10,maxY=60)
	public static final BlockOre oreSpinel = new BlockOre(Items.spinel,1,1,1,3,5);
	@Reg(Keys.oreTalcum)
	@GenOreWorld(times=2,size=15,minY = 30,maxY = 90)
	public static final BlockOre oreTalcum = new BlockOre(Items.talcum,3,5,1,3,5);
	@Reg(Keys.oreTourmaline)
	@GenOreWorld(times=3,timeRate=0.8f,size=8,minY=50,maxY=120)
	public static final BlockOre oreTourmaline = new BlockOre(Items.tourmaline, 2,4,1,2,4);
	@Reg(Keys.oreRutile)
	@GenOreWorld(times=4,timeRate = 0.5f,size = 6,minY = 30,maxY = 60)
	public static final BlockOre oreTitanium = new BlockOre(); // 金红石矿

	@Reg(Keys.orePolarium)
	@GenMeteoWorld
	public static final BlockOre orePolarium = new BlockOre(); // 勾陈矿
	@Reg(Keys.oreHalleium)
	@GenMeteoWorld
	public static final BlockOre oreHalleium = new BlockOre(); // 哈雷矿
	@Reg(Keys.oreAltairium)
	@GenMeteoWorld
	public static final BlockOre oreAltairium = new BlockOre(); // 河鼓矿
	@Reg(Keys.oreHothium)
	@GenMeteoWorld
	public static final BlockOre oreHothium = new BlockOre(); // 霍斯矿
	@Reg(Keys.oreTonium)
	@GenMeteoWorld
	public static final BlockOre oreTonium = new BlockOre(); // 钝金矿

	// 矿块
	@Reg(Keys.blockCinnabar)
	public static final Block blockCinnabar = new BlockCompressed(); // 辰砂块
	@Reg(Keys.blockInkPowder)
	public static final Block blockInkPowder = new BlockCompressed(); // 墨粉块
	@Reg(Keys.blockStellarium)
	public static final Block blockStellarium = new BlockCompressed(); // 恒星金属块
	@Reg(Keys.blockRoyalAlloy)
	public static final Block blockRoyalAlloy = new BlockCompressed(); // 奢华合金块
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
	@Reg(Keys.blockNitre)
	public static final Block blockNitre = new BlockCompressed(); // 硝石块
	@Reg(Keys.blockPyrophyllite)
	public static final Block blockPyrophyllite = new BlockCompressed(); // 叶蜡石块
	@Reg(Keys.blockIcelandSpar)
	public static final Block blockIcelandSpar = new BlockCompressed(); // 冰洲石块
	@Reg(Keys.blockSpinel)
	public static final Block blockSpinel = new BlockCompressed(); // 尖晶石块
	@Reg(Keys.blockTalcum)
	public static final Block blockTalcum = new BlockCompressed(); // 滑石块
	@Reg(Keys.blockTourmaline)
	public static final Block blockTourmaline = new BlockCompressed(); // 电气石块
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
	@Reg(Keys.blockHothium)
	public static final Block blockHothium = new BlockCompressed(); // 霍斯块
	@Reg(Keys.blockTonium)
	public static final Block blockTonium = new BlockCompressed(); // 钝金块
	@Reg(Keys.blockCocoa)
	public static final Block blockCocoa = new BlockCompressed(); // 可可块

	@Reg(Keys.blockStellariumObsidian)
	public static final Block blockStellariumObsidian = new Block(Material.ROCK); // 恒星黑曜石
//	@Reg(Keys.blockBloodPumpkin)
//	public static final Block blockBloodPumpkin = new Block(Material.WOOD); // 血南瓜
//	@Reg(Keys.blockWeepingPumpkin)
//	public static final Block blockWeepingPumpkin = new Block(Material.WOOD); // 哭泣的南瓜
	@Reg(Keys.blockBloodSand)
	public static final Block blockBloodSand = new BlockBloodSand(); // 血沙
	@Reg(Keys.blockConsolidatedGlass)
	public static final Block blockConsolidatedGlass = new BlockCompressed(); // 强化玻璃
	@Reg(Keys.blockCoagulatedBloodSand)
	public static final Block blockCoagulatedBloodSand = new BlockCompressed(); // 凝结血沙

	// 一些装饰用方块
//	@Reg(Keys.blockRoyalEnchantmentTable)
//	public static final Block blockRoyalEnchantmentTable = new BlockEnchantmentTable(){}; // todo

	// 植物
//	@Reg(Keys.saplingHura)
//	public static final Block blockSaplingHura = new BlockSapling(GenTreeHura::generate,3);
//	@Reg(Keys.logHura)
//	public static final Block blockLogHura = new BlockWood();
//	@Reg(Keys.leafHura)
//	public static final Block blockLeafHura = new BlockLeaf(blockSaplingHura,60);

//	@Reg(Keys.saplingBlood)
//	public static final Block blockSaplingBlood = new BlockSapling();
//	@Reg(Keys.logBlood)
//	public static final Block blockLogBlood = new BlockWood();
//	@Reg(Keys.leafBlood)
//	public static final Block blockLeafBlood = new BlockLeaf(blockSaplingBlood,50);

	// 逻辑方块
//	@Reg(Keys.blockLogicSearing)
//	public static final Block logicSearing = new BlockLogicSearing(); // 逻辑 - 焦黑窖
}
