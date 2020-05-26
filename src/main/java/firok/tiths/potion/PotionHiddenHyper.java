package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

import static firok.tiths.common.Keys.uuidPotionSpeedHiddenHyper;

/**
 * 特性 - 振奋 (隐藏状态)
 */
public class PotionHiddenHyper extends BasePotion
{
	public PotionHiddenHyper()
	{
		super(icon("speed_boost_hyper"),false, Keys.colorPotionHyper, false);
	}

	@Override
	public void postInit()
	{
		this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, uuidPotionSpeedHiddenHyper,0.05,2);
	}
}
