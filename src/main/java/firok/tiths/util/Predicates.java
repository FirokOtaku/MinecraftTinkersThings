package firok.tiths.util;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.IPlantable;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;

import static net.minecraft.block.material.Material.*;

import java.util.Random;

public final class Predicates
{
	private Predicates() {}

	/* === time predicates === */

	public static boolean canTick(long time, int interval, int offset)
	{
		return time % interval == offset;
	}
	public static boolean canTickServer(World world, int interval, int offset)
	{
		return world != null && !world.isRemote && canTick(world.getGameTime(), interval, offset);
	}
	public static boolean canTickClient(World world, int interval, int offset)
	{
		return world != null && world.isRemote && canTick(world.getGameTime(), interval, offset);
	}

	public static boolean canTrigger(Random rand, double rate)
	{
		return rand != null && rand.nextDouble() < rate;
	}
	public static boolean canTrigger(World world, double rate)
	{
		return world != null && world.rand.nextDouble() < rate;
	}
	public static boolean canTrigger(ToolAttackContext context, ForgeConfigSpec.DoubleValue rate)
	{
		return canTrigger(context.getAttacker().world, rate.get());
	}
	public static boolean canTrigger(ToolHarvestContext context, ForgeConfigSpec.DoubleValue rate)
	{
		return canTrigger(context.getWorld(), rate.get());
	}

	/* === block and item predicates === */

	public static boolean isAir(BlockState state)
	{
		return state.getBlock() == Blocks.AIR;
	}
	public static boolean isDirt(BlockState state)
	{
		return state.getBlock() == Blocks.DIRT;
	}


	/* === entity selectors === */

	public static boolean isLivingAlive(Entity en) { return en instanceof LivingEntity && en.isAlive(); }

	public static boolean isMobAlive(Entity en) { return  en instanceof MobEntity && en.isAlive(); }

	public static boolean isPlayerAlive(Entity en) { return  en.getType() == EntityType.PLAYER && en.isAlive(); }

	public static boolean isPigAlive(Entity en) { return  en.getType() == EntityType.PIG && en.isAlive(); }

	public static boolean isSheepAlive(Entity en) { return  en.getType() == EntityType.SHEEP && en.isAlive(); }

	public static boolean isCowAlive(Entity en) { return  en.getType() == EntityType.COW && en.isAlive(); }

	public static boolean isChickenAlive(Entity en) { return en.getType() == EntityType.CHICKEN && en.isAlive(); }

	public static boolean isCatAlive(Entity en) { return en.getType() == EntityType.CAT && en.isAlive(); }

	public static boolean isRabbitAlive(Entity en) { return en.getType() == EntityType.RABBIT && en.isAlive(); }

	public static boolean isPlant(BlockState state)
	{
		Block block = state.getBlock();
		Material material = state.getMaterial();
		return block instanceof AbstractPlantBlock ||
				block instanceof LeavesBlock ||
				block instanceof IGrowable ||
				block instanceof IPlantable ||
				material == PLANTS ||
				material == OCEAN_PLANT ||
				material == TALL_PLANTS ||
				material == NETHER_PLANTS ||
				material == WOOD ||
				material == NETHER_WOOD ||
				material == LEAVES ||
				material == CACTUS ||
				material == SEA_GRASS ||
				material == BAMBOO ||
				material == BAMBOO_SAPLING ||
				material == CORAL
				;
	}
}
