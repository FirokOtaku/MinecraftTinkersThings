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
			float pCamPitch=player.cameraPitch,pCamYaw=player.rotationYaw;

			player.posX=target.posX;
			player.posY=target.posY;
			player.posZ=target.posZ;
			player.cameraPitch=target.cameraPitch;
			player.rotationYaw=target.rotationYaw;

			target.posX=pPosX;
			target.posY=pPosY;
			target.posZ=pPosZ;
			target.cameraPitch=pCamPitch;
			target.rotationYaw=pCamYaw;
		}
	}
}
