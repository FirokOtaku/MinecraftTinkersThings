package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
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
	}
}
