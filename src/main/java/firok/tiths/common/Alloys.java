package firok.tiths.common;

import firok.tiths.TinkersThings;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.shared.TinkerFluids;

import static slimeknights.tconstruct.library.materials.Material.VALUE_Ingot;

public class Alloys
{
	public static void registerAlloys()
	{
//		TinkersThings.log("register alloys...");
		// 奢华合金
		registerAlloy(
				new FluidStack(Fluids.moltenRoyalAlloy, VALUE_Ingot*3),
				new FluidStack(TinkerFluids.iron,VALUE_Ingot*2),
				new FluidStack(TinkerFluids.gold,VALUE_Ingot)
				);
		// 艾瑞西亚
		registerAlloy(
				new FluidStack(Fluids.moltenIrisia, VALUE_Ingot * 2),
				new FluidStack(Fluids.moltenStellarium,VALUE_Ingot),
				new FluidStack(Fluids.moltenHothium,VALUE_Ingot)
		);
		// 神谕钢
		registerAlloy(
				new FluidStack(Fluids.moltenAltairium, VALUE_Ingot),
				new FluidStack(Fluids.moltenPolarium, VALUE_Ingot),
				new FluidStack(Fluids.moltenOraclium, VALUE_Ingot * 2)
		);
	}

	private static void registerAlloy(FluidStack output,FluidStack...inputs)
	{
		if(output!=null && inputs!=null && inputs.length>=2)
		{
			TinkerRegistry.registerAlloy(output,inputs);
		}
	}
}
