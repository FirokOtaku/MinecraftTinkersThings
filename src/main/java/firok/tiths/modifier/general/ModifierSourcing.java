package firok.tiths.modifier.general;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.block.StemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.List;

/**
 * 思源
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitSourcing.java
 */
@DevUse
public class ModifierSourcing extends Modifier
{
	public ModifierSourcing()
	{
		super(Colors.Beige);
	}

	@Override
	public List<ItemStack> processLoot(IModifierToolStack tool, int level, List<ItemStack> loots, LootContext context)
	{
		// todo 增加掉落
		return loots;
	}
}
