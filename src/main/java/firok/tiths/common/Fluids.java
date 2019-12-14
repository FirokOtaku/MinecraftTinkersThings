package firok.tiths.common;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.fluid.FluidMolten;

import static firok.tiths.common.Keys.*;

@SuppressWarnings("unused")
public class Fluids
{
	public static final FluidMolten moltenRoyalAlloy=$(nameRoyalAlloy, colorRoyalAlloy);
	public static final FluidMolten moltenStellarium=$(nameStellarium, colorStellarium);
	public static final FluidMolten moltenCinnabar=$(nameCinnabar, colorCinnabar);
	public static final FluidMolten moltenImmersedSilver=$(nameImmersedSilver, colorImmersedSilver);
	public static final FluidMolten moltenMithril=$(nameMithril, colorMithril);
	public static final FluidMolten moltenAdamantine=$(nameAdamantine, colorAdamantine);
	public static final FluidMolten moltenInertWitherium=$(nameInertWitherium, colorInertWitherium);
	public static final FluidMolten moltenWitherium=$(nameWitherium, colorWitherium);
	public static final FluidMolten moltenPolarium=$(namePolarium, colorPolarium);
	public static final FluidMolten moltenHalleium=$(nameHalleium, colorHalleium);
	public static final FluidMolten moltenAltairium=$(nameAltairium, colorAltairium);
	public static final FluidMolten moltenCocoa=$(nameCocoa, colorCocoa);
	public static final FluidMolten moltenTitanium=$(nameTitanium, colorTitanium);

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
