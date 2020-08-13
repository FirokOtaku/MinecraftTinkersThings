package firok.tiths.potion;

import net.minecraft.entity.SharedMonsterAttributes;

import static firok.tiths.common.Keys.*;

public class PotionHardIce extends BasePotion
{
	public PotionHardIce()
	{
		super(icon("hard_ice"),true,colorPotionHardIce);
	}

	/**
	 * @see firok.tiths.common.Potions
	 */
	@Override
	public void postInit()
	{
		registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR,uuidPotionHardIceDefense,4,1)
		.registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR_TOUGHNESS,uuidPotionHardIceToughness,1,1)
		.setBeneficial();
	}
}