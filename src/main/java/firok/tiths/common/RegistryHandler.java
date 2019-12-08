package firok.tiths.common;

import firok.tiths.TinkersThings;
import firok.tiths.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.smeltery.block.BlockMolten;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static firok.tiths.TinkersThings.log;

public class RegistryHandler
{
	public static void registerFluids()
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
					TinkersThings.log("注册流体:"+fluid.getName());

					FluidRegistry.registerFluid(fluid);
					FluidRegistry.addBucketForFluid(fluid);

					BlockMolten block=new BlockMolten(fluid);
					block.setUnlocalizedName(Keys.prefMolten+ fluid.getName());
					block.setRegistryName(TinkersThings.MOD_ID, Keys.prefMolten+ fluid.getName());

					ForgeRegistries.BLOCKS.register(block);
					ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
					// fixme low // TAIGA.proxy.registerFluidModels(fluid);
				}
				TinkersThings.log("注册流体成功");
			}
			catch (Exception e)
			{
				TinkersThings.log("注册流体失败");
				e.printStackTrace();
			}
		}
	}
	public static void registerTraits()
	{
		Field[] fields=Traits.class.getDeclaredFields();
		TinkersThings.log("register traits...");
		for(Field field:fields)
		{
			try
			{
				Object obj=field.get(null);

				if(obj instanceof AbstractTrait)
				{
					AbstractTrait trait=(AbstractTrait)obj;
//					String name=field.getName();

					TinkerRegistry.addTrait(trait);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void registerItems(IForgeRegistry<Item> registry)
	{
		int countItem=0,countItemBlock=0;
		Field[] fieldsItems=Items.class.getDeclaredFields();
		for(Field field:fieldsItems)
		{
			try
			{
				Reg reg=field.getAnnotation(Reg.class);
				Object obj=field.get(null);
				if(reg!=null && obj instanceof Item)
				{
					String regValue=reg.value();
					String regTn=reg.tn();
					String regUn=reg.un();

					String tn= regValue.length()>0? regValue: regTn;
					String un= regValue.length()>0? regValue: regUn;

					Item item=(Item)obj;
					item.setUnlocalizedName(TinkersThings.MOD_ID+'.'+un);
					item.setRegistryName(TinkersThings.MOD_ID,tn);

					registry.register(item);
				}
				countItem++;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		Field[] fieldsBlocks=Blocks.class.getDeclaredFields();
		for(Field field:fieldsBlocks)
		{
			try
			{
				Reg reg=field.getAnnotation(Reg.class);
				Object obj=field.get(null);
				if(reg!=null && obj instanceof Block)
				{
					String regValue=reg.value();
					String regTn=reg.tn();
					String regUn=reg.un();

					String tn= regValue.length()>0? regValue: regTn;
					String un= regValue.length()>0? regValue: regUn;

					Block block=(Block)obj;
					ItemBlock itemBlock=new ItemBlock(block);
					itemBlock.setUnlocalizedName(TinkersThings.MOD_ID+'.'+un);
					itemBlock.setRegistryName(TinkersThings.MOD_ID,tn);

					registry.register(itemBlock);
				}
				countItemBlock++;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		TinkersThings.log(String.format("register items: item[%d/%d] item_block[%d/%d]",countItem,fieldsItems.length,countItemBlock,fieldsBlocks.length) );
	}

	public static void registerBlocks(IForgeRegistry<Block> registry)
	{
		Field[] fields=Blocks.class.getDeclaredFields();
		int countBlock=0;
		for(Field field:fields)
		{
			try
			{
				Reg reg=field.getAnnotation(Reg.class);
				Object obj=field.get(null);
				if(reg!=null && obj instanceof Block)
				{
					String regValue=reg.value();
					String regTn=reg.tn();
					String regUn=reg.un();

					String tn= regValue.length()>0? regValue: regTn;
					String un= regValue.length()>0? regValue: regUn;

					Block block=(Block)obj;
					block.setUnlocalizedName(TinkersThings.MOD_ID+'.'+un);
					block.setRegistryName(TinkersThings.MOD_ID,tn);

					registry.register(block);
				}
				countBlock++;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		TinkersThings.log(String.format("register blocks: block[%d/%d]",countBlock,fields.length) );
	}

	private static List<MaterialIntegration> listIntegration=new ArrayList<>(20);
	public static void registerMaterials()
	{
		Field[] fields=TCMaterials.class.getDeclaredFields();
		for(Field field:fields)
		{
			try
			{
				if(field.getType().equals(Material.class))
				{
					Object obj=field.get(null);
					Material material=(Material)obj;
					Compo compo=field.getAnnotation(Compo.class);

					// 检查是否已经注册
					if(compo==null||material==null|| TinkerRegistry.getMaterial(material.identifier)!=Material.UNKNOWN) continue;

					String name = compo.name();

					log("registering material:"+name);

					Fluid fluid = compo.fluid().length()>0? FluidRegistry.getFluid(compo.fluid()):null;

					if(material!=null)
					{
//						boolean craftable=compo.craftable(),castable=compo.castatble();
//						material.setFluid(fluid).setCastable(castable).setCraftable(craftable);

						// 代表物品
						{
							String nameItemRepresent=compo.item();
							if(nameItemRepresent.length()>0)
							{
								Item itemRepresent= Item.getByNameOrId(nameItemRepresent);
								log("found represent item:"+itemRepresent);
								if(itemRepresent!=null)
								{
									log("register represent item.");
									material.setRepresentativeItem(itemRepresent);
									material.addItem(itemRepresent);
								}
							}
							else
							{
								log("not given represent item.");
							}
						}
						// 注册物品
						{
							String[] nameItems=compo.items();
							if(nameItems.length>0)
							{
								for(String nameItem:nameItems)
								{
									Item item= Item.getByNameOrId(nameItem);
									log("found extra item:"+item);
									if(item!=null) material.addItem(item);
								}
							}
						}

						// 顶端
						{
							CompoHead compoHead=field.getAnnotation(CompoHead.class);
							if(compoHead!=null)
							{
								HeadMaterialStats statHead=new HeadMaterialStats(compoHead.durability(), compoHead.miningspeed(), compoHead.attack(), compoHead.harvestLevel());
								TinkerRegistry.addMaterialStats(material,statHead);
							}
						}
						// 连接
						{
							CompoHandle compoHandle=field.getAnnotation(CompoHandle.class);
							if(compoHandle!=null)
							{
								HandleMaterialStats statHandle=new HandleMaterialStats(compoHandle.modifier(), compoHandle.durability());
								TinkerRegistry.addMaterialStats(material,statHandle);
							}
						}
						// 其它
						{
							CompoExtra compoExtra=field.getAnnotation(CompoExtra.class);
							if(compoExtra!=null)
							{
								ExtraMaterialStats statExtra=new ExtraMaterialStats(compoExtra.extraDurability());
								TinkerRegistry.addMaterialStats(material,statExtra);
							}
						}
						// 弓
						{
							CompoBow compoBow=field.getAnnotation(CompoBow.class);
							if(compoBow!=null)
							{
								BowMaterialStats statBow=new BowMaterialStats(compoBow.drawspeed(), compoBow.range(), compoBow.bonusDamage());
								TinkerRegistry.addMaterialStats(material,statBow);
							}
						}
					}

					MaterialIntegration integration = new MaterialIntegration(material, fluid, name);
					integration.preInit();
					if(fluid!=null){
						TinkerRegistry.integrate(material,fluid);
					}
					else
					{
						TinkerRegistry.integrate(material);
					}
					listIntegration.add(integration);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void integrateMaterials()
	{
		for(MaterialIntegration inte:listIntegration)
		{
			inte.integrate();
		}
	}

	public static void registerEntities(IForgeRegistry<EntityEntry> registry)
	{
		for(Field field:Entities.class.getDeclaredFields())
		{
			Class classField=field.getType();
			RegEntity regEntity=field.getAnnotation(RegEntity.class);
			if(!Entity.class.isAssignableFrom(classField) || regEntity==null ) continue;

			String name=field.getName();

			registry.register(
					EntityEntryBuilder.create()
							.entity((Class<? extends Entity>) classField)
							.id(name,regEntity.network())
							.name(name)
							.tracker(regEntity.tracker(), regEntity.updateFrequency(),regEntity.sendUpdates())
							.build()
			);
		}
	}





	public static void registerToolParts(IForgeRegistry<Item> registry)
	{}
	public static void registerTools(IForgeRegistry<Item> registry)
	{
	}
	public static void registerToolCraftings()
	{
	}
}
