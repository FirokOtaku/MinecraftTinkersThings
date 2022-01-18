package firok.tiths.modifier.general;

import firok.tiths.config.ConfigModifier;
import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import static firok.tiths.util.Predicates.canTickClient;
import static firok.tiths.util.Predicates.canTickServer;

/**
 * 辉耀
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitRadiant.java
 */
@DevUse
public class ModifierRadiant extends Modifier
{
	public ModifierRadiant()
	{
		super(Colors.FireBrick);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{
		if(isSelected)
		{
			if(canTickServer(world, 20, 1))
			{
				List<Entity> list = EntityFinders.Nearby(holder, ConfigModifier.range_radiant.get(), Predicates::isLivingAlive);
				if(list.size() <= 0) return;

				for(Entity en : list)
				{
					en.setFire(5);
				}
			}

			if(canTickClient(world, 4, 2))
			{
				Random rand = world.rand;
				world.addOptionalParticle(
						ParticleTypes.FLAME,
						holder.getPosX() + rand.nextDouble() - 0.5,
						holder.getPosY() + 1,
						holder.getPosZ() + rand.nextDouble() - 0.5,
						0, 0, 0
				);
			}
		}
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		if(damageDealt > 0 && target != null)
		{
			target.setFire(10);
		}
		return 0;
	}

	@Override
	public List<ItemStack> processLoot(IModifierToolStack tool, int level, List<ItemStack> loots, LootContext context)
	{
		final Item itemSearedBrick = TinkerSmeltery.searedBrick.get();
		if(context != null && loots.size() > 0)
		{
			Random rand = context.getRandom();
			ListIterator<ItemStack> iter = loots.listIterator();
			while(iter.hasNext())
			{
				ItemStack loot = iter.next();
				if(Predicates.isAnyStone(loot))
				{
					ItemStack stackSearedBrick = new ItemStack(itemSearedBrick, loot.getCount());
					iter.set(stackSearedBrick);
				}
				else
				{
					ItemStack stackSmelted; // todo 自动冶炼
				}
			}
		}
		return loots;
	}

	@Override
	public void afterBlockBreak(IModifierToolStack tool, int level, ToolHarvestContext context)
	{
		World world = context.getWorld();
		if(world.isRemote)
		{
			Random rand = world.rand;
			BlockPos pos = context.getTargetedPos();
			final int x = pos.getX(), y = pos.getY(), z = pos.getZ();

			for(int step = 0; step < 4; step++)
			{
				world.addParticle(ParticleTypes.FLAME,
						x + rand.nextDouble(),
						y + rand.nextDouble(),
						z + rand.nextDouble(),
						0, 0, 0
				);
			}
		}
	}
}
