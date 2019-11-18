package firok.mtim.common;

import firok.mtim.MoreTinkersMaterials;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.shared.TinkerFluids;

import static slimeknights.tconstruct.library.materials.Material.VALUE_Ingot;

public class Alloys
{
	public static void registerAlloys()
	{
		MoreTinkersMaterials.log("注册合金...");
		registerAlloy(new FluidStack(Fluids.moltenRoyalAlloy, VALUE_Ingot*3),
				new FluidStack(TinkerFluids.iron,VALUE_Ingot*2),
				new FluidStack(TinkerFluids.gold,VALUE_Ingot)
				);
	}

	static void registerAlloy(FluidStack output,FluidStack...inputs)
	{
		if(output!=null && inputs!=null && inputs.length>=2)
		{
			TinkerRegistry.registerAlloy(output,inputs);
		}
	}
}
