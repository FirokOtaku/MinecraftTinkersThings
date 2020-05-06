package firok.tiths.common;

import firok.tiths.TinkersThings;
import firok.tiths.util.VersionPhase;
import net.minecraftforge.common.config.Config;

import static net.minecraftforge.common.config.Config.*;

// 配置文件
public final class Configs
{
	private Configs() {}

	static final String c="config.tiths.";
	static final String pg="config.tiths.general.";
	static final String pgp="config.tiths.gameplay.";
	@Config(modid= TinkersThings.MOD_ID)
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
		 * 启用饰品联动
		 */
		@LangKey(pg+"enable_baubles")
		@RequiresMcRestart
		public static boolean enable_baubles=true;

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
	}


	@Config(modid= TinkersThings.MOD_ID,category = "gameplay")
	@LangKey(c+"gameplay")
	public static class Gameplay
	{
		/**
		 * 启用奢华合金简易合成
		 */
		@LangKey(pg+"enable_royal")
		@RequiresMcRestart
		public static boolean enable_royal_alloy_easy_crafting=false;
	}

	@Config(modid= TinkersThings.MOD_ID,category = "traits")
	@LangKey(c+"trait")
	public static class Traits // 属性相关的设置
	{
		@LangKey(pgp+"rate_birefringent_damage")
		@RangeDouble(min=0,max=1)
		public static double rate_birefringent_damage = 0.3;

		@LangKey(pgp+"rate_blowing")
		@RangeDouble(min=0,max=1)
		public static double rate_blowing = 0.25;

		@LangKey(pgp+"blacklist_blowing_entity")
		public static String[] blacklist_blowing_entity = new String[0];

		@LangKey(pgp+"rate_carbonizing_transform")
		@RangeDouble(min=0,max=1)
		public static double rate_carbonizing_transform = 0.05;

		@LangKey(pgp+"rate_carbonizing_drop")
		@RangeDouble(min=0,max=1)
		public static double rate_carbonizing_drop = 0.08;

		@LangKey(pgp+"rate_chemical_instable_attack")
		@RangeDouble(min=0,max=1)
		public static double rate_chemical_instable_attack = 0.15;

		@LangKey(pgp+"rate_chemical_instable_break")
		@RangeDouble(min=0,max=1)
		public static double rate_chemical_instable_break = 0.06;

		@LangKey(pgp+"factor_clustering_durability")
		@RangeInt(min=2,max=10000)
		public static int factor_clustering_durability = 550;

		@LangKey(pgp+"factor_clustering_atk")
		@RangeDouble(min=0,max=4)
		public static double factor_clustering_atk = 2;

		@LangKey(pgp+"factor_clustering_speed")
		@RangeDouble(min=0,max=4)
		public static double factor_clustering_speed = 1;

		@LangKey(pgp+"factor_clustering_speed_mining")
		@RangeDouble(min=0,max=4)
		public static double factor_clustering_speed_mining = 1;

		@LangKey(pgp+"factor_clustering_speed_draw")
		@RangeDouble(min=1,max=4)
		public static double factor_clustering_speed_draw = 0.08;

		@LangKey(pgp+"rate_creaky_use")
		@RangeDouble(min=0,max=1)
		public static double rate_creaky_use = 0.05;

		@LangKey(pgp+"rate_decoying_use")
		@RangeDouble(min=0,max=1)
		public static double rate_decoying_use = 0.08;

		public static double factor_dichroic_light_mid = 7;

		public static double factor_dichroic_light = 35;

		@LangKey(pgp+"factor_dragon_killer_damage_base")
		@RangeDouble(min=0,max=100)
		public static double factor_dragon_killer_damage_base=4;

		@LangKey(pgp+"factor_dragon_killer_damage_percent")
		@RangeDouble(min=0,max=5)
		public static double factor_dragon_killer_damage_percent=1.25;

		@LangKey(pgp+"range_extreme_freezing")
		@RangeDouble(min=1,max=10)
		public static double range_extreme_freezing=5;

		@LangKey(pgp+"enable_gluttonic_clear")
		public static boolean enable_gluttonic_clear=true;

		@LangKey(pgp+"factor_gluttonic_food")
		@RangeInt(min=0,max=100)
		public static int factor_gluttonic_food=4;

		@LangKey(pgp+"factor_gorgeous_min")
		@RangeDouble(min=1,max=10)
		public static double factor_gorgeous_min=1.1;

		@LangKey(pgp+"factor_gorgeous_max")
		@RangeDouble(min=1,max=10)
		public static double factor_gorgeous_max=1.2;

		@LangKey(pgp+"factor_hemolytic_damage")
		@RangeDouble(min=1,max=10)
		public static double factor_hemolytic_damage=1.15;

		@LangKey(pgp+"factor_hemolytic_limit")
		@RangeDouble(min=0,max=100)
		public static double factor_hemolytic_limit=5;

		@LangKey(pgp+"rate_hemolytic")
		@RangeDouble(min=0,max=1)
		public static double rate_hemolytic=0.5;

		@LangKey(pgp+"factor_hemolytic_repair")
		@RangeDouble(min=1,max=10)
		public static double factor_hemolytic_repair=5;

		@LangKey(pgp+"factor_inky")
		@RangeInt(min=1,max=6000)
		public static int factor_inky=100;

		@LangKey(pgp+"factor_life_inspiring_low")
		@RangeDouble(min=0.01,max=1)
		public static double factor_life_inspiring_low=0.35;

		@LangKey(pgp+"factor_life_inspiring_danger")
		@RangeDouble(min=0.01,max=1)
		public static double factor_life_inspiring_danger=0.15;

		@LangKey(pgp+"factor_luxurious_durability")
		@RangeDouble(min=0,max=1)
		public static double factor_luxurious_durability=0.2;

		@LangKey(pgp+"factor_moon_power")
		@RangeInt(min=0,max=10)
		public static int factor_moon_power=1;

		@LangKey(pgp+"range_nature_blessing_xz")
		@RangeInt(min=1,max=6)
		public static int range_nature_blessing_xz=4;

		@LangKey(pgp+"range_nature_blessing_y")
		@RangeInt(min=1,max=4)
		public static int range_nature_blessing_y=2;

		@LangKey(pgp+"count_nature_blessing")
		@RangeInt(min=1,max=20)
		public static int count_nature_blessing=8;

		@LangKey(pgp+"factor_nature_blessing_repair")
		@RangeInt(min=1,max=40)
		public static int factor_nature_blessing_repair=12;

		@LangKey(pgp+"enable_oracular_remove_target")
		public static boolean enable_oracular_remove_target=true;

		@LangKey(pgp+"enable_oracular_remove_player")
		public static boolean enable_oracular_remove_player=true;

		@LangKey(pgp+"rate_oracular_heal")
		@RangeDouble(min=0,max=1)
		public static double rate_oracular_heal=0.4;

		@LangKey(pgp+"factor_oracular_heal")
		@RangeDouble(min=0,max=1)
		public static double factor_oracular_heal=2;

		@RangeInt(min=1,max=50)
		@LangKey(pgp+"factor_peace_energetic_heal")
		public static int factor_peace_energetic_heal=1;

		@LangKey(pgp+"factor_pyroelectric_damage")
		@RangeDouble(min=0.1,max=5)
		public static double factor_pyroelectric_damage=1.8;

		@LangKey(pgp+"range_radiant")
		@RangeDouble(min=2,max=10)
		public static double range_radiant=5;

		@LangKey(pgp+"factor_soluble")
		@RangeInt(min=1,max=100)
		public static int factor_soluble=20;

		@LangKey(pgp+"factor_star_dashing_damage")
		@RangeDouble(min=0.5,max=10)
		public static double factor_star_dashing_damage=3;

		@LangKey(pgp+"factor_star_dashing_speed")
		@RangeDouble(min=0.01,max=0.5)
		public static double factor_star_dashing_speed=0.2f;

		@LangKey(pgp+"factor_star_dashing_amount")
		@RangeInt(min=1,max=8)
		public static int factor_star_dashing_amount=6;

		@LangKey(pgp+"factor_sun_power")
		@RangeInt(min=0,max=10)
		public static int factor_sun_power=1;

		@LangKey(pgp+"rate_thunder_waving")
		@RangeDouble(min=0,max=1)
		public static double rate_thunder_waving=0.2;

		@LangKey(pgp+"factor_thunder_waving_damage")
		@RangeDouble(min=0.5,max=10)
		public static double factor_thunder_waving_damage=3;

		@LangKey(pgp+"rate_undead_calling_break")
		@RangeDouble(min=0,max=1)
		public static double rate_undead_calling_break=0.08;

		@LangKey(pgp+"rate_undead_calling_hit")
		@RangeDouble(min=0,max=1)
		public static double rate_undead_calling_hit=0.08;

		@LangKey(pgp+"factor_watery_in")
		@RangeDouble(min=1,max=2)
		public static double factor_watery_in=1.4;

		@LangKey(pgp+"factor_watery_out")
		@RangeDouble(min=0.5,max=1)
		public static double factor_watery_out=0.6;
	}

	public static class ArmorTraits
	{
		@RangeDouble(min=0,max=1)
		public static double rate_hiding_hit=0.2f;

		@RangeDouble(min=0,max=1)
		public static double rate_chemical_instable_hit = 0.12;

		@RangeDouble(min=0,max=1)
		public static double rate_creaky_hit = 0.1;

		@RangeDouble(min=0,max=1)
		public static double rate_decoying_hit = 0.08;

	}

}
