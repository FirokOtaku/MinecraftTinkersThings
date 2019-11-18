package firok.mtim.common;

import firok.mtim.util.*;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TCMaterials
{
	private static List<MaterialIntegration> listIntegration=new ArrayList<>(20);

	@Compo(name="royal_alloy",fluid="molten_royal_alloy")
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	public static Material royalAlloy=new Material("royal_alloy", Colors.Gold);

	public static Material cloud=new Material("cloud", Colors.Silver);
	public static Material cloudStorm=new Material("cloudstorm", Colors.DarkBlue);
	public static Material skyCrystal=new Material("skycrystal", Colors.Aqua);
	public static Material stormCrystal=new Material("stormcrystal", Colors.DarkBlue);
	public static Material antiGraCrystal=new Material("antigracrystal", Colors.DarkGreen);
	public static Material phantomCrystal=new Material("phantomcrystal", Colors.Gray);
	public static Material cinnabar=new Material("cinnabar", Colors.DarkRed);
	public static Material brokenBedrock=new Material("brokenbedrock", Colors.DarkGray);
	public static Material paradiseMetal=new Material("paradisemetal", Colors.Yellow);

//	private void registerTinkerMaterials() {
//		BowMaterialStats shitty = new BowMaterialStats(0.2f, 0.4f, -1f);
////
//		integrateMaterial("Tiberium", tiberium, tiberiumFluid, 80, 3.3f, 4f, 0.7f, -25, 50, DIAMOND, shitty, true, false);
//		integrateMaterial("Aurorium", aurorium, auroriumFluid, 750, 3.6f, 3.78f, 0.77f, 25, 130, COBALT, 0.45f, 1f, 1);
//		integrateMaterial("Prometheum", prometheum, prometheumFluid, 844, 4.75f, 6.6f, 1.2f, 25, 50, DURANITE, 0.2f, 0.6f, 3);
//		integrateMaterial("Duranite", duranite, duraniteFluid, 1550, 3.2f, 3.2f, 1.16f, 100, 100, DURANITE, 0.3f, 1.4f, 2);
//		integrateMaterial("Valyrium", valyrium, valyriumFluid, 1111, 5.37f, 4.8f, 1.30f, 100, 100, VALYRIUM, 1.1f, 1.2f, 4);
//		integrateMaterial("Vibranium", vibranium, vibraniumFluid, 1235, 7.62f, 8.1f, 1.3f, 100, 100, VIBRANIUM, 1.1f, 1.8f, 4);
//		integrateMaterial("Terrax", terrax, terraxFluid, 444, 4.77f, 2.9f, 0.8f, 100, 50, COBALT, shitty, true, true);
//		integrateMaterial("Palladium", palladium, palladiumFluid, 797, 4.35f, 6.8f, 1.3f, 130, -50, DURANITE, .5f, .2f, 3);
//		integrateMaterial("Uru", uru, uruFluid, 877, 2f, 7.2f, 1.5f, -50, 175, VALYRIUM, 1.3f, 0.8f, 6);
//		integrateMaterial("Eezo", eezo, eezoFluid, 50, 23f, 3.5f, .1f, 10, 10, COBALT, shitty, true, false);
//		integrateMaterial("Basalt", basalt, basaltFluid, 200, 3, 2.5f, 0.5f, -25, 25, STONE, shitty, true, false);
//		integrateMaterial("Triberium", triberium, triberiumFluid, 223, 6.2f, 8.35f, 0.63f, 50, 50, DIAMOND, shitty, true, true);
//		integrateMaterial("Fractum", fractum, fractumFluid, 538, 5.71f, 6.93f, 0.88f, 58, 117, DIAMOND, shitty);
//		integrateMaterial("Violium", violium, violiumFluid, 925, 3.8f, 3.75f, .90f, 175, 50, COBALT, .45f, .95f, 1);
//		integrateMaterial("Proxii", proxii, proxiiFluid, 625, 6.8f, 4.21f, 1.25f, 80, 25, DURANITE, .35f, .5f, 3);
//		integrateMaterial("Tritonite", tritonite, tritoniteFluid, 780, 8f, 3.3f, 1.45f, -25, 150, COBALT, shitty);
//		integrateMaterial("Ignitz", ignitz, ignitzFluid, 350, 2f, 6.66f, .85f, 150, 250, COBALT, .8f, .8f, 3);
//		integrateMaterial("Imperomite", imperomite, imperomiteFluid, 1350, 4.65f, 5.9f, 1.15f, -100, 150, DURANITE, 1.2f, 1.8f, 2);
//		integrateMaterial("Solarium", solarium, solariumFluid, 1100, 24f, 7f, 1.25f, 150, 150, VIBRANIUM, .8f, 1.5f, 5);
//		integrateMaterial("Nihilite", nihilite, nihiliteFluid, 400, 2.8f, 4.50f, .77f, 350, 155, VALYRIUM, 1.5f, .8f, 3);
//		integrateMaterial("Adamant", adamant, adamantFluid, 1750, 6f, 6f, 2f, 0, 0, VIBRANIUM, .35f, 1.85f, 8);
//		integrateMaterial("Dyonite", dyonite, dyoniteFluid, 900, 6.45f, 5f, 0.66f, -50, 250, DURANITE, 2, .9f, -1);
//		integrateMaterial("Nucleum", nucleum, nucleumFluid, 505, 17.5f, 9.5f, 1.05f, 100, 125, VALYRIUM, shitty);
//		integrateMaterial("Lumix", lumix, lumixFluid, 666, 3.84f, 3.92f, 0.85f, 250, 200, COBALT, .8f, 1.3f, 1);
//		integrateMaterial("Seismum", seismum, seismumFluid, 780, 3.66f, 6.05f, .95f, 250, 50, COBALT, shitty);
//		integrateMaterial("Astrium", astrium, astriumFluid, 750, 8.35f, 5.4f, 0.95f, -100, 200, COBALT, .7f, .8f, 2);
//		integrateMaterial("Niob", niob, niobFluid, 700, 4.5f, 4.5f, 2f, 200, 50, COBALT, shitty);
//		integrateMaterial("Yrdeen", yrdeen, yrdeenFluid, 999, 9.1f, 3f, 1.35f, 150, 250, COBALT, shitty);
//		integrateMaterial("Meteorite", meteorite, meteoriteFluid, 1500, 1.5f, 1.5f, .5f, 0, 0, OBSIDIAN, shitty);
//		integrateMaterial("Obsidiorite", obsidiorite, obsidioriteFluid, 1500, .5f, .5f, 1, -100, 100, COBALT, shitty);
//
//		integrateOre("Osram", osramFluid);
//		integrateOre("Abyssum", abyssumFluid);
//		integrateOre("Iox", ioxFluid);
//		integrateOre("Karmesine", karmesineFluid);
//		integrateOre("Ovium", oviumFluid);
//		integrateOre("Jauxum", jauxumFluid);
//		// Community Wishlist
//		integrateOre("Dilithium", dilithiumFluid);
//	}


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
					if(compo!=null&&material!=null&&TinkerRegistry.getMaterial(material.identifier)!=Material.UNKNOWN) continue;

					boolean craftable=compo.craftable(),castable=compo.castatble();

					String name = compo.name();
					Fluid fluid = FluidRegistry.getFluid(compo.fluid());

					if(material!=null)
					{
						material.setFluid(fluid).setCastable(castable).setCraftable(craftable);

						// 代表物品
						{
							String nameItemRepresent=compo.item();
							if(nameItemRepresent!=null&&nameItemRepresent.length()>0)
							{
								Item itemRepresent= Item.getByNameOrId(nameItemRepresent);
								material.setRepresentativeItem(itemRepresent);
							}
						}
						// 注册物品
						{
							String[] nameItems=compo.items();
							if(nameItems!=null&&nameItems.length>0)
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

}
