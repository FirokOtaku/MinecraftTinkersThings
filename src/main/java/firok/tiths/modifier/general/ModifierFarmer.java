package firok.tiths.modifier.general;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.ArrayList;
import java.util.List;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 农场主
 *
 * with a chance to double the loots when harvesting crops.
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitFarmer.java
 */
@DevUse
public class ModifierFarmer extends Modifier
{
	public ModifierFarmer()
	{
		super(Colors.ForestGreen);
	}

	public List<ItemStack> processLoot(IModifierToolStack tool, int level, List<ItemStack> loots, LootContext context)
	{
		World world = context.getWorld();
		BlockState state = context.get(LootParameters.BLOCK_STATE);

		if(world.isRemote || state == null || !canTrigger(world, 0.4f) || !(state.getBlock() instanceof CropsBlock)) return loots;
		try
		{
			List<ItemStack> extraLoots = new ArrayList<>(2);

			for(ItemStack loot : loots)
			{
				final int maxSize = loot.getMaxStackSize();
				final int nowSize = loot.getCount();
				if(nowSize == 0) continue;

				if(nowSize + nowSize > maxSize)
				{
					loot.setCount(maxSize);
					ItemStack lootExtra = loot.copy();
					lootExtra.setCount(maxSize - nowSize);
					extraLoots.add(lootExtra);
				}
				else
				{
					loot.setCount(nowSize + nowSize);
				}
			}

			loots.addAll(extraLoots);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("处理loot发生错误");
		}
		return loots;
	}
}
