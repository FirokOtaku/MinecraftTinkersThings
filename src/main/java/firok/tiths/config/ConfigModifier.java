package firok.tiths.config;

import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class ConfigModifier
{
	public static ForgeConfigSpec INSTANCE;

	public static BooleanValue enable_blow_player;
	public static DoubleValue rate_blowing;

	public static DoubleValue factor_chemical_instable;
	public static DoubleValue rate_chemical_instable_attack;
	public static DoubleValue rate_chemical_instable_break;


	static {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		builder.comment("modifier settings").push("modifiers");
		enable_blow_player = builder.comment("Test config value").define("enable_blow_player", true);

		factor_chemical_instable = builder.comment("Test config value").defineInRange("factor_chemical_instable", 4, 0, 8);
		rate_chemical_instable_attack = builder.comment("").defineInRange("rate_chemical_instable_attack", 0.2, 0, 1);
		rate_chemical_instable_break = builder.comment("").defineInRange("rate_chemical_instable_break", 0.2, 0, 1);

		builder.pop();
		INSTANCE = builder.build();
	}
}
