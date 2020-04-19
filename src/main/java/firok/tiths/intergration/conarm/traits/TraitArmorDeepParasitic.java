package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitDeepParasitic;
import static firok.tiths.common.Keys.nameTraitDeepParasitic;
import static firok.tiths.util.Predicates.canTick;

/**
 * 深触寄生 - 护甲
 */
public class TraitArmorDeepParasitic extends AbstractArmorTrait
{
	public TraitArmorDeepParasitic()
	{
		super(nameTraitDeepParasitic, colorTraitDeepParasitic);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && canTick(world,200,8) && entity instanceof EntityLivingBase)
		{
			EntityLivingBase enlb=(EntityLivingBase)entity;

			if(enlb.getHealth()/enlb.getMaxHealth()>0.2 && ToolHelper.getMaxDurability(tool) - ToolHelper.getCurrentDurability(tool)> 5)
			{
				enlb.attackEntityFrom(DamageSource.MAGIC,1);
				ToolHelper.healTool(tool,5,enlb);
			}
		}

		if(!world.isRemote && canTick(world,1200,0))
		{
			NBTTagCompound nbt=tool.getTagCompound();
			if(nbt==null)
			{
				nbt=new NBTTagCompound();
				tool.setTagCompound(nbt);
			}
			addTagBindingCurse(nbt);
		}
	}

	static NBTTagCompound createTagBindingCurse()
	{
		short idEnchBindingCurse=(short) Enchantment.getEnchantmentID(Enchantments.BINDING_CURSE);

		NBTTagCompound nbtEnchantmentBindingCurse = new NBTTagCompound();
		nbtEnchantmentBindingCurse.setShort("id", idEnchBindingCurse);
		nbtEnchantmentBindingCurse.setShort("lvl", (short)1);

		return nbtEnchantmentBindingCurse;
	}

	static boolean hasTagBindingCurse(NBTTagList listEnch)
	{
		short idEnchBindingCurse=(short) Enchantment.getEnchantmentID(Enchantments.BINDING_CURSE);

		for(NBTBase nbtBase:listEnch)
		{
			if(nbtBase instanceof NBTTagCompound)
			{
				NBTTagCompound nbt=(NBTTagCompound)nbtBase;
				if(nbt.hasKey("id") && nbt.getShort("id")==idEnchBindingCurse)
					return true;
			}
		}

		return false;
	}

	static void addTagBindingCurse(NBTTagCompound rootNBT)
	{
		NBTTagList listEnch= rootNBT.getTagList("ench",10);
		addTagBindingCurse(listEnch);
		rootNBT.setTag("ench",listEnch);
	}
	static void addTagBindingCurse(NBTTagList listEnch)
	{
		if(!hasTagBindingCurse(listEnch))
		{
			NBTTagCompound nbtEnchantmentBindingCurse = createTagBindingCurse();

			listEnch.appendTag(nbtEnchantmentBindingCurse);
		}
	}

	@Override
	public void applyEffect(NBTTagCompound rootNBT, NBTTagCompound modifierTag)
	{
		super.applyEffect(rootNBT, modifierTag);
		addTagBindingCurse(rootNBT);
	}
}
