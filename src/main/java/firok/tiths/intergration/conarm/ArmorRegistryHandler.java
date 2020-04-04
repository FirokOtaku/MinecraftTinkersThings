package firok.tiths.intergration.conarm;

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
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.lang.reflect.Field;

import static c4.conarm.lib.materials.ArmorMaterialType.*;
import static firok.tiths.TinkersThings.log;
import static firok.tiths.util.InnerActions.addMaterialTraits;
import static firok.tiths.util.conf.Values.__;

public class ArmorRegistryHandler
{
	public static void registerArmorTraits()
	{
		ArmorTraits.init();
		ArmorTraits.postinit();
		RegistryHandler.registerTraits(ArmorTraits.class, AbstractArmorTrait.class);

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
					boolean i=__(info);

					// 基底
					{
						CompoArmorCore compoArmorCore=field.getAnnotation(CompoArmorCore.class);
						if(compoArmorCore!=null)
						{
							CoreMaterialStats statCore=new CoreMaterialStats(
									i && __(info.core_durability)? info.core_durability:(float)compoArmorCore.durability(),
									i && __(info.core_dense)? info.core_dense:(float)compoArmorCore.defense()
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
									i && __(info.plate_modifier)? info.plate_modifier:(float)compoArmorPlate.modifier(),
									i && __(info.plate_durability)? info.plate_durability:(float)compoArmorPlate.durability(),
									i && __(info.plate_toughness)? info.plate_toughness:(float)compoArmorPlate.toughness()
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
									i && __(info.trim_durability)? info.trim_durability:(float)compoArmorTrim.extraDurability()
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
					boolean i=__(info);

					// 基底
					{
						CompoArmorCore compoArmorCore=field.getAnnotation(CompoArmorCore.class);
						if(compoArmorCore!=null)
						{
							addMaterialTraits(material,i && __(info.core_traits)?info.core_traits:compoArmorCore.traits(),CORE,true);
						}
					}
					// 护甲板
					{
						CompoArmorPlate compoArmorPlate=field.getAnnotation(CompoArmorPlate.class);
						if(compoArmorPlate!=null)
						{
							addMaterialTraits(material,i && __(info.plate_traits)?info.plate_traits:compoArmorPlate.traits(),PLATES,true);
						}
					}
					// 夹板
					{
						CompoArmorTrim compoArmorTrim=field.getAnnotation(CompoArmorTrim.class);
						if(compoArmorTrim!=null)
						{
							addMaterialTraits(material,i && __(info.trim_traits)?info.trim_traits:compoArmorTrim.traits(),TRIM,true);
						}
					}

					String[] traitsArmor=i && __(info.traits_armor)? info.traits_armor :compo.traitsArmor();

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
