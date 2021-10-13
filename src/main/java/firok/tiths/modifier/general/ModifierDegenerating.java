package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.shared.TinkerCommons;

import static firok.tiths.util.Predicates.canTickServer;

/**
 * 简并
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitDegenerating.java
 */
@DevUse(isPlaceholder = true)
public class ModifierDegenerating extends Modifier
{
	public ModifierDegenerating()
	{
		super(0xa0ca72);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{
		if(canTickServer(world,80,0) && !tool.isBroken())
		{
			int amount = 0;

			int durMax= tool.getStats().getInt(ToolStats.DURABILITY);
			int durNow= tool.getCurrentDurability();
			int durHalf=durMax/2;
			if(durNow > durHalf) amount = 1;
			else if(durNow < durHalf) amount = -1;

			if(amount != 0)
				ToolDamageUtil.damage(tool, amount, holder, stack);
		}
	}
}
