package firok.mtim.common;

import firok.mtim.MoreTinkersMaterials;
import firok.mtim.util.Colors;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.smeltery.block.BlockMolten;

import java.lang.reflect.Field;

public class Fluids
{
	public static FluidMolten moltenRoyalAlloy=$("royal_alloy", Colors.Gold);

	public static void register()
	{
		Field[] fields=Fluids.class.getDeclaredFields();
		for(Field field:fields)
		{
			try
			{
				Object obj=field.get(null);
				if(obj instanceof FluidMolten)
				{
					FluidMolten fluid=(FluidMolten)obj;
					MoreTinkersMaterials.log("注册流体:"+fluid.getName());

					FluidRegistry.registerFluid(fluid);
					FluidRegistry.addBucketForFluid(fluid);

					BlockMolten block=new BlockMolten(fluid);
					block.setUnlocalizedName("molten_"+fluid.getName());
					block.setRegistryName(MoreTinkersMaterials.MOD_ID,"molten_"+ fluid.getName());

					ForgeRegistries.BLOCKS.register(block);
					ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
					// fixme low // TAIGA.proxy.registerFluidModels(fluid);
				}
				MoreTinkersMaterials.log("注册流体成功");
			}
			catch (Exception e)
			{
				MoreTinkersMaterials.log("注册流体失败");
				e.printStackTrace();
			}
		}
	}

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
