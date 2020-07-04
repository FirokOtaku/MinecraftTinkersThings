package firok.tiths.common;

import firok.tiths.TinkersThings;
import firok.tiths.potion.BasePotion;
import firok.tiths.util.$Material;
import firok.tiths.util.conf.MaterialInfo;
import firok.tiths.util.reg.*;
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
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.smeltery.block.BlockMolten;

import java.lang.reflect.Field;
import java.util.*;

import static firok.tiths.TinkersThings.log;

import static firok.tiths.util.InnerActions.*;
import static slimeknights.tconstruct.library.materials.MaterialTypes.*;

public final class RegistryHandler
{
	private RegistryHandler() {}

//	public static final Map<BlockMolten,ItemBlock> mapFluidBlock2Item=new HashMap<>();
	public static void registerFluids()
	{
		FieldStream.of(Fluids.class,null,Fluid.class)
				.whenFail(e->{
					log("error when registering fluid");
					log(e);
				})
				.forEach((field, anno, fluid) -> {
					FluidRegistry.registerFluid(fluid);
					FluidRegistry.addBucketForFluid(fluid);

					//BlockMolten block = new BlockMolten(fluid);
//                    // Sets names
//                    block.setUnlocalizedName("molten_" + fluid.getName());
//                    block.setRegistryName(TAIGA.MODID, "molten_" + fluid.getName());
//                    // Registers the fluid in its block form and its corresponding item (block/fluid as item in
//                    // inventory)
//                    ForgeRegistries.BLOCKS.register(block);
//                    ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));

					Block blockFluid;
					FluidBlock fluidBlock=field.getAnnotation(FluidBlock.class);
					if(fluidBlock!=null)
					{
						blockFluid=fluidBlock.value().getConstructor(Fluid.class).newInstance(fluid);
					}
					else
					{
						blockFluid=new BlockMolten(fluid);
					}

					blockFluid.setUnlocalizedName(Keys.prefMolten+ fluid.getName());
					blockFluid.setRegistryName(TinkersThings.MOD_ID, Keys.prefMolten+ fluid.getName());

					ItemBlock itemBlockMolten=new ItemBlock(blockFluid);
					itemBlockMolten.setRegistryName(blockFluid.getRegistryName());

//					mapFluidBlock2Item.put(blockFluid,itemBlockMolten);

					ForgeRegistries.BLOCKS.register(blockFluid);
					ForgeRegistries.ITEMS.register(itemBlockMolten);

					// 注册匠魂燃料
					{
						RegSmelteryFuel regSmelteryFuel=field.getAnnotation(RegSmelteryFuel.class);
						if(regSmelteryFuel!=null)
						{
							TinkerRegistry.registerSmelteryFuel(new FluidStack(fluid,regSmelteryFuel.amount()),regSmelteryFuel.duration());
						}
					}
				});
	}

	public static final Set<ITrait> traitsTiths=new HashSet<>();
	/**
	 * 注册属性本身
	 */
	public static void registerTraits(Class<?> clasz,Class<? extends ITrait> claszTrait)
	{
		FieldStream.of(clasz,null,claszTrait)
				.whenFail(e->{
					log("error when registering traits");
					log(e);
				})
				.forEach((field, anno, trait) -> {
					traitsTiths.add(trait);
					TinkerRegistry.addTrait(trait);
				});
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

	public static void registerItems(Class<?> classItems,Class<?> classBlocks,String modid)
	{
		IForgeRegistry<Item> registry=ForgeRegistries.ITEMS;
//		int countItem=0,countItemBlock=0;
		if(classItems!=null)
		FieldStream.of(classItems,null,Item.class,Reg.class)
				.whenFail(e->{
					log("error when registering item");
					log(e);
				})
				.forEach(((field, reg, item) -> {
					String regValue=reg.value();
					String regTn=reg.tn();
					String regUn=reg.un();

					String tn= regValue.length()>0? regValue: regTn;
					String un= regValue.length()>0? regValue: regUn;

					item.setUnlocalizedName(modid+'.'+un);
					item.setRegistryName(modid,tn);

					registry.register(item);

					item.setCreativeTab(TinkerRegistry.tabGeneral);

					registerOreDict(item,reg.od());// 矿物词典注册
				}));
		if(classBlocks!=null)
		FieldStream.of(classBlocks,null,Block.class,Reg.class)
				.whenFail(e->{
					log("error when registering item block");
					log(e);
				})
				.forEach((field, reg, block) -> {
					String regValue=reg.value();
					String regTn=reg.tn();
					String regUn=reg.un();

					String tn= regValue.length()>0? regValue: regTn;
					String un= regValue.length()>0? regValue: regUn;

					ItemBlock itemBlock=new ItemBlock(block);
					itemBlock.setUnlocalizedName(modid+'.'+un);
					itemBlock.setRegistryName(modid,tn);

					registry.register(itemBlock);

					block.setCreativeTab(TinkerRegistry.tabWorld);
				});

//		TinkersThings.log(String.format("register items: item[%d/%d] item_block[%d/%d]",countItem,fieldsItems.length,countItemBlock,fieldsBlocks.length) );
	}

	public static void registerBlocks(Class<?> classBlocks,String modid)
	{
		IForgeRegistry<Block> registry=ForgeRegistries.BLOCKS;
		FieldStream.of(classBlocks,null,Block.class,Reg.class)
				.whenFail(e->{
					log("error when registering block");
					log(e);
				})
				.forEach((field, reg, block) -> {
					String regValue=reg.value();
					String regTn=reg.tn();
					String regUn=reg.un();

					String tn= regValue.length()>0? regValue: regTn;
					String un= regValue.length()>0? regValue: regUn;

					block.setUnlocalizedName(modid+'.'+un);
					block.setRegistryName(modid,tn);

					registry.register(block);

					registerOreDict(block,reg.od()); // 矿物词典注册
				});
	}
	public static void registerTileEntities()
	{
		FieldStream.of(TileEntities.class,null,TileEntity.class)
				.forEach((field, anno, tileEntity) -> {
					Class classField=field.getType();
					if(!TileEntity.class.isAssignableFrom(classField)) return;
					String name=field.getName();
					GameRegistry.registerTileEntity(
							classField,
							new ResourceLocation(TinkersThings.MOD_ID, name)
					);
				});
	}

	private static List<MaterialIntegration> listIntegration=new ArrayList<>(20);

	public static final List<Material> materialsTiths=new ArrayList<>();
	/**
	 * 注册匠魂材料
	 */
	public static void registerMaterials()
	{
		FieldStream.of(TiCMaterials.class,null,Material.class,Compo.class)
				.whenFail(e->{
					log("error when registering material");
					log(e);
				})
				.forEach((field, compo, material) -> {
					// 检查是否已经注册
					if(compo==null||TinkerRegistry.getMaterial(material.identifier)!=Material.UNKNOWN) return;

					MaterialInfo info=ConfigJson.getMat(material.identifier);
					boolean i=Objects.nonNull(info);
					if(i&&isTrue(info.disable)) return;


					// 顶端
					if(!i||isFalse(info.disableHead))
					{
						CompoHead compoHead=field.getAnnotation(CompoHead.class);
						if(compoHead!=null)
						{
							HeadMaterialStats statHead=new HeadMaterialStats(
									i && Objects.nonNull(info.head_durability)? info.head_durability:compoHead.durability(),
									i && Objects.nonNull(info.head_mining_speed)? info.head_mining_speed:(float)compoHead.miningspeed(),
									i && Objects.nonNull(info.head_attack)? info.head_attack:(float)compoHead.attack(),
									i && Objects.nonNull(info.head_harvest_level)? info.head_harvest_level:compoHead.harvestLevel()
							);
							TinkerRegistry.addMaterialStats(material,statHead);
						}
					}
					// 连接
					if(!i||isFalse(info.disableHandle))
					{
						CompoHandle compoHandle=field.getAnnotation(CompoHandle.class);
						if(compoHandle!=null)
						{
							HandleMaterialStats statHandle=new HandleMaterialStats(
									i && Objects.nonNull(info.handle_modifier)? info.handle_modifier:(float)compoHandle.modifier(),
									i && Objects.nonNull(info.handle_durability)? info.handle_durability: compoHandle.durability()
							);
							TinkerRegistry.addMaterialStats(material,statHandle);
						}
					}
					// 其它
					if(!i||isFalse(info.disableExtra))
					{
						CompoExtra compoExtra=field.getAnnotation(CompoExtra.class);
						if(compoExtra!=null)
						{
							ExtraMaterialStats statExtra=new ExtraMaterialStats(
									i && Objects.nonNull(info.extra_durability)? info.extra_durability:compoExtra.extraDurability()
							);
							TinkerRegistry.addMaterialStats(material,statExtra);
						}
					}
					// 弓臂
					if(!i||isFalse(info.disableBow))
					{
						CompoBow compoBow=field.getAnnotation(CompoBow.class);
						if(compoBow!=null)
						{
							BowMaterialStats statBow=new BowMaterialStats(
									i && Objects.nonNull(info.bow_draw_speed)? info.bow_draw_speed:(float)compoBow.drawSpeed(),
									i && Objects.nonNull(info.bow_range)? info.bow_range:(float)compoBow.range(),
									i && Objects.nonNull(info.bow_bonus_damage)? info.bow_bonus_damage:(float)compoBow.bonusDamage()
							);
							TinkerRegistry.addMaterialStats(material,statBow);
						}
					}
					// 弓弦
					if(!i||isFalse(info.disableString))
					{
						CompoBowString compoBowString=field.getAnnotation(CompoBowString.class);
						if(compoBowString!=null)
						{
							BowStringMaterialStats statBowString=new BowStringMaterialStats(
									i && Objects.nonNull(info.string_modifier)? info.string_modifier:compoBowString.modifier()
							);
							TinkerRegistry.addMaterialStats(material,statBowString);
						}
					}
					// 箭杆
					if(!i||isFalse(info.disableShaft))
					{
						CompoArrowShaft compoArrowShaft=field.getAnnotation(CompoArrowShaft.class);
						if(compoArrowShaft!=null)
						{
							ArrowShaftMaterialStats statArrowShaft=new ArrowShaftMaterialStats(
									i && Objects.nonNull(info.shaft_modifier)? info.shaft_modifier:(float)compoArrowShaft.modifier(),
									i && Objects.nonNull(info.shaft_bonus_ammo)? info.shaft_bonus_ammo:compoArrowShaft.bonusAmmo()
							);
							TinkerRegistry.addMaterialStats(material,statArrowShaft);
						}
					}
					// 箭羽
					if(!i||isFalse(info.disableFletching))
					{
						CompoFletching compoFletching=field.getAnnotation(CompoFletching.class);
						if(compoFletching!=null)
						{
							FletchingMaterialStats statFletching=new FletchingMaterialStats(
									i && Objects.nonNull(info.fletching_accuracy)? info.fletching_accuracy:(float)compoFletching.accuracy(),
									i && Objects.nonNull(info.fletching_modifier)? info.fletching_modifier:(float)compoFletching.modifier()
							);
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

					materialsTiths.add(material);
				});
	}

	/**
	 * 给材料注册属性
	 */
	public static void registerMaterialTraits()
	{
		FieldStream.of(TiCMaterials.class,null,Material.class,Compo.class)
				.whenFail(e->{
					log("error when registering material trait");
					log(e);
				})
				.forEach((field, compo, material) -> {
					// 检查是否已经注册
					if(compo==null||TinkerRegistry.getMaterial(material.identifier)==Material.UNKNOWN) return;

					MaterialInfo info=ConfigJson.getMat(material.identifier);
					boolean i=Objects.nonNull(info);

					// 顶端
					{
						CompoHead compoHead=field.getAnnotation(CompoHead.class);
						if(compoHead!=null)
						{
							addMaterialTraits(material,i && Objects.nonNull(info.head_traits)? info.head_traits :compoHead.traits(), HEAD);
						}
					}
					// 连接
					{
						CompoHandle compoHandle=field.getAnnotation(CompoHandle.class);
						if(compoHandle!=null)
						{
							addMaterialTraits(material,i && Objects.nonNull(info.handle_traits)? info.handle_traits:compoHandle.traits(), HANDLE);
						}
					}
					// 其它
					{
						CompoExtra compoExtra=field.getAnnotation(CompoExtra.class);
						if(compoExtra!=null)
						{
							addMaterialTraits(material,i && Objects.nonNull(info.extra_traits)? info.extra_traits:compoExtra.traits(), EXTRA);
						}
					}
					// 弓臂
					{
						CompoBow compoBow=field.getAnnotation(CompoBow.class);
						if(compoBow!=null)
						{
							addMaterialTraits(material,i && Objects.nonNull(info.bow_traits)? info.bow_traits:compoBow.traits(), BOW);
						}
					}
					// 弓弦
					{
						CompoBowString compoBowString=field.getAnnotation(CompoBowString.class);
						if(compoBowString!=null)
						{
							addMaterialTraits(material,i && Objects.nonNull(info.string_traits)? info.string_traits:compoBowString.traits(), BOWSTRING);
						}
					}
					// 箭杆
					{
						CompoArrowShaft compoArrowShaft=field.getAnnotation(CompoArrowShaft.class);
						if(compoArrowShaft!=null)
						{
							addMaterialTraits(material,i && Objects.nonNull(info.shaft_traits)? info.shaft_traits:compoArrowShaft.traits(), SHAFT);
						}
					}
					// 箭羽
					{
						CompoFletching compoFletching=field.getAnnotation(CompoFletching.class);
						if(compoFletching!=null)
						{
							addMaterialTraits(material,i && Objects.nonNull(info.fletching_traits)? info.fletching_traits:compoFletching.traits(), FLETCHING);
						}
					}

					// 通用工具特性
					String[] traitsTool=i && Objects.nonNull(info.traits_tool)?info.traits_tool :compo.traitsTool();
					addMaterialTraits(material,traitsTool,MaterialTypes.HEAD);
					addMaterialTraits(material,traitsTool,MaterialTypes.EXTRA);
					addMaterialTraits(material,traitsTool,MaterialTypes.HANDLE);

					// 通用弓箭特性
					String[] traitsBow=i && Objects.nonNull(info.traits_bow)?info.traits_bow :compo.traitsBow();
					addMaterialTraits(material,traitsBow,MaterialTypes.BOW);
					addMaterialTraits(material,traitsBow,MaterialTypes.BOWSTRING);
					addMaterialTraits(material,traitsBow,MaterialTypes.FLETCHING);
					addMaterialTraits(material,traitsBow,MaterialTypes.SHAFT);
				});
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
		FieldStream.of(Potions.class,null, BasePotion.class)
				.whenFail(e->{
					log("error when registering potion");
					log(e);
				})
				.forEach((field, annotation, potion) -> {
					String name=field.getName();

					potion.setPotionName("effect."+name);
					potion.setRegistryName(name);

					potion.postInit();

					registry.register(potion);
				});
	}

	public static void registerOreDict(Block block,String[] ods)
	{
		if(ods==null||ods.length<=0) return;
		for(String od:ods)
		{
			try
			{
				OreDictionary.registerOre(od,block);
			}
			catch (Exception e)
			{
				log(e);
			}
		}
	}
	public static void registerOreDict(Item item,String[] ods)
	{
		if(ods==null||ods.length<=0) return;
		for(String od:ods)
		{
			try
			{
				OreDictionary.registerOre(od,item);
			}
			catch (Exception e)
			{
				log(e);
			}
		}
	}
}
