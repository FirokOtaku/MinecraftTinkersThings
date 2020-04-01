package firok.tiths.common;

import firok.tiths.TinkersThings;
import net.minecraftforge.common.config.Config;

import static net.minecraftforge.common.config.Config.*;
import static firok.tiths.common.Keys.*;

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

		@RequiresMcRestart
		public static boolean enable_royal_alloy_easy_crafting=false;

		@LangKey(confEnableMaterialCustomization)
		@RequiresMcRestart
		public static boolean enable_material_customization=true;

		@LangKey(confEnableOreGenCustomization)
		@RequiresMcRestart
		public static boolean enable_ore_gen_customization=true;
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


}
