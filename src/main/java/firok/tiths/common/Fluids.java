package firok.tiths.common;

import firok.tiths.block.fluid.BlockDirtyWater;
import firok.tiths.block.fluid.BlockMoltenEnderTurbulence;
import firok.tiths.block.fluid.BlockMoltenHothium;
import firok.tiths.block.fluid.BlockShiningGel;
import firok.tiths.util.reg.FluidBlock;
import firok.tiths.util.reg.RegSmelteryFuel;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.fluid.FluidColored;
import slimeknights.tconstruct.library.fluid.FluidMolten;

import static firok.tiths.common.Keys.*;

@SuppressWarnings("unused")
public final class Fluids
{
	private Fluids() {}

	public static final FluidMolten moltenRoyalAlloy=$(nameRoyalAlloy, colorRoyalAlloy);
	@RegSmelteryFuel(amount=10,duration=400)
	public static final FluidMolten moltenStellarium=$(nameStellarium, colorStellarium);
	@FluidBlock(BlockMoltenHothium.class)
	public static final FluidMolten moltenHothium=$(nameHothium, colorHothium);
	public static final FluidMolten moltenCinnabar=$(nameCinnabar, colorCinnabar);
	public static final FluidMolten moltenImmersedSilver=$(nameImmersedSilver, colorImmersedSilver);
	public static final FluidMolten moltenSolita=$(nameSolita, colorSolita);
	public static final FluidMolten moltenMagiga=$(nameMagiga, colorMagiga);
	public static final FluidMolten moltenInertWitherium=$(nameInertWitherium, colorInertWitherium);
	public static final FluidMolten moltenWitherium=$(nameWitherium, colorWitherium);
	public static final FluidMolten moltenPolarium=$(namePolarium, colorPolarium);
	public static final FluidMolten moltenHalleium=$(nameHalleium, colorHalleium);
	public static final FluidMolten moltenAltairium=$(nameAltairium, colorAltairium);
	public static final FluidMolten moltenCocoa=$(nameCocoa, colorCocoa);
	public static final FluidMolten moltenTitanium=$(nameTitanium, colorTitanium);
	public static final FluidMolten moltenTonium=$(nameTonium, colorTonium);
	public static final FluidMolten moltenIrisia=$(nameIrisia, colorIrisia);
	public static final FluidMolten moltenOraclium=$(nameOraclium, colorOraclium);
	public static final FluidMolten moltenSteamium=$(nameSteamium, colorSteamium);
	public static final FluidMolten moltenChloroplast=$(nameChloroplast, colorChloroplast);
	public static final FluidMolten moltenTanatonium=$(nameTanatonium, colorTanatonium);
	public static final FluidMolten moltenDecurrium=$(nameDecurrium, colorDecurrium);
	@FluidBlock(BlockMoltenEnderTurbulence.class)
	public static final FluidMolten moltenEnderTurbulence=$(nameEnderTurbulence, colorEnderTurbulence);
	@FluidBlock(BlockDirtyWater.class)
	public static final FluidColored dirtyWater=$$(nameDirtyWater, colorDirtyWater);
	@FluidBlock(BlockShiningGel.class)
	public static final FluidColored shiningGel=$$(nameShiningGel, colorShiningGel);

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

	static FluidColored $$(String name,int color)
	{
		return $$(name,color,1000,10,10000,2000);
	}
	static FluidColored $$(String name,int color,int temp,int lumen,int visc,int dens)
	{
		FluidColored fluid=new FluidColored(
				name,color,
				new ResourceLocation("tconstruct:blocks/fluids/liquid"),
				new ResourceLocation("tconstruct:blocks/fluids/liquid_flow")
		);
		fluid.setTemperature(temp); // 温度
		fluid.setLuminosity(lumen); // 亮度
		fluid.setViscosity(visc); // 黏度
		fluid.setDensity(dens); // 密度
		return fluid;
	}
}
