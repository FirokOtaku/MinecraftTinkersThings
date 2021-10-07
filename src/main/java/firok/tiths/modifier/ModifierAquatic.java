package firok.tiths.modifier;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static firok.tiths.util.Predicates.canTickServer;

/**
 * 水生
 *
 * @author Firok
 * @since 0.4.2.0
 */
@DevUse(isPlaceholder = true)
public class ModifierAquatic extends Modifier
{
	public ModifierAquatic()
	{
		super(0x32bda7);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder,
	                            int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{
		if(canTickServer(world, 100, 0))
		{
			if(holder.isInWater())
			{
				ToolStack ts = ToolStack.from(stack);
				if(ts.isBroken()) return;

				ToolDamageUtil.directDamage(ts, -1, holder, stack);
			}
		}
	}
}
