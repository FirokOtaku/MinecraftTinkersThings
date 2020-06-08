package firok.tiths.potion;

import firok.tiths.util.InnerActions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

import static firok.tiths.common.Keys.colorPotionEddying;

/**
 * 旋流
 */
public class PotionEddying extends BasePotion
{
	public PotionEddying()
	{
		super(icon("eddying"),true,colorPotionEddying);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		if(entity.isServerWorld() && entity.isInWater())
		{
			final int cycle=60;
			final float factorCycle=360f/cycle;

			float angle=entity.ticksExisted % cycle * factorCycle * InnerActions.FAC;

			entity.motionX+= MathHelper.cos(angle) * 0.1;
			entity.motionY+= -0.12;
			entity.motionZ+= MathHelper.sin(angle) * 0.1;
		}
	}
}
