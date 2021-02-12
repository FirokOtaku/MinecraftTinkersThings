package firok.tiths.potion;

import net.minecraft.entity.SharedMonsterAttributes;

import static firok.tiths.common.Keys.colorPotionTranquilized;
import static firok.tiths.common.Keys.uuidPotionTranquilizedFollowRange;

public class PotionTranquilized extends BasePotion
{
	public PotionTranquilized()
	{
		super(icon("tranquilized"),true,colorPotionTranquilized);
	}

	@Override
	public void postInit()
	{
		registerPotionAttributeModifier(
				SharedMonsterAttributes.FOLLOW_RANGE,
				uuidPotionTranquilizedFollowRange,
				0.125,
				1
		);
	}
}
