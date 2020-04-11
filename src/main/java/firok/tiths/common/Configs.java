package firok.tiths.common;

import firok.tiths.TinkersThings;
import net.minecraftforge.common.config.Config;

import static firok.tiths.common.Keys.*;
import static net.minecraftforge.common.config.Config.*;

// 配置文件
public class Configs
{
	@Config(modid= TinkersThings.MOD_ID)
	@LangKey(cateGeneral)
	public static class General // 全局设置
	{
		@LangKey(confEnableConarm)
		@RequiresMcRestart
		public static boolean enable_conarm=true;

		@RequiresMcRestart
		public static boolean enable_baubles=true;

		/**
		 * 启用严格模式
		 * 对于玩家, 禁用严格模式可以减少游戏启动时由于各类id冲突等问题造成的崩溃;
		 * 对于开发者, 启用严格模式以寻找需要修正的问题
		 */
		@RequiresMcRestart
		public static boolean enable_strict_mode=true;

		@RequiresMcRestart
		public static boolean enable_royal_alloy_easy_crafting=false;

		@LangKey(confEnableMaterialCustomization)
		@RequiresMcRestart
		public static boolean enable_material_customization=true;

		@LangKey(confEnableOreGenCustomization)
		@RequiresMcRestart
		public static boolean enable_ore_gen_customization=true;

		/**
		 * 启用工具合成方法
		 */
		public static boolean enable_tool_craft_functions=true;
	}

	@Config(modid= TinkersThings.MOD_ID,category = "traits")
	@LangKey(cateTraitsBase)
	public static class Traits // 属性相关的设置
	{
		@LangKey(confTraitRateBirefringent)
		@RangeDouble(min=0,max=1)
		public static double rate_birefringent_damage = 0.3;

		@RangeDouble(min=0,max=1)
		public static double rate_carbonizing_transform = 0.05;

		@RangeDouble(min=0,max=1)
		public static double rate_carbonizing_drop = 0.08;

		@RangeDouble(min=0,max=1)
		public static double rate_chemical_instable_attack = 0.15;

		@RangeDouble(min=0,max=1)
		public static double rate_chemical_instable_break = 0.06;

		@RangeDouble(min=0,max=1)
		public static double rate_chemical_instable_hit = 0.12;

		public static int factor_clustering_durability = 550;

		public static double factor_clustering_atk = 2;

		public static double factor_clustering_speed = 1;

		public static double factor_clustering_speed_mining = 2;

		public static double factor_clustering_speed_draw = 0.08;

		@RangeDouble(min=0,max=1)
		public static double rate_creaky_use = 0.05;

		@RangeDouble(min=0,max=1)
		public static double rate_creaky_hit = 0.1;

		@RangeDouble(min=0,max=1)
		public static double rate_decoying_use = 0.08;

		@RangeDouble(min=0,max=1)
		public static double rate_decoying_hit = 0.08;

		public static double factor_dichroic_light_mid = 7;

		public static double factor_dichroic_light = 35;

		@RangeDouble(min=0,max=100)
		public static double factor_dragon_killer_damage_base=4;

		@RangeDouble(min=0,max=5)
		public static double factor_dragon_killer_damage_percent=1.25;
	}

	public static class ArmorTraits
	{
		@RangeDouble(min=0,max=1)
		public static double rate_hiding_hit=0.2f;
	}
}
