package firok.mtim.traits;

import firok.mtim.util.Colors;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

/**
 * 换位
 */
public class TraitSwitching extends AbstractTrait
{
	public TraitSwitching()
	{
		super("switching", Colors.Beige);
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
