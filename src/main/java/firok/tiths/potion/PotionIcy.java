package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import static firok.tiths.util.Predicates.canTick;

/**
 * 酸蚀
 */
public class PotionIcy extends BasePotion
{
	public PotionIcy()
	{
		super(new ResourceLocation(TinkersThings.MOD_ID,"textures/potions/icy.png"),false, Keys.colorPotionIcy);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		level++;
		return canTick(tick,Math.max(1,20/level),0);
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		if(entity.isBurning())
		{
			entity.extinguish();
		}
	}
}