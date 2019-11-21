package firok.mtim.common;

import firok.mtim.MoreTinkersMaterials;
import firok.mtim.util.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static firok.mtim.MoreTinkersMaterials.log;

public class RegistryHandler
{
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
					item.setUnlocalizedName(un);
					item.setRegistryName(MoreTinkersMaterials.MOD_ID,tn);

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
					itemBlock.setUnlocalizedName(un);
					itemBlock.setRegistryName(MoreTinkersMaterials.MOD_ID,tn);

					registry.register(itemBlock);
				}
				countItemBlock++;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		MoreTinkersMaterials.log(String.format("register items: item[%d/%d] item_block[%d/%d]",countItem,fieldsItems.length,countItemBlock,fieldsBlocks.length) );
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
					block.setUnlocalizedName(un);
					block.setRegistryName(MoreTinkersMaterials.MOD_ID,tn);

					registry.register(block);
				}
				countBlock++;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		MoreTinkersMaterials.log(String.format("register blocks: block[%d/%d]",countBlock,fields.length) );
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
}
