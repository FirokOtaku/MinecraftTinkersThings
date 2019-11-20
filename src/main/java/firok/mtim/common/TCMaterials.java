package firok.mtim.common;

import firok.mtim.util.*;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.tools.TinkerTraits;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static firok.mtim.common.Traits.*;
import static firok.mtim.util.Keys.*;
import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

public class TCMaterials
{
	private static List<MaterialIntegration> listIntegration=new ArrayList<>(20);

	@Compo(name= nameRoyalAlloy,fluid= nameRoyalAlloy)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material royalAlloy=new Material(nameRoyalAlloy, colorRoyalAlloy)
			.addTrait(luxurious)
			.addTrait(TinkerTraits.magnetic2);

	@Compo(name= nameStellar,fluid= nameStellar)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material stellar=new Material(nameStellar, colorStellar)
			.addTrait(radiant);

	@Compo(name= nameSpiderLeg)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoExtra(extraDurability = 100)
	public static Material spiderLeg=new Material(nameSpiderLeg, colorSpiderLeg)
			.addTrait(TinkerTraits.sharp)
			.addTrait(TinkerTraits.poisonous);

	@Compo(name= nameSpiderLeg)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoExtra(extraDurability = 100)
	public static Material hardSpiderLeg=new Material(nameHardSpiderLeg, colorHardSpiderLeg)
			.addTrait(TinkerTraits.sharp);

	@Compo(name= nameCinnabar)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material cinnabar=new Material(nameCinnabar, colorCinnabar)
			.addTrait(TinkerTraits.poisonous, HEAD);

//	@Compo(name= nameGlass, item="glass",fluid="glass")
//	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
//	@CompoHandle(modifier = 0.8f, durability = 100)
//	@CompoExtra(extraDurability = 100)
//	public static Material glass=new Material(nameGlass, colorGlass)
//			.addTrait(cheapskate)
//			.addTrait(lightweight);

	@Compo(name= nameGlass) // , item="consolidated_glass"
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material consolidatedGlass=new Material(nameConsolidatedGlass, colorConsolidatedGlass)
			.addTrait(cheapskate)
			.addTrait(cheap)
			.addTrait(lightweight);

	@Compo(name= nameBrokenIce, item="ice",castatble = false, craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material brokenIce=new Material(nameBrokenIce,colorBrokenIce)
			.addTrait(cheapskate)
			.addTrait(cheap)
			.addTrait(icy);

	public static Material cloud=new Material("cloud", Colors.Silver);
	public static Material cloudStorm=new Material("cloud_storm", Colors.DarkBlue);
	public static Material skyCrystal=new Material("sky_crystal", Colors.Aqua);
	public static Material stormCrystal=new Material("storm_crystal", Colors.DarkBlue);
	public static Material antiGraCrystal=new Material("anti_gra_crystal", Colors.DarkGreen);
	public static Material phantomCrystal=new Material("phantom_crystal", Colors.Gray);
	public static Material brokenBedrock=new Material("broken_bedrock", Colors.DarkGray);
	public static Material paradiseMetal=new Material("paradise_metal", Colors.Yellow);

	public static void packMaterials()
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
					if(compo==null||material==null||TinkerRegistry.getMaterial(material.identifier)!=Material.UNKNOWN) continue;

					String name = compo.name();

					Fluid fluid = compo.fluid().length()>0?FluidRegistry.getFluid(compo.fluid()):null;

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
								material.setRepresentativeItem(itemRepresent);
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
									material.addItem(item);
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
					listIntegration.add(integration);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void integrateAll()
	{
		for(MaterialIntegration inte:listIntegration)
		{
			inte.integrate();
		}
	}

}
