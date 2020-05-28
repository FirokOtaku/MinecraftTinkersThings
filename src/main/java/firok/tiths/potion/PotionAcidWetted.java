package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import firok.tiths.util.Actions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 酸蚀
 */
public class PotionAcidWetted extends BasePotion
{
	public PotionAcidWetted()
	{
		super(icon("acid_wetted"),true, Keys.colorPotionAcidWetted);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return canTick(tick,5,0);
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		final int damage=5 + level * 5;

		Actions.CauseAcidDamage(entity,damage,canTrigger(entity.world,0.15));
	}
}