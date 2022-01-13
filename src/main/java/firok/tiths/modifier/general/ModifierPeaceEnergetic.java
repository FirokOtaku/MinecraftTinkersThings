package firok.tiths.modifier.general;

import firok.tiths.config.ConfigModifier;
import firok.tiths.util.DevUse;
import firok.tiths.util.Predicates;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTickServer;

/**
 * 平和能量
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitPeaceEnergetic.java
 */
@DevUse
public class ModifierPeaceEnergetic extends Modifier
{
	ModifierPeaceEnergetic()
	{
		super(0xfff501);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{
		if(isSelected && canTickServer(world, 20, 1) && Predicates.isStandStill(holder))
		{
			holder.heal(ConfigModifier.factor_peace_energetic_heal.get().floatValue());
		}
	}
}
