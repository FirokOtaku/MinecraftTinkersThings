package firok.tiths.traits;

import firok.tiths.util.Actions;
import firok.tiths.util.InnerActions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitRespecting;
import static firok.tiths.common.Keys.nameTraitRespecting;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 尊重
 */
public class TraitRespecting extends AbstractTrait
{
	public TraitRespecting()
	{
		super(nameTraitRespecting,colorTraitRespecting);
	}

	@Override
	public boolean isCriticalHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target)
	{
		if(!canTrigger(player.world,0.6)) return false;

		double px=player.posX,py=player.posY+player.getEyeHeight(),pz=player.posZ;
		double tx=target.posX,ty=target.posY+target.getEyeHeight(),tz=target.posZ;
		Vec3d directionReal=Actions.CalcUnitVector(new Vec3d(tx-px,ty-py,tz-pz));
		if(directionReal==null) return false;

		Vec3d directionPlayerLook= InnerActions.getEntityForward(player);
		Vec3d directionTargetLook= InnerActions.getEntityForward(target);

		// 下面是几个单位向量的点乘
		return directionPlayerLook.dotProduct(directionReal) > 0.85 && directionPlayerLook.dotProduct(directionTargetLook) < -0.9;
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return newDamage + (isCritical? damage * 0.5f : 0);
	}
}
