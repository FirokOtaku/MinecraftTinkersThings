package firok.tiths.util;

import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

/**
 * 内部操作
 */
public class InnerActions
{
	/* ---- 给材料增加属性 ---- */
	public static int addMaterialTraits(Material material, String[] traitNames)
	{
		return addMaterialTraits(material,traitNames,null,false);
	}
	public static int addMaterialTraits(Material material,String[] traitNames,String compo)
	{
		return addMaterialTraits(material, traitNames, compo, false);
	}
	public static boolean addMaterialTrait(Material material,String traitName)
	{
		return addMaterialTrait(material, traitName, null, false);
	}
	public static boolean addMaterialTrait(Material material,String traitName,String compo)
	{
		return addMaterialTrait(material, traitName, compo, false);
	}

	public static int addMaterialTraits(Material material, String[] traitNames,boolean checkArmor)
	{
		return addMaterialTraits(material,traitNames,null,checkArmor);
	}
	public static int addMaterialTraits(Material material,String[] traitNames,String compo,boolean checkArmor)
	{
		if(traitNames==null || traitNames.length<=0)
		{
			return 0;
		}

		int ret=0;
		for(String traitName:traitNames)
		{
			ret+= addMaterialTrait(material,traitName,compo,checkArmor)? 1: 0;
		}
		return ret;
	}
	public static boolean addMaterialTrait(Material material,String traitName,boolean checkArmor)
	{
		return addMaterialTrait(material, traitName, null,checkArmor);
	}
	public static boolean addMaterialTrait(Material material,String traitName,String compo,boolean checkArmor)
	{
//		log(String.format("adding trait: %s -> %s [ %s ]",traitName,material.identifier,compo));
		if(material==null||traitName==null) return false;

		ITrait trait= TinkerRegistry.getTrait(traitName);
		if(trait!=null)
		{
			if(checkArmor && !(trait instanceof IAbstractArmorTrait))
			{
				throw new RuntimeException("CHECKING_ARMOR_TRAIT : not an armor trait! "+traitName);
			}
			material.addTrait(trait,compo);
			return true;
		}
//		else
//		{
//			log("cannot find trait!");
//		}
		return false;
	}
}
