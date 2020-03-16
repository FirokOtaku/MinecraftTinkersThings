package firok.tiths.util;

import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

import static firok.tiths.TinkersThings.log;

/**
 * 内部操作
 */
public class InnerActions
{
	public static int addMaterialTraits(Material material, String[] traitNames)
	{
		return addMaterialTraits(material,traitNames,null);
	}
	public static int addMaterialTraits(Material material,String[] traitNames,String compo)
	{
		if(traitNames==null || traitNames.length<=0)
		{
			log(String.format("material[%s] compo[c%s] no traits",material,compo));
			return 0;
		}

		int ret=0;
		for(String traitName:traitNames)
		{
			ret+= addMaterialTrait(material,traitName,compo)? 1: 0;
		}
		return ret;
	}
	public static boolean addMaterialTrait(Material material,String traitName)
	{
		return addMaterialTrait(material, traitName, null);
	}
	public static boolean addMaterialTrait(Material material,String traitName,String compo)
	{
		log(String.format("adding trait: %s -> %s [ %s ]",traitName,material.identifier,compo));
		if(material==null||traitName==null) return false;

		ITrait trait= TinkerRegistry.getTrait(traitName);
		if(trait!=null)
		{
			material.addTrait(trait,compo);
			return true;
		}
		else
		{
			log("cannot find trait!");
		}
		return false;
	}
}
