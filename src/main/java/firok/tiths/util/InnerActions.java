package firok.tiths.util;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableSet;
import firok.tiths.TinkersThings;
import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 内部操作
 */
public final class InnerActions
{
	private InnerActions(){}

	/**
	 * 应用强化
	 */
	public static void apply(NBTTagCompound rootCompound, NBTTagCompound modifierTag, Applier fun)
	{
		ModifierNBT.IntegerNBT modData = ModifierNBT.readInteger(modifierTag);

		Set<Category> categories = ImmutableSet.copyOf(TagUtil.getCategories(rootCompound));
		boolean harvest = categories.contains(Category.HARVEST);
		boolean weapon = categories.contains(Category.WEAPON);
		boolean launcher = categories.contains(Category.LAUNCHER);

		ToolNBT data = TagUtil.getToolStats(rootCompound);
		int level = modData.current;

		fun.apply(rootCompound, data,level,harvest,weapon,launcher);
	}



	/* ---- 添加物品描述 ----*/
	@SideOnly(Side.CLIENT)
	public static void addInformation(Item item, List<String> list, ITooltipFlag flag, Object... params)
	{
		addInformation(item.getUnlocalizedName(), list, flag, params);
	}
	@SideOnly(Side.CLIENT)
	public static void addInformation(Block block, List<String> list, ITooltipFlag flag, Object... params)
	{
		addInformation(block.getUnlocalizedName(), list, flag, params);
	}
	@SideOnly(Side.CLIENT)
	public static void addInformation(String key, List<String> list, ITooltipFlag flag, Object... params)
	{
		if(key==null || list==null) return;

		String keyDesc=key+".desc";
		String keyMore=key+".more";

		String value;
		TRANSLATE:if(Util.isShiftKeyDown())
		{
			value=I18n.format(keyMore,params);
			if(!value.equals(keyMore)) break TRANSLATE;

			value=I18n.format(keyDesc,params);
			if(value.equals(keyDesc)) value=null;
		}
		else
		{
			value=I18n.format(keyDesc,params);
			if(value.equals(keyDesc)) value=null;
		}

		if(value!=null)
		{
			Collections.addAll(list, value.split("<br>"));
		}
	}

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
