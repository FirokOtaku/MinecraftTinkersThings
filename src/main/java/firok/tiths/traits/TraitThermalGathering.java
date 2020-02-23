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

import static firok.tiths.common.Keys.*;
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

	/**
	 * 注册一个热源方块
	 * @param block 热源方块
	 */
	public static void registerThermalSource(Block block)
	{
		THERMAL_SOURCE.add(block);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && isSelected && entity instanceof EntityLivingBase && canTick(world,80,4))
		{
			BlockPos center=entity.getPosition();
			FOR_FIND_Y:for(int ty=-2;ty<=1;ty++)
			{
				FOR_FIND_X:for(int tx=-2;tx<=2;tx++)
				{
					FOR_FIND_Z:for(int tz=-2;tz<=2;tz++)
					{
						BlockPos pos2find=center.add(tx,ty,tz);
						Block blockFound=world.getBlockState(pos2find).getBlock();
						if(THERMAL_SOURCE.contains(blockFound))
						{
							ToolHelper.healTool(tool,4,(EntityLivingBase) entity);
							break FOR_FIND_Y;
						}
					}
				}
			}
		}
	}
}
