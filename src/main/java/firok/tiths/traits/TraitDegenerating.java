package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitDegenerating;
import static firok.tiths.common.Keys.nameTraitDegenerating;
import static firok.tiths.util.Predicates.canTick;

/**
 * 简并
 */
public class TraitDegenerating extends AbstractTrait
{
	public TraitDegenerating()
	{
		super(nameTraitDegenerating,colorTraitDegenerating);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && entity instanceof EntityLivingBase && canTick(world,80,0) && !ToolHelper.isBroken(tool))
		{
			EntityLivingBase enlb=(EntityLivingBase)entity;
			int durMax= ToolHelper.getMaxDurability(tool);
			int durNow= ToolHelper.getCurrentDurability(tool);
			int durHalf=durMax/2;
			if(durNow > durHalf) ToolHelper.damageTool(tool,1,enlb);
			else if(durNow < durHalf) ToolHelper.healTool(tool,1,enlb);
		}
	}
}
