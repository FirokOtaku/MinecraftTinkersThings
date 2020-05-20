package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;

import static firok.tiths.common.Keys.colorTraitDeadening;
import static firok.tiths.common.Keys.nameTraitDeadening;

/**
 * 吸音 护甲
 */
public class TraitArmorDeadening extends AbstractArmorTrait
{
	public TraitArmorDeadening()
	{
		super(nameTraitDeadening,colorTraitDeadening);

		addAspects(new ModifierAspect.SingleAspect(this), ModifierAspect.freeModifier);
	}
}
