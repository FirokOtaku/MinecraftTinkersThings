package firok.tiths.traits;

import firok.tiths.util.Actions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import static firok.tiths.common.Keys.*;

/**
 * 折跃
 */
public class TraitWrapping extends AbstractTrait
{
	public TraitWrapping()
	{
		super(nameTraitWrapping, colorTraitWrapping);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
//		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		if(!target.world.isRemote && target.isEntityAlive() && wasHit)
		{
			Actions.CauseGatewayTeleport(target,8);
		}
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		ToolNBT data = TagUtil.getToolStats(rootCompound);
		data.durability=Math.max(1,data.durability-75);
		TagUtil.setToolTag(rootCompound, data.get());
	}
}