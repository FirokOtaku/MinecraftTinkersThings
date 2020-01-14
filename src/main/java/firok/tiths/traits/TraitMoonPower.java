package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.*;
import static firok.tiths.util.Predicates.canTick;

// 月之力量
public class TraitMoonPower extends AbstractTrait
{
	public TraitMoonPower()
	{
		super(nameTraitMoonPower, colorTraitMoonPower);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && canTick(world,100,2) && entity instanceof EntityLivingBase && !world.isDaytime())
		{
			ToolHelper.healTool(tool, 1, (EntityLivingBase) entity);
		}
	}
}