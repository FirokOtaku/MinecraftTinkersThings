package firok.tiths.util;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.TinkersThings;
import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;

/**
 * 内部操作
 */
public final class InnerActions
{
	private InnerActions(){}

	/* ---- 给物品增加特性 ---- */
	public static boolean addTrait(ITrait trait, NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		return addTrait(trait.getIdentifier(),rootCompound,modifierTag);
	}
	/**
	 * 给物品添加特性
	 * @see AbstractTrait#applyEffect(net.minecraft.nbt.NBTTagCompound, net.minecraft.nbt.NBTTagCompound)
	 * @param idTrait 要添加特性的id
	 * @return 之前是否已经添加过这个特性
	 */
	public static boolean addTrait(String idTrait, NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		NBTTagList traits = TagUtil.getTraitsTagList(rootCompound);
		for(int i = 0; i < traits.tagCount(); i++)
		{
			if(idTrait.equals(traits.getStringTagAt(i)))
			{
				return true;
			}
		}

		traits.appendTag(new NBTTagString(idTrait));
		TagUtil.setTraitsTagList(rootCompound, traits);

		return false;
	}

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

		String traitNameTarget=checkArmor?(traitName+"_armor"):traitName;
		ITrait trait= TinkerRegistry.getTrait(traitNameTarget);

		if(trait!=null)
		{
			if(TinkersThings.indev && checkArmor && !(trait instanceof IAbstractArmorTrait || trait instanceof AbstractArmorTrait))
			{
				throw new RuntimeException("CHECKING_ARMOR_TRAIT : not an armor trait! "+traitNameTarget);
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
