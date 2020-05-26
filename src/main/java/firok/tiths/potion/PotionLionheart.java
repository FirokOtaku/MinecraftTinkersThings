package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

import static firok.tiths.common.Keys.uuidPotionArmorLionheart;

/**
 * 狮心
 */
public class PotionLionheart extends BasePotion
{
	public PotionLionheart()
	{
		super(icon("lionheart"),false, Keys.colorPotionLionheart);
	}

	@Override
	public void postInit()
	{
		registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, uuidPotionArmorLionheart,0.4,2);
	}
}
