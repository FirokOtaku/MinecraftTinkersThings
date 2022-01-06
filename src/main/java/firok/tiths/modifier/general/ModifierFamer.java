package firok.tiths.modifier.general;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;

import static firok.tiths.util.Predicates.canTickServer;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 农场主
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitFarmer.java
 */
@DevUse
public class ModifierFamer extends Modifier
{
	public ModifierFamer()
	{
		super(Colors.ForestGreen);
	}

	public void afterBlockBreak(IModifierToolStack tool, int level, ToolHarvestContext context)
	{
		World world = context.getWorld();
		if(world.isRemote || !canTrigger(world, 0.4f)) return;

		BlockState state = context.getState();
		if(state.getBlock() instanceof CropsBlock)
		{
			// todo increase drops
		}
	}
}
