package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

/**
 * 虚空侵蚀
 */
public class PotionVoidInfected extends BasePotion
{
	public PotionVoidInfected()
	{
		super(icon("void_infected"),true, Keys.colorPotionRooted);
	}

	@Override
	public void postInit()
	{
		registerPotionAttributeModifier(
				SharedMonsterAttributes.MAX_HEALTH,
				Keys.uuidPotionArmorVoidInfected,
				-0.3,
				2
		);
	}
}
