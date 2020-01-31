package firok.tiths.common;

import firok.tiths.item.*;
import firok.tiths.util.Reg;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;

import static firok.tiths.common.Keys.*;

@SuppressWarnings("all")
public class Items
{

	@Reg("debug")
	public static final Item debug = new ItemDebug(); // 调试工具

	// 原材料 - 怪物掉落
	@Reg(nameSpiderLeg)
	public static final Item spiderLeg = new Item(); // 蛛腿
	@Reg(nameHardSpiderLeg)
	public static final Item hardSpiderLeg = new Item(); // 坚硬蛛腿
	@Reg(nameEnderDragonSquama)
	public static final Item enderDragonSquama = new Item(); // 末影龙鳞

	// 原材料 - 各种
	@Reg(value=nameCinnabar,od={"cinnabar","quicksilver","mercury"})
	public static final Item cinnabar = new Item(); // 辰砂
	@Reg(value=nameInkPowder,od={"dye","dyeBlack"})
	public static final Item inkPowder = new Item(); // 墨粉
	@Reg(nameBrokenIce)
	public static final Item brokenIce = new Item(); // 碎冰
	@Reg(nameShell)
	public static final Item shell = new Item(); // 贝壳

	// 原材料 - 矿
//	@Reg(nameSkyCrystal)
//	public static final Item skyCrystal = new Item(); // 天空水晶
//	@Reg(nameStormCrystal)
//	public static final Item stormCrystal = new Item(); // 风暴水晶
//	@Reg(namePhantomCrystal)
//	public static final Item phantomCrystal = new Item(); // 异象水晶
//	@Reg(nameAntiGravCrystal)
//	public static final Item antiGravCrystal = new Item(); // 反重力水晶
	@Reg(value=nameBlackrock,od={"stone"})
	public static final Item blackrock = new Item(); // 黑石
	@Reg(value=nameRuby,od={"gem","gemRuby","ruby"})
	public static final Item ruby = new Item(); // 红宝石
	@Reg(nameCorundum)
	public static final Item corundum = new Item(); // 钢玉
	@Reg(value=nameNitre)
	public static final Item nitre = new Item(); // 硝石
	@Reg(namePyrophyllite)
	public static final Item pyrophyllite = new Item(); // 叶蜡石
	@Reg(nameIcelandSpar)
	public static final Item icelandSpar = new Item(); // 冰洲石
	@Reg(value=nameSpinel,od={"gem","gemSpinel","spinel"})
	public static final Item spinel = new Item(); // 尖晶石
	@Reg(nameTalcum)
	public static final Item talcum = new Item(); // 滑石
	@Reg(nameTourmaline)
	public static final Item tourmaline = new Item(); // 电气石
	@Reg(nameSunStone)
	public static final Item sunStone = new Item(); // 日光石
	@Reg(nameMoonStone)
	public static final Item moonStone = new Item(); // 月光石
	@Reg(nameTreeRoot)
	public static final Item treeRoot = new Item(); // 树根
	@Reg(nameBrokenBedrock)
	public static final Item brokenBedrock = new Item(); // 破碎基岩
	@Reg(nameOpal)
	public static final Item opal = new Item(); // 欧珀
	@Reg(nameTopaz)
	public static final Item topaz = new Item(); // 托帕石

	// 金属粒
	@Reg(Keys.nuggetStellarium)
	public static final Item nuggetStellarium = new Item(); // 恒星金属粒
	@Reg(Keys.nuggetRoyalAlloy)
	public static final Item nuggetRoyalAlloy = new Item(); // 奢华合金粒
	@Reg(Keys.nuggetImmersedSilver)
	public static final Item nuggetImmersedSilver = new Item(); // 沉银粒
	@Reg(value=Keys.nuggetMithril,od={"nuggetMithril"})
	public static final Item nuggetMithril = new Item(); // 秘银粒
	@Reg(value=Keys.nuggetAdamantine,od={"nuggetAdamantine"})
	public static final Item nuggetAdamantine = new Item(); // 精金粒
	@Reg(Keys.nuggetInertWitherium)
	public static final Item nuggetInertWitherium =new Item(); // 惰性凋零粒
	@Reg(Keys.nuggetWitherium)
	public static final Item nuggetWitherium = new Item(); // 凋零粒
	@Reg(value=Keys.nuggetTitanium,od={"nuggetTitanium"})
	public static final Item nuggetTitanium = new Item(); // 钛粒
	@Reg(Keys.nuggetPolarium)
	public static final Item nuggetPolarium = new Item(); // 勾陈粒
	@Reg(Keys.nuggetHalleium)
	public static final Item nuggetHalleium = new Item(); // 哈雷粒
	@Reg(Keys.nuggetAltairium)
	public static final Item nuggetAltairium = new Item(); // 河鼓粒
	@Reg(Keys.nuggetHothium)
	public static final Item nuggetHothium = new Item(); // 霍斯粒
	@Reg(Keys.nuggetTonium)
	public static final Item nuggetTonium = new Item(); // 钝金粒
	@Reg(Keys.nuggetCocoa)
	public static final Item nuggetCocoa = new Item(); // 可可粒
	@Reg(Keys.nuggetIrisia)
	public static final Item nuggetIrisia = new Item(); // 艾瑞西亚锭

	// 原材料 - 金属锭
	@Reg(Keys.ingotStellarium)
	public static final Item ingotStellarium = new Item(); // 恒星金属锭
	@Reg(Keys.ingotRoyalAlloy)
	public static final Item ingotRoyalAlloy = new Item(); // 奢华合金锭
	@Reg(Keys.ingotImmersedSilver)
	public static final Item ingotImmersedSilver = new Item(); // 沉银锭
	@Reg(value=Keys.ingotMithril,od={"ingotMithril"})
	public static final Item ingotMithril = new Item(); // 秘银锭
	@Reg(value=Keys.ingotAdamantine,od={"ingotAdamantine"})
	public static final Item ingotAdamantine = new Item(); // 精金锭
	@Reg(Keys.ingotInertWitherium)
	public static final Item ingotInertWitherium = new Item(); // 惰性凋零锭
	@Reg(Keys.ingotWitherium)
	public static final Item ingotWitherium = new Item(); // 凋零锭
	@Reg(value=Keys.ingotTitanium,od={"ingotTitanium"})
	public static final Item ingotTitanium = new Item(); // 钛锭
	@Reg(Keys.ingotPolarium)
	public static final Item ingotPolarium = new Item(); // 勾陈锭
	@Reg(Keys.ingotHalleium)
	public static final Item ingotHalleium = new Item(); // 哈雷锭
	@Reg(Keys.ingotAltairium)
	public static final Item ingotAltairium = new Item(); // 河鼓锭
	@Reg(Keys.ingotHothium)
	public static final Item ingotHothium = new Item(); // 霍斯锭
	@Reg(Keys.ingotTonium)
	public static final Item ingotTonium = new Item(); // 钝金锭
	@Reg(Keys.ingotCocoa)
	public static final Item ingotCocoa = new Item(); // 可可锭
	@Reg(Keys.ingotIrisia)
	public static final Item ingotIrisia = new Item(); // 艾瑞西亚锭

	// 单纯的原材料
//	@Reg(nameRoyalPaper)
//	public static final Item royalPaper = new Item(); // 奢华纸张
//	@Reg(nameRoyalBook)
//	public static final Item royalBook = new Item(); // 奢华书本

	// 植物
//	@Reg(Keys.nameHuraFruit)
//	public static final Item huraFruit = new Item(); // 胡拉果实

	// 食物
	@Reg(nameHotBread)
	public static final Item hotBread = new ItemHotFood(4,0.4f,false); // 灼热面包
	@Reg(nameHotFish)
	public static final Item hotFish = new ItemHotFood(6,0.5f,false); // 灼热鱼
	@Reg(nameFlesh)
	public static final Item flesh = new ItemFood(8,0.7f,true)
			.setPotionEffect(new PotionEffect(MobEffects.NAUSEA,200,0),1); // 血肉

	// 唱片
	@Reg(value=Keys.recordTinkersEfforts,od={"record"})
	public static final Item recordTinkersEfforts = new ItemRecord(Keys.recordTinkersEfforts,SoundEvents.recordTinkersEfforts);
	@Reg(value=Keys.recordTinkersWill,od={"record"})
	public static final Item recordTinkersWill = new ItemRecord(Keys.recordTinkersWill,SoundEvents.recordTinkersWill);
//	public static final Item recordTinkersImagination = new ItemRecord(Keys.recordTinkersImagination,null);
//	public static final Item recordTinkersEnemy = new ItemRecord(Keys.recordTinkersEnemy,null;)

	// 匠魂工具
	@Reg(nameHardener)
	public static final Item hardener = new Item(); // 硬化剂
	@Reg(namePolisher)
	public static final Item polisher = new Item(); // 磨石
	@Reg(nameInkySlime)
	public static final Item inkySlime = new Item(); // 墨染粘液
	@Reg(nameDriller)
	public static final Item driller = new Item(); // 打孔器
//	@Reg(nameEnderGem)
	public static final Item enderGem = new Item(); // 末影之石
	@Reg(value=namePhasingGem,od={"gem"})
	public static final Item phasingGem = new Item(); // 相变之石

	// 消耗品
//	@Reg(nameGatewayGem)
	public static final Item gatewayGem = new ItemGatewayGem(4); // 折跃之石
	@Reg(nameAncientBookIrisia)
	public static final Item ancientBookIrisia = new ItemXP(true,20); // 艾瑞西亚古籍

	public static void trigger(){}
}
