package firok.tiths.traits;

import firok.tiths.util.Actions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;
import static firok.tiths.util.Predicates.canTrigger;

// 君临
public class TraitSovereign extends AbstractTrait
{
	public TraitSovereign()
	{
		super(nameTraitSovereign, colorTraitSovereign);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		if(!target.world.isRemote)
		{
			if(canTrigger(target.world,0.2))
			{
				Actions.CauseTargetFear(target, EntityPlayer.class,100);
			}
		}
	}
}
