package firok.tiths.common;

import firok.tiths.TinkersThings;
import firok.tiths.item.*;
import firok.tiths.util.reg.Reg;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import static firok.tiths.common.Keys.*;

@SuppressWarnings("all")
public final class Items
{
	private Items() {}

	@Reg("debug")
	public static final Item debug = new ItemDebug(); // 调试工具
	@Reg("debug_clear_stone")
	public static final Item debugClearStone = new ItemDebugClearStone(); // 调试工具-清空石头

	// 原材料 - 怪物掉落
	@Reg(nameSpiderLeg)
	public static final Item spiderLeg = new ItemCustom(); // 蛛腿
	@Reg(nameHardSpiderLeg)
	public static final Item hardSpiderLeg = new ItemCustom(); // 坚硬蛛腿
	@Reg(nameEnderDragonSquama)
	public static final Item enderDragonSquama = new ItemCustom(); // 末影龙鳞

	// 原材料 - 各种
	@Reg(value=nameCinnabar,od={"cinnabar","quicksilver","mercury"})
	public static final Item cinnabar = new ItemCustom(); // 辰砂
	@Reg(value=nameInkPowder,od={"dye","dyeBlack"})
	public static final Item inkPowder = new ItemCustom(); // 墨粉
	@Reg(nameBrokenIce)
	public static final Item brokenIce = new ItemCustom(); // 碎冰
	@Reg(nameShell)
	public static final Item shell = new ItemFood(1,0.05f,false); // 贝壳
	@Reg(nameShellCooked)
	public static final Item shellCooked = new ItemFood(1,0.2f,false); // 熟贝壳
	@Reg(nameEnderCreviceShard)
	public static final Item enderCreviceShard = new ItemCustom(); // 末影裂隙碎片
	@Reg(nameMercurySulfide)
	public static final Item mercurySulfide = new ItemCustom(); // 汞红锭
	@Reg(nameWitheringEssence)
	public static final Item witheringEssence = new ItemCustom(); // 凋零精华

//	public static final Item resplendentGem = new ItemCustom(); // 璀璨宝石

	// 原材料 - 矿
//	@Reg(nameSkyCrystal)
//	public static final Item skyCrystal = new ItemCustom(); // 天空水晶
//	@Reg(nameStormCrystal)
//	public static final Item stormCrystal = new ItemCustom(); // 风暴水晶
//	@Reg(namePhantomCrystal)
//	public static final Item phantomCrystal = new ItemCustom(); // 异象水晶
//	@Reg(nameAntiGravCrystal)
//	public static final Item antiGravCrystal = new ItemCustom(); // 反重力水晶
	@Reg(value=nameBlackrock,od={"stone"})
	public static final Item blackrock = new ItemCustom(); // 黑石
	@Reg(value= nameRedins,od={"gem"})
	public static final Item redins = new ItemCustom(); // 红辉石
	@Reg(nameCorundum)
	public static final Item corundum = new ItemCustom(); // 钢玉
	@Reg(value=nameNitre,od={"dyeGray"})
	public static final Item nitre = new ItemCustom(); // 硝石
	@Reg(namePyrophyllite)
	public static final Item pyrophyllite = new ItemCustom(); // 叶蜡石
	@Reg(nameIcelandSpar)
	public static final Item icelandSpar = new ItemCustom(); // 冰洲石
	@Reg(value=nameSpinel,od={"gem","gemSpinel","spinel"})
	public static final Item spinel = new ItemCustom(); // 尖晶石
	@Reg(nameTalcum)
	public static final Item talcum = new ItemCustom(); // 滑石
	@Reg(nameTourmaline)
	public static final Item tourmaline = new ItemCustom(); // 电气石
	@Reg(value=nameSunStone,od={"gem","gemSunStone","sunStone"})
	public static final Item sunStone = new ItemCustom(); // 日光石
	@Reg(value=nameMoonStone,od={"gem","gemMoonStone","moonStone"})
	public static final Item moonStone = new ItemCustom(); // 月光石
	@Reg(nameTreeRoot)
	public static final Item treeRoot = new ItemCustom(); // 树根
	@Reg(nameBrokenBedrock)
	public static final Item brokenBedrock = new ItemCustom(); // 破碎基岩
	@Reg(value=nameOpal,od={"gem","gemOpal","opal"})
	public static final Item opal = new ItemCustom(); // 欧珀
	@Reg(value=nameTopaz,od={"gem","gemTopaz","topaz"})
	public static final Item topaz = new ItemCustom(); // 托帕石
	@Reg(value= nameLizanite,od={"gem"})
	public static final Item lizanite = new ItemCustom(); // 丽辰石
	@Reg(value=nameCordierite,od={"gem","gemCordierite","cordierite"})
	public static final Item cordierite = new ItemCustom(); // 堇青石
	@Reg(value=namePrehnite,od={"gem","gemPrehnite","prehnite"})
	public static final Item prehnite = new ItemCustom(); // 葡萄石
	@Reg(nameProustite)
	public static final Item proustite = new ItemCustom(); // 硫砷银
	@Reg(nameVibratingCrystal)
	public static final Item vibratingCrystal = new ItemCustom(); // 振晶
	@Reg(nameLavaCrystal)
	public static final Item lavaCrystal = new ItemCustom(); // 岩浆结晶
	@Reg(value=nameAventurine,od={"gem","gemAventurine","aventurine"})
	public static final Item aventurine = new ItemCustom(); // 东陵石
	@Reg(nameUlun)
	public static final Item ulun = new ItemCustom(); // 乌润石
	@Reg(namePotos)
	public static final Item potos = new ItemCustom(); // 泊水石
	@Reg(nameFurutorin)
	public static final Item furutorin = new ItemCustom(); // 泠笛石
	@Reg(nameHeavesand)
	public static final Item heavesand = new ItemCustom(); // 沉沙石
	// 金属粒
	@Reg(Keys.nuggetStellarium)
	public static final Item nuggetStellarium = new ItemCustom(); // 恒星金属粒
	@Reg(Keys.nuggetRoyalAlloy)
	public static final Item nuggetRoyalAlloy = new ItemCustom(); // 奢华合金粒
	@Reg(Keys.nuggetImmersedSilver)
	public static final Item nuggetImmersedSilver = new ItemCustom(); // 沉银粒
	@Reg(value=Keys.nuggetMithril,od={"nuggetMithril"})
	public static final Item nuggetMithril = new ItemCustom(); // 秘银粒
	@Reg(value=Keys.nuggetAdamantine,od={"nuggetAdamantine"})
	public static final Item nuggetAdamantine = new ItemCustom(); // 精金粒
	@Reg(Keys.nuggetInertWitherium)
	public static final Item nuggetInertWitherium =new ItemCustom(); // 惰性凋零粒
	@Reg(Keys.nuggetWitherium)
	public static final Item nuggetWitherium = new ItemCustom(); // 凋零粒
	@Reg(value=Keys.nuggetTitanium,od={"nuggetTitanium"})
	public static final Item nuggetTitanium = new ItemCustom(); // 钛粒
	@Reg(Keys.nuggetPolarium)
	public static final Item nuggetPolarium = new ItemCustom(); // 勾陈粒
	@Reg(Keys.nuggetHalleium)
	public static final Item nuggetHalleium = new ItemCustom(); // 哈雷粒
	@Reg(Keys.nuggetAltairium)
	public static final Item nuggetAltairium = new ItemCustom(); // 河鼓粒
	@Reg(Keys.nuggetHothium)
	public static final Item nuggetHothium = new ItemCustom(); // 霍斯粒
	@Reg(Keys.nuggetTonium)
	public static final Item nuggetTonium = new ItemCustom(); // 钝金粒
	@Reg(Keys.nuggetCocoa)
	public static final Item nuggetCocoa = new ItemCustom(); // 可可粒
	@Reg(Keys.nuggetIrisia)
	public static final Item nuggetIrisia = new ItemCustom(); // 艾瑞西亚锭
	@Reg(Keys.nuggetOraclium)
	public static final Item nuggetOraclium = new ItemCustom(); // 神谕粒
	@Reg(Keys.nuggetSteamium)
	public static final Item nuggetSteamium = new ItemCustom(); // 气钢粒
	@Reg(Keys.nuggetChloroplast)
	public static final Item nuggetChloroplast = new ItemCustom(); // 叶绿粒

	// 原材料 - 金属锭
	@Reg(Keys.ingotStellarium)
	public static final Item ingotStellarium = new ItemCustom(); // 恒星金属锭
	@Reg(Keys.ingotRoyalAlloy)
	public static final Item ingotRoyalAlloy = new ItemCustom(); // 奢华合金锭
	@Reg(Keys.ingotImmersedSilver)
	public static final Item ingotImmersedSilver = new ItemCustom(); // 沉银锭
	@Reg(value=Keys.ingotMithril,od={"ingotMithril"})
	public static final Item ingotMithril = new ItemCustom(); // 秘银锭
	@Reg(value=Keys.ingotAdamantine,od={"ingotAdamantine"})
	public static final Item ingotAdamantine = new ItemCustom(); // 精金锭
	@Reg(Keys.ingotInertWitherium)
	public static final Item ingotInertWitherium = new ItemCustom(); // 惰性凋零锭
	@Reg(Keys.ingotWitherium)
	public static final Item ingotWitherium = new ItemCustom(); // 凋零锭
	@Reg(value=Keys.ingotTitanium,od={"ingotTitanium"})
	public static final Item ingotTitanium = new ItemCustom(); // 钛锭
	@Reg(Keys.ingotPolarium)
	public static final Item ingotPolarium = new ItemCustom(); // 勾陈锭
	@Reg(Keys.ingotHalleium)
	public static final Item ingotHalleium = new ItemCustom(); // 哈雷锭
	@Reg(Keys.ingotAltairium)
	public static final Item ingotAltairium = new ItemCustom(); // 河鼓锭
	@Reg(Keys.ingotHothium)
	public static final Item ingotHothium = new ItemCustom(); // 霍斯锭
	@Reg(Keys.ingotTonium)
	public static final Item ingotTonium = new ItemCustom(); // 钝金锭
	@Reg(Keys.ingotCocoa)
	public static final Item ingotCocoa = new ItemCustom(); // 可可锭
	@Reg(Keys.ingotIrisia)
	public static final Item ingotIrisia = new ItemCustom(); // 艾瑞西亚锭
	@Reg(Keys.ingotOraclium)
	public static final Item ingotOraclium = new ItemCustom(); // 神谕锭
	@Reg(Keys.ingotSteamium)
	public static final Item ingotSteamium = new ItemCustom(); // 气钢锭
	@Reg(Keys.ingotGrain)
	public static final Item ingotGrain = new ItemCustom(); // 谷砖
	@Reg(Keys.ingotChloroplast)
	public static final Item ingotChloroplast = new ItemCustom(); // 叶绿锭

	// 单纯的原材料
//	@Reg(nameRoyalPaper)
//	public static final Item royalPaper = new ItemCustom(); // 奢华纸张
//	@Reg(nameRoyalBook)
//	public static final Item royalBook = new ItemCustom(); // 奢华书本
	@Reg(nameCinder)
	public static final Item cinder = new ItemCustom(); // 煤屑

	// 植物
//	@Reg(Keys.nameHuraFruit)
//	public static final Item huraFruit = new ItemCustom(); // 胡拉果实

	// 食物
	@Reg(nameHotBread)
	public static final Item hotBread = new ItemHotFood(4,0.4f,false); // 灼热面包
	@Reg(nameHotFish)
	public static final Item hotFish = new ItemHotFood(6,0.5f,false); // 灼热鱼
	@Reg(nameFlesh)
	public static final Item flesh = new ItemFood(8,0.7f,true)
			.setPotionEffect(new PotionEffect(MobEffects.NAUSEA,400,0),1); // 血肉
	@Reg(nameFleshCooked)
	public static final Item fleshCooked = new ItemFood(14,0.9f,false)
			.setPotionEffect(new PotionEffect(MobEffects.STRENGTH,600,0),1); // 熟血肉

	// 唱片
	@Reg(value=Keys.recordTinkersEfforts,od={"record"})
	public static final Item recordTinkersEfforts = new ItemRecord(Keys.recordTinkersEfforts,SoundEvents.recordTinkersEfforts);
	@Reg(value=Keys.recordTinkersWill,od={"record"})
	public static final Item recordTinkersWill = new ItemRecord(Keys.recordTinkersWill,SoundEvents.recordTinkersWill);
	@Reg(value=Keys.recordTinkersImagination,od={"record"})
	public static final Item recordTinkersImagination = new ItemRecord(Keys.recordTinkersImagination,SoundEvents.recordTinkersImagination);
//	public static final Item recordTinkersEnemy = new ItemRecord(Keys.recordTinkersEnemy,null;)

	// 匠魂工具
	@Reg(nameHardener)
	public static final Item hardener = new ItemCustom(); // 硬化剂
	@Reg(namePolisher)
	public static final Item polisher = new ItemCustom(); // 打磨剂
	@Reg(nameInkySlime)
	public static final Item inkySlime = new ItemCustom(); // 墨染粘液
	@Reg(nameDriller)
	public static final Item driller = new ItemCustom(); // 打孔器
//	@Reg(nameEnderGem)
//	public static final Item enderGem = new ItemCustom(); // 末影之石
	@Reg(value=namePhasingGem,od={"gem"})
	public static final Item phasingGem = new ItemCustom(); // 相变之石

	// 残页
	@Reg(value = "page1",od = {"paper"})
	public static final ItemPage page1 = new ItemPage("page.tiths.page1",new ResourceLocation(TinkersThings.MOD_ID,"textures/gui/page1"));
	@Reg(value = "page2",od = {"paper"})
	public static final ItemPage page2 = new ItemPage("page.tiths.page2",new ResourceLocation(TinkersThings.MOD_ID,"textures/gui/page1"));

	// 消耗品
//	@Reg(nameGatewayGem)
	public static final Item gatewayGem = new ItemGatewayGem(4); // 折跃之石
	@Reg(nameAncientBookIrisia)
	public static final Item ancientBookIrisia = new ItemXP(true,20); // 艾瑞西亚古籍
	@Reg(value = nameFormerCrystal,od = {"book"})
	public static final Item formerCrystal = new ItemFormerCrystal(); // 往昔水晶

	public static void trigger(){}
}
