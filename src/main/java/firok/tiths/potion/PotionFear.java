package firok.tiths.potion;

import firok.tiths.TinkersThings;
import net.minecraft.util.ResourceLocation;

/**
 * 恐惧
 */
public class PotionFear extends BasePotion
{
	public PotionFear()
	{
		super(icon("fear"),true,0);
	}

//	@Override
//	public boolean isReady(int tick, int level)
//	{
//		return true;
//	}
//
//	@Override
//	public void performEffect(EntityLivingBase entity, int level)
//	{
//		EntityLivingBase revengeTarget=entity.getRevengeTarget();
//		if(revengeTarget!=null && revengeTarget.isEntityAlive())
//		{
//			double mx=0;
//			double my=0;
//			double mz=0;
//		}
//	}
}
