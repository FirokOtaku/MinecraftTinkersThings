package firok.tiths.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

import java.util.ArrayList;

public class ConfigModifier
{
	public static ForgeConfigSpec INSTANCE;

	public static BooleanValue enable_blow_player;
	public static DoubleValue rate_blowing;
	public static EntityTypeValue blocklist_blowing;

	public static DoubleValue factor_chemical_instable;
	public static DoubleValue rate_chemical_instable_attack;
	public static DoubleValue rate_chemical_instable_break;

	public static DoubleValue rate_creaky_use;

	public static DoubleValue rate_decoying_use;

	public static DoubleValue factor_dragon_killer_damage_base;
	public static DoubleValue factor_dragon_killer_damage_percent;

	public static ConfigValue<Double> rate_carbonizing_transform;

	public static DoubleValue range_extreme_freezing;

	public static DoubleValue factor_gorgeous_min;
	public static DoubleValue factor_gorgeous_max;

	public static DoubleValue factor_hemolytic_damage;

	public static DoubleValue factor_hemolytic_limit;

	public static DoubleValue rate_hemolytic;

	public static DoubleValue factor_hemolytic_repair;


	static {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		builder.comment("modifier settings").push("modifiers");
		enable_blow_player = builder.comment("Test config value")
				.define("enable_blow_player", true);
//		blocklist_blowing = ConfigListenerManager.addListener(
//				new EntityTypeValue(
//						ConfigUtils.defineList(
//								builder,
//								"Blow entity blacklist",
//								"blow_blacklist", new ArrayList<>()
//						)
//				));

		factor_chemical_instable = builder.comment("Test config value")
				.defineInRange("factor_chemical_instable", 1D, 0, 5);
		rate_chemical_instable_attack = builder.comment("")
				.defineInRange("rate_chemical_instable_attack", 0.2, 0, 1);
		rate_chemical_instable_break = builder.comment("")
				.defineInRange("rate_chemical_instable_break", 0.2, 0, 1);

		rate_creaky_use = builder.comment("")
				.defineInRange("rate_creaky_use", 0.2, 0, 1);

		rate_decoying_use = builder.comment("")
				.defineInRange("rate_decoying_use", 0.2, 0, 1);

		factor_dragon_killer_damage_base = builder.comment("")
				.defineInRange("factor_dragon_killer_damage_base", 4., 0, 8);
		factor_dragon_killer_damage_percent = builder.comment("")
				.defineInRange("factor_dragon_killer_damage_percent", 0.2, 0, 1);

		rate_carbonizing_transform = ConfigUtils.defineDouble(builder,
				"",
				"rate_carbonizing_transform",
				0.2, 0, 1);

		range_extreme_freezing = builder.comment("")
				.defineInRange("range_extreme_freezing", 4f, 1, 10);

		factor_gorgeous_min = builder.comment("")
				.defineInRange("factor_gorgeous_min", 1.1f, 1, 10);
		factor_gorgeous_max = builder.comment("")
				.defineInRange("factor_gorgeous_max", 1.2f, 1, 10);

		factor_hemolytic_damage = builder.comment("")
				.defineInRange("factor_hemolytic_damage", 1.15f, 1, 10);
		factor_hemolytic_limit = builder.comment("")
				.defineInRange("factor_hemolytic_limit", 5f, 0, 100);
		rate_hemolytic = builder.comment("")
				.defineInRange("rate_hemolytic", 0.5f, 0, 1);
		factor_hemolytic_repair = builder.comment("")
				.defineInRange("factor_hemolytic_repair", 5f, 1, 10);

		builder.pop();
		INSTANCE = builder.build();
	}
}
