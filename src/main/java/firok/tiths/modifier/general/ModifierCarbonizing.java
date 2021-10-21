package firok.tiths.modifier.general;

import firok.tiths.config.ConfigModifier;
import firok.tiths.util.Actions;
import firok.tiths.util.DevUse;
import firok.tiths.util.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

// 碳化
@DevUse(isPlaceholder = true)
public class ModifierCarbonizing extends Modifier
{
	public ModifierCarbonizing()
	{
		super(0x2b2b2b);
	}

	@Nullable
	@Override
	public Boolean removeBlock(IModifierToolStack tool, int level, ToolHarvestContext context) {
		ServerWorld world = context.getWorld();
		Random rand = world.rand;
		ServerPlayerEntity player = context.getPlayer();

		boolean playSound = false;

		BlockState state = context.getState();
		BlockPos pos = context.getPos();

		List<ItemStack> drops = Block.getDrops(state, world, pos, world.getTileEntity(pos));

		for (int i = 0; i < drops.size(); i++) {
			ItemStack drop = drops.get(i);
			if (ItemTags.STONE_BRICKS.contains(drop.getItem()) &&
					Predicates.canTrigger(rand, ConfigModifier.rate_carbonizing_transform.get() * drop.getCount()))
			{
				drops.set(i, new ItemStack(Items.COAL, drop.getCount()));
				playSound = true;
			}
		}

		if (playSound)
		{
			// todo play sound
			// player.world.playSound(null, player.posX, player.posY, player.posZ,
			// 		SoundEvents.effectFriction, SoundCategory.MASTER, 1, 1);
		}

		drops.forEach(itemStack -> Actions.CauseSpawnItem(player, itemStack));

		return false;
	}
}
