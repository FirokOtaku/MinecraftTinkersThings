package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTraitLeveled;

import static firok.tiths.common.Keys.colorTraitWarmSoft;
import static firok.tiths.common.Keys.nameTraitWarmSoft;

/**
 * 温软 - 护甲
 */
public class TraitArmorWarmSoft extends AbstractArmorTraitLeveled
{
	public TraitArmorWarmSoft(int level)
	{
		super(nameTraitWarmSoft,colorTraitWarmSoft,2,level);
	}
}
