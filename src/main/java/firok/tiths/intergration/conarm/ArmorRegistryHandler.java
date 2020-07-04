package firok.tiths.intergration.conarm;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.ConfigJson;
import firok.tiths.common.RegistryHandler;
import firok.tiths.common.TiCMaterials;
import firok.tiths.intergration.conarm.util.CompoArmorCore;
import firok.tiths.intergration.conarm.util.CompoArmorPlate;
import firok.tiths.intergration.conarm.util.CompoArmorTrim;
import firok.tiths.util.conf.MaterialInfo;
import firok.tiths.util.reg.Compo;
import firok.tiths.util.reg.FieldStream;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.lang.reflect.Field;
import java.util.Objects;

import static c4.conarm.lib.materials.ArmorMaterialType.*;
import static firok.tiths.TinkersThings.log;
import static firok.tiths.util.InnerActions.*;

public class ArmorRegistryHandler
{
	public static void registerArmorTraits()
	{
		ArmorTraits.init();
		ArmorTraits.postinit();
		RegistryHandler.registerTraits(ArmorTraits.class, AbstractArmorTrait.class);
		FieldStream.of(ArmorTraits.class,null,AbstractArmorTrait.class)
		.forEach((field, annotation, abstractArmorTrait)->ArmoryRegistry.registerModifier(abstractArmorTrait));
	}
	/**
	 * 注册护甲材料
	 */
	public static void registerArmorMaterials()
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
					if(compo==null||TinkerRegistry.getMaterial(material.identifier)==Material.UNKNOWN) continue;

					MaterialInfo info= ConfigJson.getMat(material.identifier);
					boolean i=Objects.nonNull(info);

					// 基底
					{
						CompoArmorCore compoArmorCore=field.getAnnotation(CompoArmorCore.class);
						if(compoArmorCore!=null)
						{
							CoreMaterialStats statCore=new CoreMaterialStats(
									i && Objects.nonNull(info.core_durability)? info.core_durability:(float)compoArmorCore.durability(),
									i && Objects.nonNull(info.core_dense)? info.core_dense:(float)compoArmorCore.defense()
							);
							material.addStats(statCore);
						}
					}
					// 护甲板
					{
						CompoArmorPlate compoArmorPlate=field.getAnnotation(CompoArmorPlate.class);
						if(compoArmorPlate!=null)
						{
							PlatesMaterialStats statPlate=new PlatesMaterialStats(
									i && Objects.nonNull(info.plate_modifier)? info.plate_modifier:(float)compoArmorPlate.modifier(),
									i && Objects.nonNull(info.plate_durability)? info.plate_durability:(float)compoArmorPlate.durability(),
									i && Objects.nonNull(info.plate_toughness)? info.plate_toughness:(float)compoArmorPlate.toughness()
							);
							material.addStats(statPlate);
						}
					}
					// 夹板
					{
						CompoArmorTrim compoArmorTrim=field.getAnnotation(CompoArmorTrim.class);
						if(compoArmorTrim!=null)
						{
							TrimMaterialStats statTrim=new TrimMaterialStats(
									i && Objects.nonNull(info.trim_durability)? info.trim_durability:(float)compoArmorTrim.extraDurability()
							);
							material.addStats(statTrim);
						}
					}
				}
			}
			catch (Exception e)
			{
				log("error when registering armor material attribution");
				log(e);
			}
		}
	}

	/**
	 * 给护甲材料增加属性
	 */
	public static void registerArmorMaterialTraits()
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
					if(compo==null||TinkerRegistry.getMaterial(material.identifier)==Material.UNKNOWN) continue;

					MaterialInfo info=ConfigJson.getMat(material.identifier);
					boolean i=Objects.nonNull(info);
					if(i&&isTrue(info.disable)) return;

					// 基底
					if(!i||isFalse(info.disableCore))
					{
						CompoArmorCore compoArmorCore=field.getAnnotation(CompoArmorCore.class);
						if(compoArmorCore!=null)
						{
							addMaterialTraits(material,i && Objects.nonNull(info.core_traits)?info.core_traits:compoArmorCore.traits(),CORE,true);
						}
					}
					// 护甲板
					if(!i||isFalse(info.disablePlate))
					{
						CompoArmorPlate compoArmorPlate=field.getAnnotation(CompoArmorPlate.class);
						if(compoArmorPlate!=null)
						{
							addMaterialTraits(material,i && Objects.nonNull(info.plate_traits)?info.plate_traits:compoArmorPlate.traits(),PLATES,true);
						}
					}
					// 夹板
					if(!i||isFalse(info.disableTrim))
					{
						CompoArmorTrim compoArmorTrim=field.getAnnotation(CompoArmorTrim.class);
						if(compoArmorTrim!=null)
						{
							addMaterialTraits(material,i && Objects.nonNull(info.trim_traits)?info.trim_traits:compoArmorTrim.traits(),TRIM,true);
						}
					}

					String[] traitsArmor=i && Objects.nonNull(info.traits_armor)? info.traits_armor :compo.traitsArmor();

					addMaterialTraits(material,traitsArmor, CORE,true);
					addMaterialTraits(material,traitsArmor, PLATES,true);
					addMaterialTraits(material,traitsArmor, TRIM,true);
				}
			}
			catch (Exception e)
			{
				log("error when registering armor material trait");
				log(e);
			}
		}
	}
}
