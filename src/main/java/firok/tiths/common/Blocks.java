package firok.tiths.common;

import firok.tiths.block.*;
import firok.tiths.util.reg.GenOre;
import firok.tiths.util.reg.Reg;
import firok.tiths.world.Strategy;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import slimeknights.tconstruct.library.utils.HarvestLevels;

@SuppressWarnings("all")
public final class Blocks
{
	private Blocks() {}

	@Reg(Keys.blockAirPump)
	public static final Block blockAirPump = new BlockCompressed(); // 气泵
	@Reg(Keys.blockAltairium)
	public static final Block blockAltairium = new BlockCompressed().setBeaconBase(); // 河鼓块
	@Reg(Keys.blockBrumeJade)
	public static final Block blockBrumeJade = new BlockCompressed(Material.ROCK).setBeaconBase(); // 云玉块
	@Reg(Keys.blockBlackrock)
	public static final Block blockBlackrock = new BlockCompressed(Material.ROCK); // 黑石块
	//	@Reg(Keys.blockBloodPumpkin)
	public static final Block blockBloodPumpkin = new BlockBloodSand(); // 血南瓜
	@Reg(Keys.blockBloodSand)
	public static final Block blockBloodSand = new BlockBloodSand(); // 血沙
	@Reg(Keys.blockBloodyChiseledQuartz)
	public static final Block blockBloodyChiseledQuartz = new BlockBloodyChiseledQuartzBlock(); // 錾制血腥石英块
	@Reg(Keys.blockBloodyPillarQuartz)
	public static final Block blockBloodyPillarQuartz = new BlockCompressed(); // 錾制血腥竖纹石英块
	@Reg(Keys.blockBloodyQuartz)
	public static final Block blockBloodyQuartz = new BlockCompressed(); // 血腥石英块
	@Reg(Keys.prefBlock + Keys.nameBuoy)
	public static final Block blockBuoy = new BlockBuoy(); // 浮筒
	@Reg(Keys.prefBlock + "buoyant_light")
	public static final Block blockBuoyantLight = new BlockBuoyantLight(); // 浮灯
	@Reg(Keys.blockChannel)
	public static final Block blockChannel = new BlockChannel(); // 通道
	@Reg(Keys.blockChannel+"_placer")
	public static final Block blockChannelPlacer = new BlockChannelPlacer(); // 通道放置器
	@Reg(Keys.blockChloroplast)
	public static final Block blockChloroplast = new BlockCompressed().setBeaconBase(); // 叶绿块
	// 矿块
	@Reg(Keys.blockCinnabar)
	public static final Block blockCinnabar = new BlockCompressed(); // 辰砂块
	@Reg(Keys.blockCloud)
	public static final Block blockCloud = new BlockCloud(); // 云
	@Reg(Keys.blockCloud+"_brick")
	public static final Block blockCloudBrick = new BlockCloudBrick(); // 云砖
	@Reg(Keys.blockCoagulatedBloodSand)
	public static final Block blockCoagulatedBloodSand = new BlockCompressed(Material.SAND); // 凝结血沙
	@Reg(Keys.blockCocoa)
	public static final Block blockCocoa = new BlockCompressed(Material.ROCK); // 可可块
	@Reg(Keys.blockColorfulGlass)
	public static final Block blockColorfulGlass = new BlockCompressed(Material.GLASS).enableTransparent().setHardness(0.2f); // 彩色玻璃
	@Reg(Keys.blockConsolidatedGlass)
	public static final Block blockConsolidatedGlass = new BlockCompressed(Material.GLASS)
	{
		{
			this.setResistance(200);
		}
	}.enableTransparent(); // 强化玻璃
	@Reg(Keys.blockCordierite)
	public static final Block blockCordierite = new BlockCompressed(Material.ROCK).setBeaconBase(); // 堇青石块
	@Reg(Keys.blockCorundum)
	public static final Block blockCorumdum = new BlockCompressed(Material.ROCK).setBeaconBase(); // 钢玉块
	@Reg(Keys.blockCreepSoil)
	public static final Block blockCreepSoil = new BlockCreepSoil(); // 虫苔
	@Reg(Keys.blockDecurrium)
	public static final Block blockDecurrium = new BlockCompressed(); // 深流钢块
	@Reg(Keys.blockFulgurite)
	public static final Block blockFulgurite = new BlockOre().setVeryRareOre(); // 闪电熔岩
	@Reg(Keys.blockFurutorin)
	public static final Block blockFurutorin = new BlockCompressed(Material.ROCK).setBeaconBase(); // 泠笛石块
	@Reg(Keys.blockHalleium)
	public static final Block blockHalleium = new BlockCompressed().setBeaconBase(); // 哈雷块
	@Reg(Keys.blockHeavesand)
	public static final Block blockHeavesand = new BlockCompressed(Material.ROCK).setBeaconBase(); // 沉沙石块
	@Reg(Keys.blockHothium)
	public static final Block blockHothium = new BlockCompressed().setBeaconBase(); // 霍斯块
	@Reg(Keys.blockIcelandSpar)
	public static final Block blockIcelandSpar = new BlockCompressed(Material.ROCK); // 冰洲石块
	@Reg("block_ice_light")
	public static final Block blockIceLight = new BlockIceLight(); // 冻灯
	@Reg(Keys.blockIcelit)
	public static final Block blockIcelit = new BlockCompressed(Material.ROCK).setLightLevel(1); // 冰明玉块
	@Reg("icelit_glow")
	public static final Block blockIcelitGlow = new BlockIcelitGlow(); // 灯明 光点
	@Reg(Keys.blockImitatium)
	public static final Block blockImitatium = new BlockCompressed().setBeaconBase(); // 拟素块
	@Reg(Keys.blockImmersedSilver)
	public static final Block blockImmersedSilver = new BlockCompressed().setBeaconBase(); // 沉银块
	@Reg(Keys.blockInertWitherium)
	public static final Block blockInertWitherium = new BlockCompressed().setBeaconBase(); // 惰性凋零块
	@Reg(Keys.blockInkPowder)
	public static final Block blockInkPowder = new BlockCompressed(Material.SAND); // 墨粉块
	@Reg(Keys.blockIrisia)
	public static final Block blockIrisia = new BlockCompressed().setBeaconBase(); // 艾瑞希亚块
	@Reg(Keys.blockLavaCrystal)
	public static final Block blockLavaCrystal = new BlockCompressed(Material.ROCK).setBeaconBase().setLightLevel(1); // 岩浆结晶块
	@Reg(Keys.blockLizanite)
	public static final Block blockLizanite = new BlockCompressed(Material.ROCK).setBeaconBase(); // 坦桑石块
	@Reg(Keys.blockMagiga)
	public static final Block blockMagiga = new BlockCompressed().setBeaconBase(); // 伽钢块
	@Reg(Keys.blockMercurySulfide)
	public static final Block blockMercurySulfide = new BlockCompressed(); // 汞红块
	@Reg(value = Keys.blockMeteorolite, od = {"meteorolite"})
	@GenOre(times = 1, timeRate = 0.6f, size = 15, minY = 10, maxY = 70, biome = Strategy.ONLY_WHITELIST, biomeWL ={"$desert"})
	public static final BlockOre blockMeteorolite = new BlockOre().setEpicOre(); // 陨石
	@Reg(Keys.blockMoonStone)
	public static final Block blockMoonStone = new BlockCompressed(Material.ROCK).setBeaconBase();// 月光石块
	@Reg(Keys.blockNitre)
	public static final Block blockNitre = new BlockCompressed(Material.ROCK); // 硝石块
	@Reg(Keys.blockOpal)
	public static final Block blockOpal = new BlockCompressed(Material.ROCK).enableTransparent().setBeaconBase(); // 欧珀块
	@Reg(Keys.blockOraclium)
	public static final Block blockOraclium = new BlockCompressed().setBeaconBase(); // 神谕块
	@Reg(Keys.blockPolarium)
	public static final Block blockPolarium = new BlockCompressed().setBeaconBase(); // 勾陈块
	@Reg(Keys.blockPotos)
	public static final Block blockPotos = new BlockCompressed(Material.ROCK).setBeaconBase(); // 泊水石块
	@Reg(Keys.blockPrehnite)
	public static final Block blockPrehnite = new BlockCompressed(Material.ROCK).setBeaconBase(); // 葡萄石块
	@Reg(Keys.blockProustite)
	public static final Block blockProustite = new BlockCompressed(Material.ROCK); // 硫砷银矿
	@Reg(Keys.blockPyrophyllite)
	public static final Block blockPyrophyllite = new BlockCompressed(Material.ROCK); // 叶蜡石块
	@Reg(Keys.blockRoyalAlloy)
	public static final Block blockRoyalAlloy = new BlockCompressed().setBeaconBase(); // 奢华合金块
	@Reg(Keys.blockRedins)
	public static final Block blockRuby = new BlockCompressed(Material.ROCK).setBeaconBase(); // 虹辉石块
	@Reg("block_sea_grass")
	public static final BlockSeaGrass blockSeaGrass = new BlockSeaGrass(); // 海草
	@Reg(Keys.blockSolita)
	public static final Block blockSolita = new BlockCompressed().setBeaconBase(); // 坚金块
	@Reg(Keys.blockSpinel)
	public static final Block blockSpinel = new BlockCompressed(Material.ROCK).setBeaconBase(); // 尖晶石块
	@Reg(Keys.blockSteamium)
	public static final Block blockSteamium = new BlockCompressed().setBeaconBase(); // 气钢块
	@Reg(Keys.blockStellarium)
	public static final Block blockStellarium = new BlockCompressed().setBeaconBase().setLightLevel(1); // 恒星金属块
	@Reg(Keys.blockStellariumObsidian)
	public static final Block blockStellariumObsidian = new Block(Material.ROCK); // 恒星黑曜石
	@Reg(Keys.blockSunStone)
	public static final Block blockSunStone = new BlockCompressed(Material.ROCK).setBeaconBase(); // 日光石块
	@Reg(Keys.blockTalcum)
	public static final Block blockTalcum = new BlockCompressed(Material.ROCK); // 滑石块
	@Reg(Keys.blockTanatonium)
	public static final Block blockTanatonium = new BlockCompressed().setBeaconBase().setLightLevel(1); // 塔拉特妮姆块
	@Reg(Keys.blockTinkerDisintegrator)
	public static final Block blockTinkerDisintegrator = new BlockTinkerDisintegrator(); // 匠魂粉碎机
	@Reg(Keys.blockTitanium)
	public static final Block blockTitanium = new BlockCompressed().setBeaconBase(); // 钛块
	@Reg(Keys.blockTonium)
	public static final Block blockTonium = new BlockCompressed().setBeaconBase(); // 钝金块
	@Reg(Keys.blockTopaz)
	public static final Block blockTopaz = new BlockCompressed(Material.ROCK).setBeaconBase(); // 托帕石块
	@Reg(Keys.blockTorrentialCrystal)
	public static final Block blockTorrentialCrystal = new BlockCompressed(Material.ROCK).setBeaconBase(); // 激流水晶块
	@Reg(Keys.blockTourmaline)
	public static final Block blockTourmaline = new BlockCompressed(Material.ROCK); // 电气石块
	@Reg(Keys.blockUlun)
	public static final Block blockUlun = new BlockCompressed(Material.ROCK).setBeaconBase(); // 乌润石块
	@Reg(Keys.blockVibratingCrystal)
	public static final Block blockVibratingCrystal = new BlockCompressed(Material.ROCK).setBeaconBase(); // 振晶块
	@Reg(Keys.blockWitherium)
	public static final Block blockWitherium = new BlockCompressed().setBeaconBase(); // 凋零块
	@Reg(Keys.oreAltairium)
//	@GenMeteoWorld
	@GenOre(times = 1, timeRate = 1, size = 3, minY = 10, maxY = 70, biome = Strategy.ONLY_WHITELIST, biomeWL ={"$desert"})
	public static final Block oreAltairium = new BlockOre().setEpicOre(); // 河鼓矿
	@Reg(Keys.oreBrumeJade)
//	@GenOre(times=2,timeRate = 0.8f,size=6,minY = 24,maxY = 56)
	public static final Block oreBrumeJade = new BlockOreBrumeJade(); // 云玉矿
	@Reg(Keys.oreBlackrock)
	@GenOre(times = 5, timeRate = 0.7, size = 20, minY = 40, maxY = 80)
	public static final Block oreBlackrock = new BlockOre(Items.blackrock, 1, 3, 2, 1, 6).setCommonOre(); // 黑石矿石
	@Reg(Keys.oreBrokenBedrock)
	public static final Block oreBrokenBedrock = new BlockOre(Items.brokenBedrock, 1, 2, 1, 6, 10)
	{{
		setHarvestLevel("pickaxe", HarvestLevels.COBALT);
	}}
			.setHardness(80f).setResistance(1000); // 破碎基岩
	@Reg(Keys.oreChloroplast)
	@GenOre(times = 2, timeRate = 0.1, size = 3, minY = 60, maxY = 70)
	public static final Block oreChloroplast = new BlockOreChloroplast().setVeryRareOre(); // 叶绿矿
	@Reg(value = Keys.oreCinnabar, od = {"oreCinnabar", "oreQuicksilver", "oreMercury"})
	@GenOre(times = 4, size = 9, minY = 18, maxY = 56)
	public static final Block oreCinnabar = new BlockOre(Items.cinnabar, 1, 3, 2, 3, 5).setCommonOre(); // 辰砂矿石
	@Reg(Keys.oreCordierite)
	@GenOre(times = 6, timeRate = 0.5f, size = 15, minY = 10, maxY = 55, biome = Strategy.ONLY_WHITELIST, biomeWL = {"minecraft:taiga_cold","minecraft:redwood_taiga","minecraft:taiga","minecraft:mutated_taiga_cold","minecraft:mutated_redwood_taiga","minecraft:mutated_taiga"})
	public static final Block oreCordierite = new BlockOre(Items.cordierite, 1, 1, 1, 4, 6).setRareOre(); // 堇青石矿
	//	@Reg(Keys.oreShell)
//	public static final BlockOreShell oreShell = new BlockOreShell(Items.shell,3,6,1,4,6); // 散贝壳方块
	@Reg(Keys.oreCorundum)
	@GenOre(times = 4, timeRate = 0.7, size = 7, minY = 0, maxY = 28, biome = Strategy.ONLY_WHITELIST, biomeWL = {"$hills"})
	public static final Block oreCorundum = new BlockOre(Items.corundum, 1, 1, 1, 2, 4).setRareOre(); // 刚玉矿
	@Reg(Keys.oreFurutorin)
//	@GenOre(times = 2, timeRate = 0.8f, size = 6, minY = 24, maxY = 56)
	public static final Block oreFurutorin = new BlockOre(Items.furutorin, 1, 1, 1, 4, 6).setRareOre(); // 泠笛石矿
	@Reg(Keys.oreHalleium)
//	@GenMeteoWorld
	@GenOre(times = 2, timeRate = 0.5, size = 4, minY = 15, maxY = 18)
	public static final Block oreHalleium = new BlockOre().setEpicOre(); // 哈雷矿
	@Reg(Keys.oreHeavesand)
//	@GenOre(times = 2, timeRate = 0.8f, size = 6, minY = 24, maxY = 56)
	public static final Block oreHeavesand = new BlockOre(Items.heavesand, 1, 1, 1, 4, 6).setRareOre(); // 沉沙石矿
	@Reg(Keys.oreHothium)
//	@GenMeteoWorld
	@GenOre(times = 5, timeRate = 0.6, size = 3, minY = 0, maxY = 60, biome = Strategy.ONLY_WHITELIST, biomeWL = {"$cold"})
	public static final Block oreHothium = new BlockOre().setEpicOre(); // 霍斯矿
	@Reg(Keys.oreIcelandSpar)
	@GenOre(times = 8, timeRate = 0.6f, size = 3, minY = 0, maxY = 50, biome = Strategy.ONLY_WHITELIST, biomeWL = {"$cool","$cold"})
	public static final Block oreIcelandSpar = new BlockOre(Items.icelandSpar, 1, 2, 1, 3, 5).setCommonOre(); // 冰洲石矿
	@Reg(Keys.oreIcelit)
	@GenOre(times = 6, timeRate = 0.8, size = 6, minY = 5, maxY = 50, biome = Strategy.ONLY_WHITELIST, biomeWL = "$sea")
	public static final Block oreIcelit = new BlockOre(Items.icelit, 1, 1, 1, 3,5).setLightLevel(0.6f); // 冰明玉矿
	@Reg(Keys.oreImitatium)
	public static final Block oreImitatium = new BlockOre(Material.ROCK, Items.nuggetImitatium, 3, 6, 2, 6, 10)
			.setEpicOre().setLightLevel(0.1f); // 拟素矿
	@Reg(Keys.oreImmersedSilver)
	@GenOre(times = 8, timeRate = 0.5, size = 7, minY = 0, maxY = 30, biome = Strategy.ONLY_WHITELIST, biomeWL = {"minecraft:deep_ocean"})
	public static final Block oreImmersedSilver = new BlockOre().setVeryRareOre(); // 沉银矿石
	@Reg(Keys.oreInertWitherium)
	@GenOre(times = 2, timeRate = 0.5, size = 3, dim = Strategy.ONLY_WHITELIST, dimsWL = -1, selector = "netherrack")
	public static final Block oreInertWitherium = new BlockOre().setVeryRareOre(); // 惰性凋零矿
	@Reg(Keys.oreInkPowder)
	@GenOre(times = 1, timeRate = 0.5f, size = 10, minY = 40, maxY = 80)
	public static final Block oreInkPowder = new BlockOre(Items.inkPowder, 2, 4, 2, 2, 7).setCommonOre(); // 墨粉矿石
	@Reg(Keys.oreLavaCrystal)
	@GenOre(times = 10, timeRate = 1, size = 3, minY = 32, maxY = 33, dim = Strategy.ONLY_WHITELIST, dimsWL = -1, selector = "lava")
	public static final Block oreLavaCrystal = new BlockOre(Items.lavaCrystal, 1, 1, 1, 3, 6).setVeryRareOre().setLightLevel(1); // 熔岩水晶矿
	@Reg(Keys.oreLizanite)
	@GenOre(times = 4, timeRate = 0.5f, size = 5, minY = 6, maxY = 50, dim = Strategy.ONLY_WHITELIST, dimsWL = -1, selector = "netherrack")
	public static final Block oreLizanite = new BlockOre(Items.lizanite, 1, 1, 1, 4, 6).setRareOre(); // 丽辰石矿
	@Reg(Keys.oreMoonStone)
	@GenOre(times = 3, timeRate = 0.8f, size = 4, minY = 0, maxY = 64, biome = Strategy.ONLY_WHITELIST, biomeWL = {"$cold"})
	public static final Block oreMoonStone = new BlockOre(Items.moonStone, 1, 1, 1, 4, 6).setRareOre(); // 月光石矿
	@Reg(Keys.oreNitre)
	@GenOre(times = 4, timeRate = 1, size = 8, minY = 30, maxY = 50, biome = Strategy.ONLY_WHITELIST, biomeWL = {"$desert"})
	public static final Block oreNitre = new BlockOre(Items.nitre, 1, 3, 1, 3, 5).setCommonOre(); // 硝石矿
	@Reg(Keys.oreOpal)
	@GenOre(times = 4, timeRate = 1, size = 5, minY = 0, maxY = 24, biome = Strategy.ONLY_WHITELIST, biomeWL = {"$desert"})
	public static final Block oreOpal = new BlockOre(Items.opal, 1, 1, 1, 4, 6).setRareOre(); // 欧泊
	@Reg(Keys.orePolarium)
//	@GenMeteoWorld
	@GenOre(times = 6, timeRate = 1, size = 5, minY = 0, maxY = 180, dim = Strategy.ONLY_WHITELIST, dimsWL = 1, selector = "end_stone")
	public static final Block orePolarium = new BlockOre().setEpicOre(); // 勾陈矿
	@Reg(Keys.orePotos)
//	@GenOre(times = 2, timeRate = 0.8f, size = 6, minY = 24, maxY = 56)
	public static final Block orePotos = new BlockOre(Items.potos, 1, 1, 1, 4, 6).setRareOre(); // 泊水石矿
	@Reg(Keys.orePrehnite)
	@GenOre(times = 8, timeRate = 0.3f, size = 5, minY = 0, maxY = 11)
	public static final Block orePrehnite = new BlockOre(Items.prehnite, 1, 1, 1, 4, 6).setRareOre(); // 葡萄石矿
	@Reg(Keys.oreProustite)
	@GenOre(times = 5,timeRate = 1, size = 9, minY = 20, maxY = 100,dim = Strategy.ONLY_WHITELIST, dimsWL = -1, selector = "netherrack")
	public static final Block oreProustite = new BlockOre(Items.proustite, 1, 1, 1, 4, 6).setRareOre(); // 硫砷银矿
	@Reg(Keys.orePyrophyllite)
	@GenOre(times = 4,timeRate = 1, size = 6, minY = 30, maxY = 80, biome = Strategy.ONLY_WHITELIST, biomeWL = {"$forests"})
	public static final Block orePyrophyllite = new BlockOre(Items.pyrophyllite, 1, 2, 1, 3, 5).setCommonOre(); // 叶蜡石矿
	@Reg(value = Keys.oreRedins)
	@GenOre(times = 3, timeRate = 0.8f, size = 5, minY = 0, maxY = 40)
	public static final Block oreRedins = new BlockOre(Items.redins, 1, 1, 1, 6, 10).setRareOre(); // 虹辉石矿
	@Reg(Keys.oreSolidDirt)
	@GenOre(times = 4, timeRate = 0.3, size = 8, minY = 10, maxY = 128, selector = "dirt")
	public static final Block oreSolidDirt = new BlockOre().setCommonOre().setHardness(15); // 硬实泥土
	@Reg(Keys.oreSolidSand)
	@GenOre(times = 4, timeRate = 0.3, size = 8, minY = 10, maxY = 128, selector = "sand")
	public static final Block oreSolidSand = new BlockOre().setCommonOre().setHardness(15); // 硬实沙子
	@Reg(Keys.oreSolidStone)
	@GenOre(times = 4, timeRate = 0.3, size = 8, minY = 10, maxY = 128, selector = "stone")
	public static final Block oreSolidStone = new BlockOre().setCommonOre().setHardness(15); // 硬实石头
	@Reg(value = Keys.oreSpinel, od = {"oreSpinel"})
	@GenOre(times = 5, timeRate = 1, size = 4, minY = 40, maxY = 100,biome = Strategy.ONLY_WHITELIST, biomeWL = {"$forests","$mesas","$hills"})
	public static final Block oreSpinel = new BlockOre(Items.spinel, 1, 1, 1, 3, 5).setVeryRareOre(); // 尖晶石矿
	@Reg(Keys.oreSteamium)
	@GenOre(times = 7,timeRate = 0.7f, size = 9, minY = 0, maxY = 60)
	public static final Block oreSteamium = new BlockOre().setRareOre(); // 气钢矿
	@Reg(Keys.oreStellarium)
	@GenOre(times = 8, timeRate = 0.1f, size = 3, minY = 20, maxY = 60, biome = Strategy.ONLY_WHITELIST, biomeWL = {"$desert"})
	public static final Block oreStellarium = new BlockOre().setEpicOre(); // 恒星金属矿
	@Reg(Keys.oreSunStone)
	@GenOre(times = 2, timeRate = 0.8f, size = 3, minY = 30, maxY = 100, biome = Strategy.ONLY_WHITELIST, biomeWL = {"$hot"})
	public static final Block oreSunStone = new BlockOre(Items.sunStone, 1, 1, 1, 4, 6).setRareOre(); // 日光石矿
	@Reg(Keys.oreTalcum)
	@GenOre(times = 10,timeRate = 0.7f, size = 9, minY = 20, maxY = 64,biome = Strategy.ONLY_WHITELIST, biomeWL = {"minecraft:swampland"})
	public static final Block oreTalcum = new BlockOre(Items.talcum, 1, 2, 1, 3, 5).setCommonOre(); // 滑石矿
	@Reg(Keys.oreTanatonium)
	@GenOre(times = 6, timeRate = 0.8, size = 6, minY = 5, maxY = 50, biome = Strategy.ONLY_WHITELIST, biomeWL = "$sea")
	public static final Block oreTanatonium = new BlockOre().setEpicOre().setLightLevel(0.2f); // 塔拉特妮姆矿
	@Reg(Keys.oreRutile)
	@GenOre(times = 3, timeRate = 0.8f, size = 6, minY = 0, maxY = 60,biome = Strategy.ONLY_WHITELIST, biomeWL = {"$plains"})
	public static final Block oreTitanium = new BlockOre().setEpicOre(); // 金红石矿
	@Reg(Keys.oreTonium)
//	@GenMeteoWorld
	@GenOre(times = 2, timeRate = 1, size = 3, minY = 0, maxY = 30, biome = Strategy.ONLY_WHITELIST, biomeWL = {"minecraft:deep_ocean"})
	public static final Block oreTonium = new BlockOre().setEpicOre(); // 钝金矿
	@Reg(Keys.oreTopaz)
	@GenOre(times = 5, timeRate = 1, size = 3, minY = 0, maxY = 64,biome = Strategy.ONLY_WHITELIST, biomeWL = {"$hills"})
	public static final Block oreTopaz = new BlockOre(Items.topaz, 1, 1, 1, 4, 6).setRareOre(); // 托帕石矿
	@Reg(Keys.oreTorrentialCrystal)
	public static final Block oreTorrentialCrystal = new BlockOre(Items.torrentialCrystal, 1, 1, 1, 4, 6).setRareOre(); // 激流水晶矿
	@Reg(Keys.oreTourmaline)
	@GenOre(times = 2, timeRate = 0.8f, size = 6, minY = 10, maxY = 40)
	public static final Block oreTourmaline = new BlockOre(Items.tourmaline, 1, 2, 1, 2, 4).setRareOre(); // 电气石矿
	@Reg(Keys.oreTreeRoot)
	public static final Block oreTreeRoot = new BlockOre(Material.WOOD, Material.WOOD.getMaterialMapColor(), Items.treeRoot, 1, 3, 1, 2, 4)
	{{
		setSoundType(SoundType.WOOD);
		setHardness(2.0f);
		setResistance(25);
	}}; // 树根
	@Reg(Keys.oreUlun)
//	@GenOre(times = 2, timeRate = 0.8f, size = 6, minY = 24, maxY = 56)
	public static final Block oreUlun = new BlockOre(Items.ulun, 1, 1, 1, 4, 6).setRareOre(); // 乌润石矿
	@Reg(Keys.oreVibratingCrystal)
	@GenOre(times = 7, timeRate = 0.5f, size = 5, minY = 5, maxY = 160, dim = Strategy.ONLY_WHITELIST, dimsWL = 1, selector = "end_stone")
	public static final Block oreVibratingCrystal = new BlockOre(Items.vibratingCrystal, 1, 3, 1, 5, 7).setVeryRareOre(); // 振晶矿
	@Reg(Keys.oreWitherium)
	public static final Block oreWitherium = new BlockOre().setVeryRareOre(); // 凋零矿

	static
	{
		blockStellariumObsidian.setHardness(50F).setResistance(1200F).setHarvestLevel("pickaxe", HarvestLevels.OBSIDIAN);
	}

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
//	@Reg("gem_brewin_stand")
//	public static final Block gemBrewingStand = new BlockSpecialBrewingStand(); 宝石酿造台
//	static
//	{
//		TEGemBrewingStand.registerFixesBrewingStand(Minecraft.getMinecraft().getDataFixer());
//	}
}
