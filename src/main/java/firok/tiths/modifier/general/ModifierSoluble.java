package firok.tiths.modifier.general;

import firok.tiths.config.ConfigModifier;
import firok.tiths.util.Colors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTickClient;
import static firok.tiths.util.Predicates.canTickServer;

/**
 * 可溶
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitSoluble.java
 */
public class ModifierSoluble extends Modifier
{
	public ModifierSoluble()
	{
		super(Colors.CadetBlue);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{
		if(isSelected && holder.isInWater())
		{
			if(canTickServer(world, 15, 2))
			{
				ToolDamageUtil.damage(tool, ConfigModifier.factor_soluble.get(), holder, stack);
			}

//			if(canTickClient(world, 4, 3))
//			{
//				// todo particle effect
//			}
		}
	}
}
