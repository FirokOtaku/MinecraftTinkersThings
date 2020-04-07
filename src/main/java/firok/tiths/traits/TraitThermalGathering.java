package firok.tiths.traits;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.ArrayList;
import java.util.List;

import static firok.tiths.common.Keys.colorTraitThermalGathering;
import static firok.tiths.common.Keys.nameTraitThermalGathering;
import static firok.tiths.util.Predicates.canTick;

// 热力聚集
public class TraitThermalGathering extends AbstractTrait
{
	public TraitThermalGathering()
	{
		super(nameTraitThermalGathering, colorTraitThermalGathering);
	}


	private static final List<Block> THERMAL_SOURCE=new ArrayList<>();
	static
	{
		THERMAL_SOURCE.add(Blocks.LAVA);
		THERMAL_SOURCE.add(Blocks.FLOWING_LAVA);
		THERMAL_SOURCE.add(Blocks.FIRE);
	}
	public static boolean isThermalSource(Block block)
	{
		return THERMAL_SOURCE.contains(block);
	}

	/**
	 * 注册一个热源方块
	 * @param block 热源方块
	 */
	public static void registerThermalSource(Block block)
	{
		THERMAL_SOURCE.add(block);
	}

	public static boolean canThermalGather(World world)
	{
		return canTick(world,80,4);
	}
	private static Entity _entity=null;
	private static long _tickTime=-1;
	private static boolean _hasFound=false;
	public static boolean checkThermalSource(Entity entity)
	{
		World world=entity.world;
		long timeNow=world.getTotalWorldTime();

		if (entity != _entity || timeNow != _tickTime)
		{
			_entity = entity;
			_tickTime = timeNow;
			_hasFound = false;

			BlockPos center = entity.getPosition();
			FOR_FIND_Y:
			for (int ty = -2; ty <= 1; ty++)
			{
				FOR_FIND_X:
				for (int tx = -2; tx <= 2; tx++)
				{
					FOR_FIND_Z:
					for (int tz = -2; tz <= 2; tz++)
					{
						BlockPos pos2find = center.add(tx, ty, tz);
						Block blockFound = world.getBlockState(pos2find).getBlock();
						if (isThermalSource(blockFound))
						{
							_hasFound = true;
							break FOR_FIND_Y;
						}
					}
				}
			}
		}
		return _hasFound;
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && isSelected && entity instanceof EntityLivingBase && canThermalGather(world))
		{
			boolean hasFound=checkThermalSource(entity);
			if(hasFound)
			{
				ToolHelper.healTool(tool,4,(EntityLivingBase) entity);
			}
		}
	}
}
