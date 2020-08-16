package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;

import static firok.tiths.common.Keys.colorTraitThresholdLimiting;
import static firok.tiths.common.Keys.nameTraitThresholdLimiting;

/**
 * 阈限 - 护甲
 */
public class TraitArmorThresholdLimiting extends AbstractArmorTrait
{
	public TraitArmorThresholdLimiting()
	{
		super(nameTraitThresholdLimiting,colorTraitThresholdLimiting);
	}
}
