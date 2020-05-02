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
		super(new ResourceLocation(TinkersThings.MOD_ID,"textures/potions/speed_boost_hyper.png"),false, Keys.colorPotionRooted, false);
	}

	@Override
	public void postInit()
	{
		this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, uuidPotionSpeedHiddenHyper,0.05,2);
	}
}
