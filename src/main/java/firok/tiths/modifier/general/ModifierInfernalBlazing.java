package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTickServer;

/**
 * 狱炎
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitInfernalBlazing.java
 */
@DevUse
public class ModifierInfernalBlazing extends Modifier
{
	public ModifierInfernalBlazing()
	{
		super(0x76222d);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{
		if(isSelected && canTickServer(world, 80, 4))
		{
			holder.setFire(4);
		}
	}
}
