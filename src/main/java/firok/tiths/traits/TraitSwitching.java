package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitSwitching;
import static firok.tiths.common.Keys.nameTraitSwitching;
/**
 * 换位
 */
public class TraitSwitching extends AbstractTrait
{
	public TraitSwitching()
	{
		super(nameTraitSwitching,colorTraitSwitching);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && target.isEntityAlive())
		{
			double pPosX=player.posX,pPosY=player.posY,pPosZ=player.posZ;
			float pRotPitch=player.rotationPitch,pRotYaw=player.rotationYaw,pCamPitch=player.cameraPitch;

			player.rotationYaw=target.rotationYaw;
			player.rotationPitch=target.rotationPitch;
			player.setPositionAndUpdate(target.posX,target.posY,target.posZ);

			target.cameraPitch=pCamPitch;
			target.rotationYaw=pRotYaw;
			target.rotationPitch=pRotPitch;
			target.setPositionAndUpdate(pPosX,pPosY,pPosZ);
		}
	}
}
