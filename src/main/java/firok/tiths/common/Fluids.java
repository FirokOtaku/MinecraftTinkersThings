package firok.tiths.common;

import firok.tiths.util.RegSmelteryFuel;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.fluid.FluidMolten;

import static firok.tiths.common.Keys.*;

@SuppressWarnings("unused")
public class Fluids
{
	public static final FluidMolten moltenRoyalAlloy=$(nameRoyalAlloy, colorRoyalAlloy);
	@RegSmelteryFuel(amount=10,duration=400)
	public static final FluidMolten moltenStellarium=$(nameStellarium, colorStellarium);
	public static final FluidMolten moltenHothium=$(nameHothium, colorHothium);
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
	public static final FluidMolten moltenTonium=$(nameTonium, colorTonium);
	public static final FluidMolten moltenIrisia=$(nameIrisia, colorIrisia);

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
		fluid.setTemperature(temp); // 温度
		fluid.setLuminosity(lumen); // 亮度
		fluid.setViscosity(visc); // 黏度
		fluid.setDensity(dens); // 密度
		return fluid;
	}
}
