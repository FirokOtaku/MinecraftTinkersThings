package firok.tiths.intergration.conarm;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import firok.tiths.common.TiCMaterials;
import firok.tiths.util.Compo;
import firok.tiths.intergration.conarm.util.CompoArmorCore;
import firok.tiths.intergration.conarm.util.CompoArmorPlate;
import firok.tiths.intergration.conarm.util.CompoArmorTrim;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.lang.reflect.Field;

import static firok.tiths.TinkersThings.log;
import static firok.tiths.util.InnerActions.*;
import static c4.conarm.lib.materials.ArmorMaterialType.*;

public class ArmorRegistryHandler
{
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

					// 基底
					{
						CompoArmorCore compoArmorCore=field.getAnnotation(CompoArmorCore.class);
						if(compoArmorCore!=null)
						{
							CoreMaterialStats statCore=new CoreMaterialStats((float)compoArmorCore.durability(),(float)compoArmorCore.defense());
							material.addStats(statCore);
						}
					}
					// 护甲板
					{
						CompoArmorPlate compoArmorPlate=field.getAnnotation(CompoArmorPlate.class);
						if(compoArmorPlate!=null)
						{
							PlatesMaterialStats statPlate=new PlatesMaterialStats((float)compoArmorPlate.modifier(),(float)compoArmorPlate.durability(),(float)compoArmorPlate.toughness());
							material.addStats(statPlate);
						}
					}
					// 夹板
					{
						CompoArmorTrim compoArmorTrim=field.getAnnotation(CompoArmorTrim.class);
						if(compoArmorTrim!=null)
						{
							TrimMaterialStats statTrim=new TrimMaterialStats((float)compoArmorTrim.extraDurability());
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

					// 基底
					{
						CompoArmorCore compoArmorCore=field.getAnnotation(CompoArmorCore.class);
						if(compoArmorCore!=null)
						{
							addMaterialTraits(material,compoArmorCore.traits(),CORE,true);
						}
					}
					// 护甲板
					{
						CompoArmorPlate compoArmorPlate=field.getAnnotation(CompoArmorPlate.class);
						if(compoArmorPlate!=null)
						{
							addMaterialTraits(material,compoArmorPlate.traits(),PLATES,true);
						}
					}
					// 夹板
					{
						CompoArmorTrim compoArmorTrim=field.getAnnotation(CompoArmorTrim.class);
						if(compoArmorTrim!=null)
						{
							addMaterialTraits(material,compoArmorTrim.traits(),TRIM,true);
						}
					}

					addMaterialTraits(material,compo.traitsArmor(), CORE,true);
					addMaterialTraits(material,compo.traitsArmor(), PLATES,true);
					addMaterialTraits(material,compo.traitsArmor(), TRIM,true);
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
