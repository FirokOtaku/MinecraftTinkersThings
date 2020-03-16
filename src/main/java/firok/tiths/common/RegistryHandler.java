package firok.tiths.common;

import firok.tiths.TinkersThings;
import firok.tiths.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.smeltery.block.BlockMolten;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static firok.tiths.TinkersThings.log;
import static firok.tiths.util.InnerActions.*;
import static slimeknights.tconstruct.library.materials.MaterialTypes.*;

public class RegistryHandler
{
	public static final Map<BlockMolten,ItemBlock> mapFluidBlock2Item=new HashMap<>();
	public static void registerFluids()
	{
		Field[] fields=Fluids.class.getDeclaredFields();
		for(Field field:fields)
		{
			try
			{
				Object obj=field.get(null);
				if(obj instanceof Fluid)
				{
					Fluid fluid=(Fluid)obj;
//					TinkersThings.log("registering fluid:"+fluid.getName());

					FluidRegistry.registerFluid(fluid);
					FluidRegistry.addBucketForFluid(fluid);

					BlockMolten blockMolten=new BlockMolten(fluid);
					blockMolten.setUnlocalizedName(Keys.prefMolten+ fluid.getName());
					blockMolten.setRegistryName(TinkersThings.MOD_ID, Keys.prefMolten+ fluid.getName());
					fluid.setBlock(blockMolten);

					ItemBlock itemBlockMolten=new ItemBlock(blockMolten);
					itemBlockMolten.setRegistryName(blockMolten.getRegistryName());

					mapFluidBlock2Item.put(blockMolten,itemBlockMolten);

					ForgeRegistries.BLOCKS.register(blockMolten);
					ForgeRegistries.ITEMS.register(itemBlockMolten);
					// fixme low // TAIGA.proxy.registerFluidModels(fluid);

					// 注册匠魂燃料
					{
						RegSmelteryFuel regSmelteryFuel=field.getAnnotation(RegSmelteryFuel.class);
						if(regSmelteryFuel!=null)
						{
							TinkerRegistry.registerSmelteryFuel(new FluidStack(fluid,regSmelteryFuel.amount()),regSmelteryFuel.duration());
						}
					}
				}
//				TinkersThings.log("注册流体成功");
			}
			catch (Exception e)
			{
				log("error when registering fluid");
				log(e);
			}
		}
	}

	/**
	 * 注册属性本身
	 */
	public static void registerTraits()
	{
		Field[] fields=Traits.class.getDeclaredFields();
//		TinkersThings.log("register traits...");
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
				log("error when registering traits");
				log(e);
			}
		}
	}
//	public static void registerModifiers()
//	{
//		Field[] fields=Modifiers.class.getDeclaredFields();
//		if(true) return;
//
//
//		TinkersThings.log("register modifiers...");
//		for(Field field:fields)
//		{
//			try
//			{
//				Object obj=field.get(null);
//
//				if(obj instanceof ToolModifier)
//				{
//					ToolModifier modifier=(ToolModifier)obj;
//
//					TinkerRegistry.registerModifier(modifier);
//				}
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}

	public static void registerItems()
	{
		IForgeRegistry<Item> registry=ForgeRegistries.ITEMS;
//		int countItem=0,countItemBlock=0;
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

					item.setCreativeTab(TinkerRegistry.tabGeneral);

					// 矿物词典注册
					String[] ods=reg.od();
					if(ods.length>0)
					{
						for(String od:ods) OreDictionary.registerOre(od,item);
					}
				}
//				countItem++;
			}
			catch (Exception e)
			{
				log("error when registering item");
				log(e);
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

					block.setCreativeTab(TinkerRegistry.tabWorld);
				}
//				countItemBlock++;
			}
			catch (Exception e)
			{
				log("error when registering item block");
				log(e);
			}
		}

//		TinkersThings.log(String.format("register items: item[%d/%d] item_block[%d/%d]",countItem,fieldsItems.length,countItemBlock,fieldsBlocks.length) );
	}

	public static void registerBlocks()
	{
		IForgeRegistry<Block> registry=ForgeRegistries.BLOCKS;
		Field[] fields=Blocks.class.getDeclaredFields();
//		int countBlock=0;
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

					// 矿物词典注册
					String[] ods=reg.od();
					if(ods.length>0)
					{
						for(String od:ods) OreDictionary.registerOre(od,block);
					}
				}
//				countBlock++;
			}
			catch (Exception e)
			{
				log("error when registering block");
				log(e);
			}
		}
//		TinkersThings.log(String.format("register blocks: block[%d/%d]",countBlock,fields.length) );
	}
	public static void registerTileEntities()
	{
		for(Field field:TileEntities.class.getDeclaredFields())
		{
			Class classField=field.getType();
			if(!TileEntity.class.isAssignableFrom(classField)) continue;
			String name=field.getName();
			GameRegistry.registerTileEntity(
					classField,
					new ResourceLocation(TinkersThings.MOD_ID, name)
			);
		}
	}

	private static List<MaterialIntegration> listIntegration=new ArrayList<>(20);

	/**
	 * 注册匠魂材料
	 */
	public static void registerMaterials()
	{
		Field[] fields= TiCMaterials.class.getDeclaredFields();
		for(Field field:fields)
		{
			try
			{
				Object obj=field.get(null);
				if(obj instanceof Material)
				{
					Material material=(Material)obj;
					Compo compo=field.getAnnotation(Compo.class);

					// 检查是否已经注册
					if(compo==null||TinkerRegistry.getMaterial(material.identifier)!=Material.UNKNOWN) continue;

					// 顶端
					{
						CompoHead compoHead=field.getAnnotation(CompoHead.class);
						if(compoHead!=null)
						{
							HeadMaterialStats statHead=new HeadMaterialStats(compoHead.durability(), (float)compoHead.miningspeed(), (float)compoHead.attack(), compoHead.harvestLevel());
							TinkerRegistry.addMaterialStats(material,statHead);
						}
					}
					// 连接
					{
						CompoHandle compoHandle=field.getAnnotation(CompoHandle.class);
						if(compoHandle!=null)
						{
							HandleMaterialStats statHandle=new HandleMaterialStats((float)compoHandle.modifier(), compoHandle.durability());
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
					// 弓臂
					{
						CompoBow compoBow=field.getAnnotation(CompoBow.class);
						if(compoBow!=null)
						{
							BowMaterialStats statBow=new BowMaterialStats((float)compoBow.drawSpeed(), (float)compoBow.range(), (float)compoBow.bonusDamage());
							TinkerRegistry.addMaterialStats(material,statBow);
						}
					}
					// 弓弦
					{
						CompoBowString compoBowString=field.getAnnotation(CompoBowString.class);
						if(compoBowString!=null)
						{
							BowStringMaterialStats statBowString=new BowStringMaterialStats(compoBowString.modifier());
							TinkerRegistry.addMaterialStats(material,statBowString);
						}
					}
					// 箭杆
					{
						CompoArrowShaft compoArrowShaft=field.getAnnotation(CompoArrowShaft.class);
						if(compoArrowShaft!=null)
						{
							ArrowShaftMaterialStats statArrowShaft=new ArrowShaftMaterialStats((float)compoArrowShaft.modifier(),compoArrowShaft.bonusAmmo());
							TinkerRegistry.addMaterialStats(material,statArrowShaft);
						}
					}
					// 箭羽
					{
						CompoFletching compoFletching=field.getAnnotation(CompoFletching.class);
						if(compoFletching!=null)
						{
							FletchingMaterialStats statFletching=new FletchingMaterialStats((float)compoFletching.accuracy(),(float)compoFletching.modifier());
							TinkerRegistry.addMaterialStats(material,statFletching);
						}
					}

					MaterialIntegration integration = new MaterialIntegration(material,material.getFluid());
					integration.preInit();
//					TinkerRegistry.integrate(material);

//					MaterialIntegration integration = new MaterialIntegration(material, fluid, name);
//					integration.preInit();
//					if(fluid!=null){
//						TinkerRegistry.integrate(material,fluid);
//					}
//					else
//					{
//						TinkerRegistry.integrate(material);
//					}
					listIntegration.add(integration);
//					TinkerRegistry.addMaterial(material);
				}
			}
			catch (Exception e)
			{
				log("error when registering material");
				log(e);
			}
		}
	}

	/**
	 * 给材料注册属性
	 */
	public static void registerMaterialTraits()
	{
		Field[] fields= TiCMaterials.class.getDeclaredFields();
		for(Field field:fields)
		{
			try
			{
				Object obj=field.get(null);
				if(obj instanceof Material)
				{
					Material material=(Material)obj;
					Compo compo=field.getAnnotation(Compo.class);

					// 检查是否已经注册
					if(compo==null||TinkerRegistry.getMaterial(material.identifier)==Material.UNKNOWN)
					{
						log(String.format("compo[ %s ],material[ %s ]",compo,material));
						continue;
					};

					// 顶端
					{
						CompoHead compoHead=field.getAnnotation(CompoHead.class);
						if(compoHead!=null)
						{
							addMaterialTraits(material,compoHead.traits(), HEAD);
						}
					}
					// 连接
					{
						CompoHandle compoHandle=field.getAnnotation(CompoHandle.class);
						if(compoHandle!=null)
						{
							addMaterialTraits(material,compoHandle.traits(), HANDLE);
						}
					}
					// 其它
					{
						CompoExtra compoExtra=field.getAnnotation(CompoExtra.class);
						if(compoExtra!=null)
						{
							addMaterialTraits(material,compoExtra.traits(), EXTRA);
						}
					}
					// 弓臂
					{
						CompoBow compoBow=field.getAnnotation(CompoBow.class);
						if(compoBow!=null)
						{
							addMaterialTraits(material,compoBow.traits(), BOW);
						}
					}
					// 弓弦
					{
						CompoBowString compoBowString=field.getAnnotation(CompoBowString.class);
						if(compoBowString!=null)
						{
							addMaterialTraits(material,compoBowString.traits(), BOWSTRING);
						}
					}
					// 箭杆
					{
						CompoArrowShaft compoArrowShaft=field.getAnnotation(CompoArrowShaft.class);
						if(compoArrowShaft!=null)
						{
							addMaterialTraits(material,compoArrowShaft.traits(), SHAFT);
						}
					}
					// 箭羽
					{
						CompoFletching compoFletching=field.getAnnotation(CompoFletching.class);
						if(compoFletching!=null)
						{
							addMaterialTraits(material,compoFletching.traits(), FLETCHING);
						}
					}

					addMaterialTraits(material,compo.traitsTool(),MaterialTypes.HEAD);
					addMaterialTraits(material,compo.traitsTool(),MaterialTypes.EXTRA);
					addMaterialTraits(material,compo.traitsTool(),MaterialTypes.HANDLE);
					addMaterialTraits(material,compo.traitsTool(),MaterialTypes.BOW);
					addMaterialTraits(material,compo.traitsTool(),MaterialTypes.BOWSTRING);
					addMaterialTraits(material,compo.traitsTool(),MaterialTypes.FLETCHING);
					addMaterialTraits(material,compo.traitsTool(),MaterialTypes.SHAFT);
				}
			}
			catch (Exception e)
			{
				log("error when registering material trait");
				log(e);
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

	public static void registerEntities()
	{
		IForgeRegistry<EntityEntry> registry=ForgeRegistries.ENTITIES;
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

//	public static void registerVillagers()
//	{
//		IForgeRegistry<VillagerProfession> registry=ForgeRegistries.VILLAGER_PROFESSIONS;
//		for(Field field:Villagers.class.getDeclaredFields())
//		{
//			try
//			{
//				Object obj=field.get(null);
//				if(obj instanceof VillagerProfession)
//				{
//					VillagerProfession profession=(VillagerProfession)obj;
//					registry.register(profession);
//				}
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}

	public static void registerPotions()
	{
		IForgeRegistry<Potion> registry=ForgeRegistries.POTIONS;
		for(Field field:Potions.class.getDeclaredFields())
		{
			try
			{
				Object obj=field.get(null);
				if(obj instanceof Potion)
				{
					Potion potion=(Potion)obj;
					String name=field.getName();

					potion.setPotionName("effect."+name);
					potion.setRegistryName(name);

					registry.register(potion);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
