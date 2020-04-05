package firok.tiths.entity.mob;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

// 血傀儡
public class BloodGolem extends EntityMob  implements IRangedAttackMob
{
	public BloodGolem(World worldIn)
	{
		super(worldIn);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		;
	}

	@Override
	public void setSwingingArms(boolean swingingArms)
	{
		;
	}
}
