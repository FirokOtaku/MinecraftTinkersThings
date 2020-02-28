package firok.tiths.common;

import firok.tiths.block.*;
import firok.tiths.util.GenOreWorld;
import firok.tiths.util.Reg;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import slimeknights.tconstruct.library.utils.HarvestLevels;

@SuppressWarnings("all")
public class Blocks
{
	// 算不上矿石的自然生成方块
	@Reg(value=Keys.blockMeteorolite,od={"meteorolite"})
//	@GenMeteoWorld
	@GenOreWorld(times=1,timeRate=0.12f,size=7,minY=40,maxY=180)
	public static final BlockOre blockMeteorolite = new BlockOre().setEpicOre(); // 陨石
	@Reg(Keys.blockFulgurite)
	public static final Block blockFulgurite = new BlockOre().setVeryRareOre(); // 闪电熔岩
	@Reg(Keys.blockCloud)
	public static final Block blockCloud = new BlockCloud(); // 云

	// 矿石
	@Reg(value=Keys.oreCinnabar,od={"oreCinnabar","oreQuicksilver","oreMercury"})
	@GenOreWorld(times=3,minY=30,maxY=70,size=10)
	public static final BlockOre oreCinnabar = new BlockOre(Items.cinnabar,1,3,2,3,5).setCommonOre(); // 辰砂矿石
	@Reg(Keys.oreInkPowder)
	@GenOreWorld(times=5,minY=60,maxY=120,size=8)
	public static final BlockOre oreInkPowder = new BlockOre(Items.inkPowder,2,4,2,2,7).setCommonOre(); // 墨粉矿石
	@Reg(Keys.oreImmersedSilver)
	@GenOreWorld(times=3,size=5,minY = 30,maxY = 80)
	public static final BlockOre oreImmersedSilver = new BlockOre().setRareOre(); // 沉银矿石
	@Reg(value=Keys.oreMithril,od={"oreMithril"})
	@GenOreWorld(times=2,timeRate = 0.4f,size=6,minY = 10,maxY = 50)
	public static final BlockOre oreMithril = new BlockOre().setEpicOre(); // 秘银矿石
	@Reg(value=Keys.oreAdamantine,od={"oreAdamantine"})
	@GenOreWorld(times=2,timeRate = 0.4f,size=6,minY = 10,maxY = 50)
	public static final BlockOre oreAdamantine = new BlockOre().setEpicOre(); // 精金矿石
	@Reg(Keys.oreBlackrock)
	@GenOreWorld(times=9,size=6,minY=10,maxY=150)
	public static final BlockOre oreBlackrock = new BlockOre(Items.blackrock,1,3,2,1,6); // 黑石矿石
	@Reg(Keys.oreInertWitherium)
	@GenOreWorld(times=2,size=5)
	public static final BlockOre oreInertWitherium = new BlockOre().setVeryRareOre(); // 惰性凋零矿
	@Reg(Keys.oreWitherium)
	public static final BlockOre oreWitherium = new BlockOre().setVeryRareOre(); // 凋零矿
	@Reg(value=Keys.oreRuby,od={"oreRuby"})
	@GenOreWorld(times = 5,timeRate = 0.4f,size = 4,minY = 10,maxY = 60)
	public static final BlockOre oreRuby = new BlockOre(Items.ruby,1,1,1,6,10).setVeryRareOre(); // 红宝石矿
//	@Reg(Keys.oreShell)
//	public static final BlockOreShell oreShell = new BlockOreShell(Items.shell,3,6,1,4,6); // 散贝壳方块
	@Reg(Keys.oreCorundum)
	@GenOreWorld(times = 5,timeRate = 0.4f,size = 4,minY = 10,maxY = 80)
	public static final BlockOre oreCorundum = new BlockOre(Items.corundum,1,1,1,2,4).setRareOre(); // 刚玉矿
	@Reg(Keys.oreNitre)
	@GenOreWorld(times=6,timeRate=0.6f,size=9,minY=35,maxY=120)
	public static final BlockOre oreNitre = new BlockOre(Items.nitre,1,3,1,3,5).setCommonOre(); // 硝石矿
	@Reg(Keys.orePyrophyllite)
	@GenOreWorld(times=2,minY=40,maxY=70,size=8)
	public static final BlockOre orePyrophyllite = new BlockOre(Items.pyrophyllite,1,2,1,3,5).setCommonOre(); // 叶蜡石矿
	@Reg(Keys.oreIcelandSpar)
	@GenOreWorld(times=3,size=6,minY = 40,maxY = 80)
	public static final BlockOre oreIcelandSpar = new BlockOre(Items.icelandSpar,1,2,1,3,5).setCommonOre(); // 冰洲石矿
	@Reg(value=Keys.oreSpinel,od={"oreSpinel"})
	@GenOreWorld(times=5,timeRate = 0.6f,size=4,minY=10,maxY=60)
	public static final BlockOre oreSpinel = new BlockOre(Items.spinel,1,1,1,3,5).setRareOre();
	@Reg(Keys.oreTalcum)
	@GenOreWorld(times=2,size=15,minY = 30,maxY = 90)
	public static final BlockOre oreTalcum = new BlockOre(Items.talcum,1,2,1,3,5).setCommonOre();
	@Reg(Keys.oreTourmaline)
	@GenOreWorld(times=3,timeRate=0.8f,size=8,minY=50,maxY=120)
	public static final BlockOre oreTourmaline = new BlockOre(Items.tourmaline, 1,2,1,2,4).setCommonOre();
	@Reg(Keys.oreRutile)
	@GenOreWorld(times=4,timeRate = 0.5f,size = 6,minY = 30,maxY = 60)
	public static final BlockOre oreTitanium = new BlockOre().setRareOre(); // 金红石矿
	@Reg(Keys.oreSunStone)
	@GenOreWorld(times=2,timeRate = 0.8f,size=4,minY = 50,maxY = 100)
	public static final BlockOre oreSunStone = new BlockOre(Items.sunStone,1,1,1,4,6).setRareOre(); // 日光石矿
	@Reg(Keys.oreMoonStone)
	@GenOreWorld(times=2,timeRate = 0.8f,size=4,minY = 50,maxY = 100)
	public static final BlockOre oreMoonStone = new BlockOre(Items.moonStone,1,1,1,4,6).setRareOre(); // 月光石矿
	@Reg(Keys.oreOpal)
	@GenOreWorld(times=2,timeRate = 0.8f,size=4,minY = 50,maxY = 100)
	public static final BlockOre oreOpal = new BlockOre(Items.opal,1,1,1,4,6).setRareOre();
	@Reg(Keys.oreTopaz)
	@GenOreWorld(times=2,timeRate = 0.8f,size=4,minY = 50,maxY = 100)
	public static final BlockOre oreTopaz = new BlockOre(Items.topaz,1,1,1,4,6).setRareOre();
	@Reg(Keys.oreTanzanite)
	@GenOreWorld(times=2,timeRate = 0.8f,size=4,minY = 50,maxY = 100)
	public static final BlockOre oreTanzanite = new BlockOre(Items.tanzanite,1,1,1,4,6).setRareOre();
	@Reg(Keys.oreCordierite)
	@GenOreWorld(times=2,timeRate = 0.8f,size=4,minY = 50,maxY = 100)
	public static final BlockOre oreCordierite = new BlockOre(Items.cordierite,1,1,1,4,6).setRareOre();
	@Reg(Keys.orePrehnite)
	@GenOreWorld(times=2,timeRate = 0.8f,size=4,minY = 50,maxY = 100)
	public static final BlockOre orePrehnite = new BlockOre(Items.prehnite,1,1,1,4,6).setRareOre();
	@Reg(Keys.oreProustite)
	@GenOreWorld(times=4,timeRate = 0.8f,size=4,minY = 50,maxY = 100)
	public static final BlockOre oreProustite = new BlockOre(Items.proustite,1,1,1,4,6).setRareOre();
	@Reg(Keys.oreLavaCrystal)
	public static final BlockOre oreLavaCrystal = new BlockOre(Items.lavaCrystal,1,1,1,3,6).setVeryRareOre();

	@Reg(Keys.orePolarium)
//	@GenMeteoWorld
	@GenOreWorld(times=1,timeRate=0.12f,size=5,minY=40,maxY=180)
	public static final BlockOre orePolarium = new BlockOre().setEpicOre(); // 勾陈矿
	@Reg(Keys.oreHalleium)
//	@GenMeteoWorld
	@GenOreWorld(times=1,timeRate=0.12f,size=5,minY=40,maxY=180)
	public static final BlockOre oreHalleium = new BlockOre().setEpicOre(); // 哈雷矿
	@Reg(Keys.oreAltairium)
//	@GenMeteoWorld
	@GenOreWorld(times=1,timeRate=0.12f,size=5,minY=40,maxY=180)
	public static final BlockOre oreAltairium = new BlockOre().setEpicOre(); // 河鼓矿
	@Reg(Keys.oreHothium)
//	@GenMeteoWorld
	@GenOreWorld(times=1,timeRate=0.12f,size=5,minY=40,maxY=180)
	public static final BlockOre oreHothium = new BlockOre().setEpicOre(); // 霍斯矿
	@Reg(Keys.oreTonium)
//	@GenMeteoWorld
	@GenOreWorld(times=1,timeRate=0.12f,size=5,minY=40,maxY=180)
	public static final BlockOre oreTonium = new BlockOre().setEpicOre(); // 钝金矿
	@Reg(Keys.oreStellarium)
	@GenOreWorld(times=1,timeRate=0.12f,size=5,minY=40,maxY=180)
	public static final BlockOre oreStellarium = new BlockOre().setEpicOre(); // 恒星金属矿
	@Reg(Keys.oreVibratingCrystal)
	@GenOreWorld(times=3,timeRate=0.9f,size=4,minY=30,maxY=60)
	public static final BlockOre oreVibratingCrystal = new BlockOre(Items.vibratingCrystal,1,3,1,5,7).setVeryRareOre(); // 振晶矿
	@Reg(Keys.oreSteamium)
	@GenOreWorld(times=2,timeRate = 1,size=6,minY=40,maxY=60)
	public static final BlockOre oreSteamium = new BlockOre(); // 气钢矿

	@Reg(Keys.oreTreeRoot)
	public static final Block oreTreeRoot = new BlockOre(Material.WOOD,Material.WOOD.getMaterialMapColor(),Items.treeRoot,1,3,1,2,4)
			{{setSoundType(SoundType.WOOD);}}
			.setHardness(2.0f); // 树根
	@Reg(Keys.oreBrokenBedrock)
	public static final Block oreBrokenBedrock = new BlockOre(Items.brokenBedrock,1,2,1,6,10)
			{{setHarvestLevel("pickaxe",HarvestLevels.COBALT);}}
			.setHardness(80f); // 破碎基岩

	// 矿块
	@Reg(Keys.blockCinnabar)
	public static final Block blockCinnabar = new BlockCompressed(); // 辰砂块
	@Reg(Keys.blockInkPowder)
	public static final Block blockInkPowder = new BlockCompressed(); // 墨粉块
	@Reg(Keys.blockStellarium)
	public static final Block blockStellarium = new BlockCompressed(); // 恒星金属块
	@Reg(Keys.blockRoyalAlloy)
	public static final Block blockRoyalAlloy = new BlockCompressed(); // 奢华合金块
	@Reg(Keys.blockIrisia)
	public static final Block blockIrisia = new BlockCompressed(); // 艾瑞希亚块
	@Reg(Keys.blockImmersedSilver)
	public static final Block blockImmersedSilver = new BlockCompressed(); // 沉银块
	@Reg(Keys.blockMithril)
	public static final Block blockMithril = new BlockCompressed(); // 秘银块
	@Reg(Keys.blockAdamantine)
	public static final Block blockAdamantine = new BlockCompressed(); // 精金块
	@Reg(Keys.blockBlackrock)
	public static final Block blockBlackrock = new BlockCompressed(Material.ROCK); // 黑石块
	@Reg(Keys.blockInertWitherium)
	public static final Block blockInertWitherium = new BlockCompressed(); // 惰性凋零块
	@Reg(Keys.blockWitherium)
	public static final Block blockWitherium = new BlockCompressed(); // 凋零块
	@Reg(Keys.blockCorundum)
	public static final Block blockCorumdum = new BlockCompressed(Material.ROCK); // 钢玉块
	@Reg(Keys.blockNitre)
	public static final Block blockNitre = new BlockCompressed(Material.ROCK); // 硝石块
	@Reg(Keys.blockPyrophyllite)
	public static final Block blockPyrophyllite = new BlockCompressed(Material.ROCK); // 叶蜡石块
	@Reg(Keys.blockIcelandSpar)
	public static final Block blockIcelandSpar = new BlockCompressed(Material.ROCK); // 冰洲石块
	@Reg(Keys.blockSpinel)
	public static final Block blockSpinel = new BlockCompressed(Material.ROCK); // 尖晶石块
	@Reg(Keys.blockTalcum)
	public static final Block blockTalcum = new BlockCompressed(Material.ROCK); // 滑石块
	@Reg(Keys.blockTourmaline)
	public static final Block blockTourmaline = new BlockCompressed(Material.ROCK); // 电气石块
	@Reg(Keys.blockRuby)
	public static final Block blockRuby = new BlockCompressed(Material.ROCK); // 红宝石块
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
	public static final Block blockCocoa = new BlockCompressed(Material.ROCK); // 可可块
	@Reg(Keys.blockSunStone)
	public static final Block blockSunStone = new BlockCompressed(Material.ROCK); // 日光石块
	@Reg(Keys.blockMoonStone)
	public static final Block blockMoonStone = new BlockCompressed(Material.ROCK);// 月光石块
	@Reg(Keys.blockOpal)
	public static final Block blockOpal = new BlockCompressed(Material.ROCK); // 欧珀块
	@Reg(Keys.blockTopaz)
	public static final Block blockTopaz = new BlockCompressed(Material.ROCK); // 托帕石块
	@Reg(Keys.blockTanzanite)
	public static final Block blockTanzanite = new BlockCompressed(Material.ROCK); // 坦桑石块
	@Reg(Keys.blockCordierite)
	public static final Block blockCordierite = new BlockCompressed(Material.ROCK); // 堇青石块
	@Reg(Keys.blockPrehnite)
	public static final Block blockPrehnite = new BlockCompressed(Material.ROCK); // 葡萄石块
	@Reg(Keys.blockProustite)
	public static final Block blockProustite = new BlockCompressed(Material.ROCK); // 硫砷银矿
	@Reg(Keys.blockOraclium)
	public static final Block blockOraclium = new BlockCompressed(); // 神谕块
	@Reg(Keys.blockVibratingCrystal)
	public static final Block blockVibratingCrystal = new BlockCompressed(Material.ROCK); // 振晶块
	@Reg(Keys.blockLavaCrystal)
	public static final Block blockLavaCrystal = new BlockCompressed(Material.ROCK); // 岩浆结晶块
	@Reg(Keys.blockSteamium)
	public static final Block blockSteamium = new BlockCompressed(); // 气钢块

	@Reg(Keys.blockStellariumObsidian)
	public static final Block blockStellariumObsidian = new Block(Material.ROCK); // 恒星黑曜石
	static
	{
		blockStellariumObsidian.setHardness(50F).setResistance(1200F).setHarvestLevel("pickaxe", HarvestLevels.OBSIDIAN);
	}
//	@Reg(Keys.blockBloodPumpkin)
//	public static final Block blockBloodPumpkin = new Block(Material.WOOD); // 血南瓜
//	@Reg(Keys.blockWeepingPumpkin)
//	public static final Block blockWeepingPumpkin = new Block(Material.WOOD); // 哭泣的南瓜
	@Reg(Keys.blockBloodSand)
	public static final Block blockBloodSand = new BlockBloodSand(); // 血沙
	@Reg(Keys.blockConsolidatedGlass)
	public static final Block blockConsolidatedGlass = new BlockCompressed(Material.GLASS); // 强化玻璃
	@Reg(Keys.blockCoagulatedBloodSand)
	public static final Block blockCoagulatedBloodSand = new BlockCompressed(Material.SAND); // 凝结血沙

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
