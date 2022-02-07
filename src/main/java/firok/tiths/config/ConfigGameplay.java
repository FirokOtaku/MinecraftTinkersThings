package firok.tiths.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ConfigGameplay
{
	public static ForgeConfigSpec INSTANCE;

	public static IntValue factor_momentum_unit;

	static {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		builder.comment("gameplay settings").push("gameplay");

		factor_momentum_unit = builder.comment("")
				.defineInRange("factor_momentum_unit", 50, 1, 100);

		builder.pop();
		INSTANCE = builder.build();
	}
}
