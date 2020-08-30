package firok.tiths.common;

import firok.tiths.TinkersThings;
import firok.tiths.util.VersionPhase;
import firok.tiths.util.reg.GenOre;
import firok.tiths.world.Strategy;
import net.minecraftforge.common.config.Config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static firok.tiths.common.Keys.*;
import static net.minecraftforge.common.config.Config.*;

// 配置文件
public final class Configs
{
	private Configs() {}

	static final String c="config.tiths.";
	static final String pg="config.tiths.general.";
	static final String pgp="config.tiths.gameplay.";
	static final String pgt="config.tiths.trait.";
	static final String pga="config.tiths.trait_armor.";
	static final String pgg="config.tiths.gameplay.";
	static final String pbg="config.tiths.biome_groups.";
	static final String pwg="config.tiths.world_gens";
	@Config(name=TinkersThings.MOD_ID+"_general",modid= TinkersThings.MOD_ID)
	@LangKey(c+"general")
	public static class General // 全局设置
	{
		/**
		 * 启用匠魂护甲联动
		 */
		@LangKey(pg+"enable_conarm")
		@RequiresMcRestart
		public static boolean enable_conarm=true;

		/**
		 * 启用严格模式
		 * 对于玩家, 禁用严格模式可以减少游戏启动时由于各类id冲突等问题造成的崩溃;
		 * 对于开发者, 启用严格模式以寻找需要修正的问题
		 */
		@LangKey(pg+"enable_strict_mode")
		@RequiresMcRestart
		public static boolean enable_strict_mode=true;

		/**
		 * 启用材料属性自定义
		 */
		@LangKey(pg+"enable_material_customization")
		@RequiresMcRestart
		public static boolean enable_material_customization=true;

		/**
		 * 启用矿物属性自定义
		 */
		@LangKey(pg+"enable_ore_gen_customization")
		@RequiresMcRestart
		public static boolean enable_ore_gen_customization=true;

		/**
		 * 启用工具合成方法
		 */
		@LangKey(pg+"enable_tool_craft_functions")
		@RequiresMcRestart
		public static boolean enable_tool_craft_functions=true;

		/**
		 * 启用单线程优化
		 * 对于在单一线程执行tick逻辑的情况启用优化以提升性能
		 * 如果出现某些bug, 或确定目标环境为多线程tick逻辑模式, 请关闭此选项
		 */
		@LangKey(pg+"enable_single_thread_optimization")
		public static boolean enable_single_thread_optimization=true;

		/**
		 * 是否禁用警告信息
		 */
		@LangKey(pg+"disable_login_warning")
		public static boolean disable_login_warning=false;

		public static boolean isShowLoginWarning()
		{
			final VersionPhase version=TinkersThings.version;
			if(version.canDisableLoginWarning)
			{
				return !disable_login_warning; // master
			}
			else return true;
		}

		/**
		 * 是否为所有指令启用生存模式可用性
		 */
		@LangKey(pg+"enable_command_survival")
		public static boolean enable_command_survival=false;

		/**
		 * 单独为指定玩家开启指令生存模式可用性
		 */
		@LangKey(pg+"whitelist_command_survival")
		public static String[] whitelist_command_survival= {};

		/**
		 * 强制禁用跨区块的世界生成
		 * 默认的生成配置下, 跨区块生成的情况并不频繁, 所以不需要启用此选项
		 * 当由于更改了生成配置或其它一些情况导致频繁出现跨区块生成行为, 而降低世界生成性能时, 可以开启此选项
		 * 开启后将会强制禁用对跨区块坐标的读写, 避免跨区块生成, 但是这可能会导致世界生成时在区块交界处出现切面
		 */
		@LangKey(pg+"block_cross_chunk_generation")
		public static boolean block_cross_chunk_generation = false;

		/**
		 * 启用世界生成日志
		 * 启用后将会在/.minecraft/tiths/文件夹下生成日志
		 * 这可能会严重降低世界生成效率, 仅供测试用途
		 */
		@LangKey(pg+"log_chunk_generation")
		@RequiresWorldRestart
		public static boolean log_chunk_generation = false;

		/**
		 * 世界生成日志区块间隔
		 * 设定每隔多少区块生成一次日志
		 */
		@RangeInt(min = 1, max = 32)
		@LangKey(pg+"log_chunk_generation_cache")
		public static int log_chunk_generation_cache = 1;

		/**
		 * 世界生成全局维度黑名单
		 */
		@LangKey(pg+"blacklist_dim_generation")
		public static Integer[] blacklist_dim_generation = {};

		/**
		 * 世界生成全局生物群系黑名单
		 */
		@LangKey(pg+"blacklist_biome_generation")
		public static String[] blacklist_biome_generation = {};
	}


	@Config(name=TinkersThings.MOD_ID+"_gameplay",modid= TinkersThings.MOD_ID,category = "gameplay")
	@LangKey(c+"gameplay")
	public static class Gameplay
	{
		/**
		 * 启用奢华合金简易合成
		 */
		@LangKey(pgp+"enable_royal")
		@RequiresMcRestart
		public static boolean enable_royal_alloy_easy_crafting=false;

		/**
		 * 启用闪电熔岩生成
		 */
		@LangKey(pgp+"enable_fulgurite_generation")
		public static boolean enable_fulgurite_generation=true;
	}

	@Config(name=TinkersThings.MOD_ID+"_trait",modid= TinkersThings.MOD_ID,category = "trait")
	@LangKey(c+"trait")
	public static class Traits // 属性相关的设置
	{
		@LangKey(pgt+"rate_birefringent_damage")
		@RangeDouble(min=0,max=1)
		public static double rate_birefringent_damage = 0.3;

		@LangKey(pgt+"rate_blowing")
		@RangeDouble(min=0,max=1)
		public static double rate_blowing = 0.25;

		@LangKey(pgt+"blacklist_blowing_entity")
		public static String[] blacklist_blowing_entity = new String[0];

		@LangKey(pgt+"enable_blow_player")
		public static boolean enable_blow_player = true;

		@LangKey(pgt+"rate_carbonizing_transform")
		@RangeDouble(min=0,max=1)
		public static double rate_carbonizing_transform = 0.05;

		@LangKey(pgt+"rate_chemical_instable_attack")
		@RangeDouble(min=0,max=1)
		public static double rate_chemical_instable_attack = 0.15;

		@LangKey(pgt+"rate_chemical_instable_break")
		@RangeDouble(min=0,max=1)
		public static double rate_chemical_instable_break = 0.06;

		@LangKey(pgt+"factor_chemical_instable")
		@RangeDouble(min=0.01,max=5)
		public static double factor_chemical_instable = 1;

//		@LangKey(pgp+"factor_clustering_durability")
//		@RangeInt(min=2,max=10000)
//		public static int factor_clustering_durability = 550;
//
//		@LangKey(pgp+"factor_clustering_atk")
//		@RangeDouble(min=0,max=4)
//		public static double factor_clustering_atk = 2;
//
//		@LangKey(pgp+"factor_clustering_speed")
//		@RangeDouble(min=0,max=4)
//		public static double factor_clustering_speed = 1;
//
//		@LangKey(pgp+"factor_clustering_speed_mining")
//		@RangeDouble(min=0,max=4)
//		public static double factor_clustering_speed_mining = 1;
//
//		@LangKey(pgp+"factor_clustering_speed_draw")
//		@RangeDouble(min=1,max=4)
//		public static double factor_clustering_speed_draw = 0.08;

		@LangKey(pgt+"rate_creaky_use")
		@RangeDouble(min=0,max=1)
		public static double rate_creaky_use = 0.05;

		@LangKey(pgt+"rate_decoying_use")
		@RangeDouble(min=0,max=1)
		public static double rate_decoying_use = 0.08;

//		public static double factor_dichroic_light_mid = 7;
//
//		public static double factor_dichroic_light = 35;

		@LangKey(pgt+"factor_dragon_killer_damage_base")
		@RangeDouble(min=0,max=100)
		public static double factor_dragon_killer_damage_base=4;

		@LangKey(pgt+"factor_dragon_killer_damage_percent")
		@RangeDouble(min=0,max=5)
		public static double factor_dragon_killer_damage_percent=1.25;

		@LangKey(pgt+"range_extreme_freezing")
		@RangeDouble(min=1,max=10)
		public static double range_extreme_freezing=5;

		@LangKey(pgt+"enable_gluttonic_clear")
		public static boolean enable_gluttonic_clear=true;

		@LangKey(pgt+"factor_gluttonic_food")
		@RangeInt(min=0,max=100)
		public static int factor_gluttonic_food=4;

		@LangKey(pgt+"factor_gorgeous_min")
		@RangeDouble(min=1,max=10)
		public static double factor_gorgeous_min=1.1;

		@LangKey(pgt+"factor_gorgeous_max")
		@RangeDouble(min=1,max=10)
		public static double factor_gorgeous_max=1.2;

		@LangKey(pgt+"factor_hemolytic_damage")
		@RangeDouble(min=1,max=10)
		public static double factor_hemolytic_damage=1.15;

		@LangKey(pgt+"factor_hemolytic_limit")
		@RangeDouble(min=0,max=100)
		public static double factor_hemolytic_limit=5;

		@LangKey(pgt+"rate_hemolytic")
		@RangeDouble(min=0,max=1)
		public static double rate_hemolytic=0.5;

		@LangKey(pgt+"factor_hemolytic_repair")
		@RangeDouble(min=1,max=10)
		public static double factor_hemolytic_repair=5;

		@LangKey(pgt+"factor_inky")
		@RangeInt(min=1,max=6000)
		public static int factor_inky=100;

		@LangKey(pgt+"factor_life_inspiring_low")
		@RangeDouble(min=0.01,max=1)
		public static double factor_life_inspiring_low=0.35;

		@LangKey(pgt+"factor_life_inspiring_danger")
		@RangeDouble(min=0.01,max=1)
		public static double factor_life_inspiring_danger=0.15;

		@LangKey(pgt+"factor_luxurious_durability")
		@RangeDouble(min=0,max=1)
		public static double factor_luxurious_durability=0.2;

		@LangKey(pgt+"factor_moon_power")
		@RangeInt(min=0,max=10)
		public static int factor_moon_power=1;

		@LangKey(pgt+"range_nature_blessing_xz")
		@RangeInt(min=1,max=6)
		public static int range_nature_blessing_xz=4;

		@LangKey(pgt+"range_nature_blessing_y")
		@RangeInt(min=1,max=4)
		public static int range_nature_blessing_y=2;

		@LangKey(pgt+"count_nature_blessing")
		@RangeInt(min=1,max=20)
		public static int count_nature_blessing=8;

		@LangKey(pgt+"factor_nature_blessing_repair")
		@RangeInt(min=1,max=40)
		public static int factor_nature_blessing_repair=12;

		@LangKey(pgt+"enable_oracular_remove_target")
		public static boolean enable_oracular_remove_target=true;

		@LangKey(pgt+"enable_oracular_remove_player")
		public static boolean enable_oracular_remove_player=true;

		@LangKey(pgt+"rate_oracular_heal")
		@RangeDouble(min=0,max=1)
		public static double rate_oracular_heal=0.4;

		@LangKey(pgt+"factor_oracular_heal")
		@RangeDouble(min=0,max=1)
		public static double factor_oracular_heal=2;

		@RangeInt(min=1,max=50)
		@LangKey(pgt+"factor_peace_energetic_heal")
		public static int factor_peace_energetic_heal=1;

		@LangKey(pgt+"factor_pyroelectric_damage")
		@RangeDouble(min=0.1,max=5)
		public static double factor_pyroelectric_damage=1.8;

		@LangKey(pgt+"range_radiant")
		@RangeDouble(min=2,max=10)
		public static double range_radiant=5;

		@LangKey(pgt+"factor_soluble")
		@RangeInt(min=1,max=100)
		public static int factor_soluble=20;

		@LangKey(pgt+"factor_star_dashing_damage")
		@RangeDouble(min=0.5,max=10)
		public static double factor_star_dashing_damage=3;

		@LangKey(pgt+"factor_star_dashing_speed")
		@RangeDouble(min=0.01,max=0.5)
		public static double factor_star_dashing_speed=0.2f;

		@LangKey(pgt+"factor_star_dashing_amount")
		@RangeInt(min=1,max=8)
		public static int factor_star_dashing_amount=6;

		@LangKey(pgt+"factor_sun_power")
		@RangeInt(min=0,max=10)
		public static int factor_sun_power=1;

		@LangKey(pgt+"rate_thunder_waving")
		@RangeDouble(min=0,max=1)
		public static double rate_thunder_waving=0.2;

		@LangKey(pgt+"factor_thunder_waving_damage")
		@RangeDouble(min=0.5,max=10)
		public static double factor_thunder_waving_damage=3;

		@LangKey(pgt+"rate_undead_calling_break")
		@RangeDouble(min=0,max=1)
		public static double rate_undead_calling_break=0.08;

		@LangKey(pgt+"rate_undead_calling_hit")
		@RangeDouble(min=0,max=1)
		public static double rate_undead_calling_hit=0.08;

		@LangKey(pgt+"factor_watery_in")
		@RangeDouble(min=1,max=2)
		public static double factor_watery_in=1.4;

		@LangKey(pgt+"factor_watery_out")
		@RangeDouble(min=0.5,max=1)
		public static double factor_watery_out=0.6;
	}

	@Config(name=TinkersThings.MOD_ID+"_trait_armor",modid= TinkersThings.MOD_ID,category = "trait_armor")
	@LangKey(c+"trait_armor")
	public static class ArmorTraits
	{
		@RangeInt(min=1,max=20)
		@LangKey(pga+"factor_aquatic_heal")
		public static int factor_aquatic_heal=1;

		@RangeInt(min=1,max=20)
		@LangKey(pga+"factor_aquatic_damage")
		public static int factor_aquatic_damage=1;

		@RangeInt(min=1,max=1200)
		@LangKey(pga+"arsenic_poisonous_duration")
		public static int arsenic_poisonous_duration = 120;

		@RangeInt(min=1,max=1200)
		@LangKey(pga+"capacitor_duration")
		public static int capacitor_duration = 100;

		@RangeInt(min=1,max=1200)
		@LangKey(pga+"cavious_weakness_duration")
		public static int cavious_weakness_duration = 200;

		@RangeDouble(min=0,max=1)
		@LangKey(pga+"rate_chemical_instable_hit")
		public static double rate_chemical_instable_hit = 0.12;

//		@RangeDouble(min=0,max=1)
//		@LangKey(pga+"rate_creaky_hit")
//		public static double rate_creaky_hit = 0.1;
//
//		@RangeDouble(min=0,max=1)
//		@LangKey(pga+"rate_decoying_hit")
//		public static double rate_decoying_hit = 0.08;

		@LangKey(pga+"rate_carbonizing_drop")
		@RangeDouble(min=0,max=1)
		public static double rate_carbonizing_drop = 0.08;

		@LangKey(pga+"rate_devouring")
		@RangeDouble(min=0,max=1)
		public static double rate_devouring = 0.2;

		@LangKey(pga+"factor_devouring")
		@RangeDouble(min=0,max=2)
		public static double factor_devouring = 0.5;

		@LangKey(pga+"rate_hiding_hit")
		@RangeDouble(min=0,max=1)
		public static double rate_hiding_hit=0.2f;

		@LangKey(pga+"factor_hiding_hit")
		@RangeInt(min=1,max=1200)
		public static int factor_hiding_hit = 120;

		@LangKey(pga+"factor_hydrating_water")
		@RangeInt(min=0,max=50)
		public static int factor_hydrating_water = 10;

		@LangKey(pga+"factor_hydrating_rain")
		@RangeInt(min=0,max=50)
		public static int factor_hydrating_rain = 7;

		@LangKey(pga+"range_hyper")
		@RangeDouble(min=5,max=10)
		public static double range_hyper = 5;

		@LangKey(pga+"rate_meshing")
		@RangeDouble(min=0,max=1)
		public static double rate_meshing = 0.15;

		@LangKey(pga+"factor_meshing")
		@RangeInt(min=1,max=20)
		public static int factor_meshing = 6;

		@LangKey(pga+"rate_quick_freezing")
		@RangeDouble(min=0,max=1)
		public static double rate_quick_freezing = 0.4;

		@LangKey(pga+"rate_sliding")
		@RangeDouble(min=0,max=1)
		public static double rate_sliding = 0.15;

		@LangKey(pga+"factor_sliding")
		@RangeDouble(min=0.01,max=10)
		public static double factor_sliding = 5;

		@LangKey(pga+"rate_smooth_moving")
		@RangeDouble(min=0,max=1)
		public static double rate_smooth_moving = 0.5;

		@LangKey(pga+"rate_smooth_moving")
		@RangeDouble(min=0,max=1)
		public static double rate_smooth_standing = 0.2;

		@LangKey(pga+"range_surging")
		@RangeDouble(min=5,max=10)
		public static double range_surging = 5;

		@LangKey(pga+"range_widening")
		@RangeDouble(min=5,max=16)
		public static double range_widening = 8;

		@LangKey(pga+"factor_wither_flowing")
		@RangeInt(min=1,max=1200)
		public static int factor_wither_flowing = 160;
	}

	@Config(name=TinkersThings.MOD_ID+"_world_gens",modid= TinkersThings.MOD_ID,category = "world_gens")
	@LangKey(c+"world_gens")
	@SuppressWarnings("unused")
	public static class WorldGens // 世界生成相关设置
	{
		@LangKey(pwg+"enable_world_gen")
		public static boolean enable_world_gen=true;

		@LangKey(pwg+nameAltairium) // 河鼓
		public static String altairium="type=common; min_y=10; max_y=70; size=3; strategy_biome=ONLY_WHITELIST; biomes=$desert";

		@LangKey(pwg+nameBrumeJade) // 云玉
		public static String brume_jade="type=cloud; rate_chunk=0.015";

		@LangKey(pwg+nameBlackrock) // 黑石
		public static String blackrock="type=common; time_chunk=5; rate_single=0.7; size=20; min_y=40; max_y=80";

		@LangKey(pwg+nameBrokenBedrock) // 破碎基岩
		public static String broken_bedrock="type=bedrock; time_chunk=3; rate_single=0.6; size=8";

		@LangKey(pwg+nameChloroplast) // 叶绿矿
		public static String chloroplast="type=common; time_chunk=2; rate_single=0.1; size=3; min_y=60; max_y=70";

		@LangKey(pwg+nameCinnabar) // 水银
		public static String cinnabar="type=common; time_chunk=4; size=9; min_y=18; max_y=56";

		@LangKey(pwg+nameCordierite) // 堇青石
		public static String cordierite="type=common; time_chunk=6; rate_single=0.5; size=15; min_y=10; max_y=55; strategy_biome=ONLY_WHITELIST; biomes=minecraft:taiga_cold,minecraft:redwood_taiga,minecraft:taiga,minecraft:mutated_taiga_cold,minecraft:mutated_redwood_taiga,minecraft:mutated_taiga";

		@LangKey(pwg+nameCorundum) // 刚玉
		public static String corundum="type=common; time_chunk=4; rate_single=0.7; size=7; min_y=0; max_y=28; strategy_biome=ONLY_WHITELIST; biomes=$hill";

//		public static String furutorin; // 泠笛石

		@LangKey(pwg+nameHalleium) // 哈雷
		public static String halleium="type=common; time_chunk=2; rate_single=0.5; size=4; min_y=15; max_y=18";

//		public static String heavesand=""; // 沉沙石

		@LangKey(pwg+nameHothium) // 霍斯
		public static String hothium="type=common; time_chunk=5; rate_single=0.6; size=3; min_y=0; max_y=60; strategy_biome=ONLY_WHITELIST; biomes=$cold";

		@LangKey(pwg+nameIcelandSpar) // 冰州石
		public static String iceland_spar="type=common; time_chunk=8; rate_single=0.6; size=3; min_y=0; max_y=50; strategy_biome=ONLY_WHITELIST; biomes=$cold";

		@LangKey(pwg+nameIcelit) // 冰明
		public static String icelit="type=common; time_chunk=6; rate_single=0.8; size=6; min_y=5; max_y=50; strategy_biome=ONLY_WHITELIST; biomes=$sea";

//		@LangKey(nameImitatium) // 拟素
//		public static String imitatium="type=common; time_chunk=";

		@LangKey(pwg+nameImmersedSilver) // 沉银
		public static String immersed_silver="type=common; time_chunk=8; rate_single=0.5; size=7; min_y=0; max_y=30; strategy_biome=ONLY_WHITELIST; biomes=$sea";

		@LangKey(pwg+nameInertWitherium) // 惰性凋零
		public static String inert_witherium="type=common; time_chunk=2; min_y=5; max_y=128; rate_single=0.5; size=3; strategy_dim=ONLY_WHITELIST; dims=-1; selector=netherrack";

		@LangKey(pwg+nameInkPowder) // 墨粉
		public static String ink_powder="type=common; rate_chunk=0.5; size=10; min_y=40; max_y=80";

		@LangKey(pwg+nameLavaCrystal) // 岩浆水晶
		public static String lava_crystal="type=common; time_chunk=10; size=3; min_y=32; max_y=33; strategy_dim=ONLY_WHITELIST; dims=-1; selector=lava";

		@LangKey(pwg+nameLizanite) // 丽辰石
		public static String lizanite="type=common; time_chunk=4; rate_single=0.5; size=5; min_y=6; max_y=50; strategy_dim=ONLY_WHITELIST; dims=-1; selector=netherrack";

		@LangKey(pwg+nameMoonStone) // 月光石
		public static String moon_stone="type=common; time_chunk=3; rate_single=0.8; size=5; min_y=5; max_y=50; strategy_biome=ONLY_WHITELIST; biomes=$cold";

		@LangKey(pwg+nameNitre) // 硝石
		public static String nitre="type=common; time_chunk=4; size=8; min_y=30; max_y=50; strategy_biome=ONLY_WHITELIST; biomes=$desert";

		@LangKey(pwg+nameOpal) // 欧珀
		public static String opal="type=common; size=5; min_y=0; max_y=24; strategy_biome=ONLY_WHITELIST; biome=$desert";

		@LangKey(pwg+namePolarium) // 勾陈
		public static String polarium="type=common; time_chunk=6; size=5; min_y=0; max_y=180; strategy_dim=ONLY_WHITELIST; dims=1; selector=end_stone";

		@LangKey(pwg+namePrehnite) // 葡萄石
		public static String prehnite="type=common; time_chunk=8; rate_single=0.3; size=5; min_y=0; max_y=11";

		@LangKey(pwg+nameProustite) // 硫砷银矿
		public static String proustite="type=common; time_chunk=5; size=9; min_y=20; max_y=100; strategy_dim=ONLY_WHITELIST; dims=-1; selector=netherrack";

		@LangKey(pwg+namePyrophyllite) // 叶蜡石
		public static String pyrophyllite="type=common; time_chunk=4; size=6; min_y=30; max_y=80; strategy_biome=ONLY_WHITELIST; biomes=$forest";

		@LangKey(pwg+nameRedins) // 虹辉石
		public static String redins="type=common; time_chunk=3; rate_single=0.8; size=5; min_y=0; max_y=40";

		@LangKey(pwg+"solid_dirt") // 硬实泥土
		public static String solid_dirt="type=common; time_chunk=4; rate_single=0.3; size=8; min_y=10; max_y=128; selector=dirt";

		@LangKey(pwg+"solid_sand") // 硬实沙子
		public static String solid_sand="type=common; time_chunk=4; rate_single=0.3; size=8; min_y=10; max_y=128; selector=sand";

		@LangKey(pwg+"solid_stone") // 硬实石头
		public static String solid_stone="type=common; time_chunk=4; rate_single=0.3; size=8; min_y=10; max_y=128; selector=stone";

		@LangKey(pwg+nameSpinel) // 尖晶石矿
		public static String spinel="type=common; time_chunk=5; rate_single=0.5; size=4; min_y=40; max_y=100; strategy_biome=ONLY_WHITELIST; biomes=$forest,$mesa,$hill";

		@LangKey(pwg+nameSteamium) // 气钢
		public static String steamium="type=common; time_chunk=7; rate_single=0.7; size=9; min_y=0; max_y=60";

		@LangKey(pwg+nameStellarium) // 恒星
		public static String stellarium="type=common; time_chunk=0.1; size=3; min_y=20; max_y=60; strategy_biome=ONLY_WHITELIST; biomes=$desert";

		@LangKey(pwg+nameSunStone) // 日光石
		public static String sun_stone="type=common; time_chunk=2; rate_single=0.8; size=3; min_y=30; max_y=100; strategy_biome=ONLY_WHITELIST; biomes=$hot";

		@LangKey(pwg+nameTalcum) // 滑石
		public static String talcum="type=common; time_chunk=10; rate_single=0.7; size=9; min_y=20; max_y=64; strategy_biome=ONLY_WHITELIST; biomes=minecraft:swampland";

		@LangKey(pwg+nameTanatonium) // 拉特特妮姆
		public static String tanatonium="type=common; time_chunk=6; rate_single=0.6; size=6; min_y=5; max_y=50; strategy_biome=ONLY_WHITELIST; biomes=$sea";

		@LangKey(pwg+nameTitanium) // 金红石 钛
		public static String titanium="type=common; time_chunk=3; rate_single=0.8; size=6; min_y=0; max_y=60; strategy_biome=ONLY_WHITELIST; biomes=$plain";

		@LangKey(pwg+nameTonium) // 钝金
		public static String tonium="type=common; time_chunk=2; size=3; min_y=0; max_y=30; strategy_biome=ONLY_WHITELIST; biomes=minecraft:deep_ocean";

		@LangKey(pwg+nameTopaz) // 托帕石
		public static String topaz="type=common; time_chunk=5; size=3; min_y=0; max_y=64; strategy_biome=ONLY_WHITELIST; biomes=$hill";

		@LangKey(pwg+nameTourmaline) // 电气石矿
		public static String tourmaline="type=common; time_chunk=2; rate_single=0.8; size=6; min_y=10; max_y=40";

		@LangKey(pwg+nameTreeRoot) // 树根
		public static String tree_root="type=tree_root";

		@LangKey(pwg+nameVibratingCrystal) // 振晶
		public static String vibrating_crystal="type=common; time_chunk=7; rate_single=0.5; size=5; min_y=5; max_y=160; strategy_dim=ONLY_WHITELIST; dims=1; selector=end_stone";
	}

	@Config(name=TinkersThings.MOD_ID+"_biome_groups",modid= TinkersThings.MOD_ID,category = "biome_groups")
	@LangKey(c+"biome_groups")
	public static class BiomeGroups
	{
		// 组合
		// 环境
		@LangKey(pbg+"plain")
		public static String[] biomes_plain = { biome_plains, biome_savanna_rock, biome_savanna, biome_mutated_ice_flats, biome_ice_flats, biome_mutated_plains };

		@LangKey(pbg+"forest")
		public static String[] biomes_forest = { biome_jungle, biome_jungle_edge, biome_forest, biome_birch_forest, biome_taiga_cold, biome_redwood_taiga, biome_taiga, biome_roofed_forest, biome_mutated_taiga_cold, biome_mutated_jungle_edge, biome_mutated_savanna_rock, biome_mutated_redwood_taiga, biome_mutated_forest, biome_mutated_taiga, biome_mutated_birch_forest, biome_mutated_roofed_forest, biome_mutated_jungle, biome_mutated_savanna };

		@LangKey(pbg+"mesa")
		public static String[] biomes_mesa = {biome_mesa, biome_mesa_rock, biome_mesa_clear_rock, biome_mutated_mesa, biome_mutated_mesa_clear_rock, biome_mutated_mesa_rock };

		@LangKey(pbg+"hill")
		public static String[] biomes_hill = { biome_taiga_cold_hills, biome_forest_hills, biome_ice_mountains, biome_jungle_hills, biome_taiga_hills, biome_redwood_taiga_hills, biome_extreme_hills_with_trees, biome_extreme_hills, biome_smaller_extreme_hills, biome_mutated_extreme_hills, biome_mutated_redwood_taiga_hills, biome_mutated_extreme_hills_with_trees, biome_mutated_birch_forest_hills, biome_birch_forest_hills };

		@LangKey(pbg+"desert")
		public static String[] biomes_desert = { biome_desert, biome_desert_hills, biome_mutated_desert };

		@LangKey(pbg+"water")
		public static String[] biomes_water = { biome_river, biome_frozen_river, biome_swampland, biome_mutated_swampland };

		@LangKey(pbg+"sea")
		public static String[] biomes_sea = { biome_frozen_ocean, biome_ocean, biome_deep_ocean, biome_cold_beach, biome_stone_beach, biome_beaches };

		@LangKey(pbg+"water_sea")
		public static String[] biomes_water_sea = { biome_river, biome_frozen_river, biome_swampland, biome_mutated_swampland, biome_frozen_ocean, biome_ocean, biome_deep_ocean, biome_cold_beach, biome_stone_beach, biome_beaches };

		@LangKey(pbg+"special")
		public static String[] biomes_special = { biome_void, biome_hell, biome_sky, biome_mushroom_island_shore, biome_mushroom_island };

		// 温度
		@LangKey(pbg+"hot")
		public static String[] biomes_hot = {biome_desert,biome_desert_hills,biome_mutated_savanna,biome_mutated_savanna_rock,biome_mesa,biome_mesa_rock,biome_mesa_clear_rock,biome_mutated_desert,biome_mutated_savanna,biome_mutated_savanna_rock,biome_mutated_mesa,biome_mutated_mesa_clear_rock,biome_mutated_mesa_rock};

		@LangKey(pbg+"warm")
		public static String[] biomes_warm = {biome_plains,biome_forest,biome_swampland,biome_river,biome_beaches,biome_forest_hills,biome_jungle,biome_jungle_hills,biome_jungle_edge,biome_birch_forest,biome_birch_forest_hills,biome_roofed_forest,biome_mutated_plains,biome_mutated_forest,biome_mutated_swampland,biome_mutated_jungle,biome_mutated_jungle_edge,biome_mutated_birch_forest,biome_mutated_birch_forest_hills,biome_mutated_roofed_forest};

		@LangKey(pbg+"common")
		public static String[] biomes_common = {biome_extreme_hills,biome_taiga,biome_taiga_hills,biome_smaller_extreme_hills,biome_stone_beach,biome_redwood_taiga,biome_redwood_taiga_hills,biome_extreme_hills_with_trees,biome_mutated_extreme_hills,biome_mutated_taiga,biome_mutated_redwood_taiga,biome_mutated_redwood_taiga_hills,biome_mutated_extreme_hills_with_trees};

		@LangKey(pbg+"cool")
		public static String[] biomes_cool = {biome_ocean,biome_mushroom_island,biome_mushroom_island_shore,biome_deep_ocean};

		@LangKey(pbg+"cold")
		public static String[] biomes_cold = {biome_frozen_ocean,biome_frozen_river,biome_ice_flats,biome_ice_mountains,biome_cold_beach,biome_taiga_cold,biome_taiga_cold_hills,biome_mutated_ice_flats,biome_mutated_taiga_cold};

		/**
		 * 获取生物群系列表, 会自动将参数中的组合key转换为对应的生物群系
		 * @param keys 生物群系id列表, 可以包含组合key
		 * @return 组装后的生物群系列表
		 */
		public static String[] getBiomes(String...keys)
		{
			if(keys==null || keys.length==0) return new String[0];

			Set<String> set=new HashSet<>();
			FOR_KEY:for(String keyTemp:keys)
			{
				if(keyTemp==null) continue;
				String[] keysTemp;
				SWITCH: switch (keyTemp)
				{
					case "$plain": keysTemp= biomes_plain; break SWITCH;
					case "$forest": keysTemp= biomes_forest; break SWITCH;
					case "$mesa": keysTemp= biomes_mesa; break SWITCH;
					case "$hill": keysTemp= biomes_hill; break SWITCH;
					case "$desert": keysTemp=biomes_desert; break SWITCH;
					case "$water": keysTemp=biomes_water; break SWITCH;
					case "$sea": keysTemp=biomes_sea; break SWITCH;
					case "$water_sea": keysTemp=biomes_water_sea; break SWITCH;
					case "$special": keysTemp=biomes_special; break SWITCH;
					case "$hot" : keysTemp=biomes_hot; break SWITCH;
					case "$warm" : keysTemp=biomes_warm; break SWITCH;
					case "$common" : keysTemp=biomes_common; break SWITCH;
					case "$cool" : keysTemp=biomes_cool; break SWITCH;
					case "$cold" : keysTemp=biomes_cold; break SWITCH;
					default:
					{
						set.add(keyTemp);
						continue FOR_KEY;
					}
				}
				set.addAll(Arrays.asList(keysTemp));
			}
			return set.toArray(new String[0]);
		}
	}

}
