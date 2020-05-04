package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import firok.tiths.util.Values;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

/**
 * 特性 - 可溶 (隐藏状态)
 */
public class PotionHiddenSoluble extends BasePotion
{
	public PotionHiddenSoluble()
	{
		super(new ResourceLocation(TinkersThings.MOD_ID,"textures/potions/speed_boost_soluble.png"),false, Keys.colorPotionRooted, false);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		if(!entity.isInWater() || entity.isSneaking()) return;

		level++;

		float factor = entity.rotationYaw * Values.FAC;
		float moX= - MathHelper.sin( factor ) * level / 25;
		float moZ=   MathHelper.cos( factor ) * level / 25;
		entity.motionX += moX;
		entity.motionZ += moZ;
	}
}