package firok.tiths.config;

import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class ConfigModifier
{
	public static ForgeConfigSpec INSTANCE;

	public static BooleanValue enable_blow_player;
	public static DoubleValue rate_blowing;

	static {
		ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
		COMMON_BUILDER.comment("modifier settings").push("modifiers");
		enable_blow_player = COMMON_BUILDER.comment("Test config value").define("enable_blow_player", true);

		COMMON_BUILDER.pop();
		INSTANCE = COMMON_BUILDER.build();
	}
}
