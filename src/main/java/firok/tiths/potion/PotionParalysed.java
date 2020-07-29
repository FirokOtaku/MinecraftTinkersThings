package firok.tiths.potion;

import net.minecraft.entity.SharedMonsterAttributes;

import static firok.tiths.common.Keys.*;

// 麻痹
public class PotionParalysed extends BasePotion
{
	public PotionParalysed()
	{
		super(icon("paralysed"),false, colorPotionParalysed);
	}

	/**
	 * @see firok.tiths.common.Potions
	 */
	@Override
	public void postInit()
	{
		registerPotionAttributeModifier(
				SharedMonsterAttributes.MOVEMENT_SPEED,
				uuidPotionParalysedSpeed,
				-0.5,
				2)
		.registerPotionAttributeModifier(
				SharedMonsterAttributes.ATTACK_DAMAGE,
				uuidPotionParalysedDamage,
				-0.5,
				2
		);
	}
}
