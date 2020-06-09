package firok.tiths.potion;

import firok.tiths.common.Keys;
import net.minecraft.entity.SharedMonsterAttributes;

import static firok.tiths.common.Keys.uuidPotionArmorSoftenedArmor;
import static firok.tiths.common.Keys.uuidPotionArmorSoftenedArmorToughness;

public class PotionArmorSoftened extends BasePotion
{
	public PotionArmorSoftened()
	{
		super(icon("armor_softened"),true, Keys.colorPotionArmorSoftened);
	}

	@Override
	public void postInit()
	{
		registerPotionAttributeModifier(
				SharedMonsterAttributes.ARMOR,
				uuidPotionArmorSoftenedArmor,
				-0.2,
				2
		)
		.registerPotionAttributeModifier(
				SharedMonsterAttributes.ARMOR_TOUGHNESS,
				uuidPotionArmorSoftenedArmorToughness,
				-0.2,
				2
		);
	}
}
