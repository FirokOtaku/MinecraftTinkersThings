package firok.tiths.modifier.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class ModifierKleinField extends Modifier
{
	public ModifierKleinField()
	{
		super(0);
	}

	@Override
	public void onAttacked(IModifierToolStack tool, int level, EquipmentContext context, EquipmentSlotType slotType, DamageSource source, float amount, boolean isDirectDamage)
	{
		super.onAttacked(tool, level, context, slotType, source, amount, isDirectDamage);
	}
}
