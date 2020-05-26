package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

/**
 * 特性 - 浮力 (隐藏状态)
 */
public class PotionHiddenBuoyant extends BasePotion
{
	public PotionHiddenBuoyant()
	{
		super(new ResourceLocation(TinkersThings.MOD_ID,"textures/potions/buoyant.png"),false, Keys.colorPotionBuoyant, false);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity,final int level)
	{
		if(entity.isInWater())
		{
			boolean up=entity.motionY >= -0.2;
			boolean isSneaking=entity.isSneaking();
			for(int i=0;i<level+1;i++)
			{
				entity.motionY = up ? entity.motionY + (isSneaking?0.01:0.05) : entity.motionY * (isSneaking? 1 : 0.8);
			}
		}
	}
}