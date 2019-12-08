package firok.tiths.common;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.fluid.FluidMolten;

import static firok.tiths.util.Keys.*;

@SuppressWarnings("unused")
public class Fluids
{
	public static FluidMolten moltenRoyalAlloy=$(nameRoyalAlloy, colorRoyalAlloy);
	public static FluidMolten moltenStellarium=$(nameStellarium, colorStellarium);
	public static FluidMolten moltenCinnabar=$(nameCinnabar, colorCinnabar);
	public static FluidMolten moltenImmersedSilver=$(nameImmersedSilver, colorImmersedSilver);
	public static FluidMolten moltenMithril=$(nameMithril, colorMithril);
	public static FluidMolten moltenAdamantine=$(nameAdamantine, colorAdamantine);
	public static FluidMolten moltenInertWitherium=$(nameInertWitherium, colorInertWitherium);
	public static FluidMolten moltenWitherium=$(nameWitherium, colorWitherium);

	static FluidMolten $(String name,int color)
	{
		return $(name,color,1000,10,10000,2000);
	}
	static FluidMolten $(String name,int color,int temp,int lumen,int visc,int dens)
	{
		FluidMolten fluid=new FluidMolten(
				name,color,
				new ResourceLocation("tconstruct:blocks/fluids/molten_metal"),
				new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow")
				);
		fluid.setTemperature(temp);
		fluid.setLuminosity(lumen);
		fluid.setViscosity(visc);
		fluid.setDensity(dens);
		return fluid;
	}
}
