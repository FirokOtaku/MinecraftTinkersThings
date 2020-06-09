package firok.tiths.potion;

import net.minecraft.entity.SharedMonsterAttributes;

import static firok.tiths.common.Keys.*;

/**
 * 侵蚀
 */
public class PotionEroded extends BasePotion
{
	public PotionEroded()
	{
		super(icon("eroded"),true,colorPotionEroded);
	}

	@Override
	public void postInit()
	{
		registerPotionAttributeModifier(
				SharedMonsterAttributes.MAX_HEALTH,
				uuidPotionErodedMaxHealth,
				-1,
				0
		);
	}
}
