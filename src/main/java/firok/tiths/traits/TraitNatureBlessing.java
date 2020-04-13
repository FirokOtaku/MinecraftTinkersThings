package firok.tiths.traits;

import firok.tiths.common.Configs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitNatureBlessing;
import static firok.tiths.common.Keys.nameTraitNatureBlessing;
import static firok.tiths.util.Predicates.canTick;

// 自然祝福
public class TraitNatureBlessing extends AbstractTrait
{
	public TraitNatureBlessing()
	{
		super(nameTraitNatureBlessing, colorTraitNatureBlessing);
	}

	static long _time=-1;
	static Entity _entity;
	static boolean _hasFound =false;
	static boolean _realCheck(Entity entity,World world)
	{
		final int cx=(int)entity.posX,cy=(int)entity.posY,cz=(int)entity.posZ;
		int countNature=0;
		FOR_X:for(int ox = -Configs.Traits.range_nature_blessing_xz; ox <= Configs.Traits.range_nature_blessing_xz; ox++)
		{
			FOR_Y:for(int oz = -Configs.Traits.range_nature_blessing_xz; oz <= Configs.Traits.range_nature_blessing_xz; oz++)
			{
				FOR_Z:for(int oy = Configs.Traits.range_nature_blessing_y; oy >= -Configs.Traits.range_nature_blessing_y; oy++)
				{
					BlockPos posTemp=new BlockPos(cx+ox,cy+oy,cz+oz);
					IBlockState state=world.getBlockState(posTemp);
					Block block=state.getBlock();
					boolean isNature=block.isLeaves(state,world,posTemp) || block.isWood(world,posTemp);
					if(isNature) countNature++;

					if(countNature >= Configs.Traits.count_nature_blessing)
					{
						return true;
					}
				}
			}
		}

		return false;
	}
	public static boolean check(Entity entity,World world)
	{
		if(Configs.General.enable_single_thread_optimization)
		{
			long now=world.getTotalWorldTime();
			if(_entity==entity && _time==now)
			{
				return _hasFound;
			}

			_entity=entity;
			_time=now;
			_hasFound=_realCheck(entity,world);

			return _hasFound;
		}
		return _realCheck(entity,world);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && canTick(world,200,3) && entity instanceof EntityLivingBase)
		{
			if(check(entity,world))
			{
				ToolHelper.healTool(tool, Configs.Traits.factor_nature_blessing_repair,(EntityLivingBase)entity);
			}
		}
	}
}
