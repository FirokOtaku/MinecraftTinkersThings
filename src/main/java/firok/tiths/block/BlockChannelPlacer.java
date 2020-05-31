package firok.tiths.block;

import firok.tiths.common.Items;
import firok.tiths.util.Actions;
import firok.tiths.util.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.gadgets.TinkerGadgets;
import slimeknights.tconstruct.shared.TinkerCommons;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 通道放置器
 */
public class BlockChannelPlacer extends Block // BlockDirectional
{
//	public static final PropertyDirection FACING = BlockDirectional.FACING;

	public BlockChannelPlacer()
	{
		super(Material.IRON);
		this.setHardness(25);
		this.setResistance(10);
//		this.setDefaultState(getDefaultState().withProperty(FACING,EnumFacing.DOWN));
	}

//	public IBlockState withRotation(IBlockState state, Rotation rot)
//	{
//		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
//	}
//	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
//	{
//		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
//	}

//	public IBlockState getStateFromMeta(int meta)
//	{
//		EnumFacing enumfacing;
//
//		switch (meta & 7)
//		{
//			case 1: enumfacing = EnumFacing.EAST; break;
//			case 2: enumfacing = EnumFacing.WEST; break;
//			case 3: enumfacing = EnumFacing.SOUTH; break;
//			case 4: enumfacing = EnumFacing.NORTH; break;
//			case 5: enumfacing = EnumFacing.UP; break;
//			default: case 0: enumfacing = EnumFacing.DOWN; break;
//		}
//
//		return this.getDefaultState().withProperty(FACING, enumfacing);
//	}

//	public int getMetaFromState(IBlockState state)
//	{
//		switch (state.getValue(FACING))
//		{
//			case EAST: return 1;
//			case WEST: return 2;
//			case SOUTH: return 3;
//			case NORTH: return 4;
//			case UP: return 5;
//			default: case DOWN: return 0;
//		}
//	}

//	protected BlockStateContainer createBlockState()
//	{
//		return new BlockStateContainer(this, FACING);
//	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stackHeld=player.getHeldItem(hand);

		if(stackHeld.isEmpty()) return false;

		Item itemHeld=stackHeld.getItem();
//		if(itemHeld == net.minecraft.init.Items.IRON_SWORD)
//		{
//			world.setBlockState(
//					pos,
//					state.withProperty(FACING,facing)
//			);
//			return true;
//		}

		if((itemHeld==Items.channelPackStone || itemHeld==Items.channelPackWood) &&
		   (facing==EnumFacing.NORTH ||
		   facing==EnumFacing.SOUTH ||
		   facing==EnumFacing.EAST ||
		   facing==EnumFacing.WEST)
		)
		{
			boolean isWood=itemHeld==Items.channelPackWood;
			IBlockState stateLadder=
					(isWood?Blocks.LADDER.getDefaultState():TinkerGadgets.stoneLadder.getDefaultState())
					.withProperty(BlockLadder.FACING,facing);
			IBlockState stateChannel=firok.tiths.common.Blocks.blockConsolidatedGlass.getDefaultState();

			EnumFacing blockFacing=EnumFacing.DOWN; // state.getValue(FACING);
			final int dox=blockFacing.getFrontOffsetX(),doy=blockFacing.getFrontOffsetY(),doz=blockFacing.getFrontOffsetZ(); // direction offset

			List<BlockPos> listPosChannel=new ArrayList<>();
			BlockPos posLadder=null;

			FOR_DIR_X:for(int tox=-1;tox<=1;tox++) // temp offset x
			{
				FOR_DIR_Y:for(int toy=-1;toy<=1;toy++) // temp offset y
				{
					FOR_DIR_Z:for(int toz=-1;toz<=1;toz++) // temp offset z
					{
						if(tox==0 && toy==0 && toz==0) continue;
						if(dox * tox + doy * toy + doz * toz<=0) continue; // math is funny, isn't it?

						TRY_REPLACE:for(int len=0;len<32;len++)
						{
							BlockPos posTemp=pos.add(tox + dox * len ,toy + doy * len ,toz + doz * len); // temp pos

							boolean isCenter= (
									(tox!=0 && (toy==0 && toz==0)) ||
									(toy!=0 && (tox==0 && toz==0)) ||
									(toz!=0 && (tox==0 && toy==0))
									);

							IBlockState stateOld=world.getBlockState(posTemp);
							Block blockOld=stateOld.getBlock();
							IBlockState stateNew=isCenter?stateLadder:stateChannel;

							if(Predicates.isAir(blockOld) || Predicates.isWater(blockOld))
							{
//								world.setBlockState(posTemp,stateNew);
								if(isCenter) posLadder=posTemp;
								else listPosChannel.add(posTemp);

								break TRY_REPLACE;
							}
							else if(stateOld.equals(stateNew))
							{
								continue TRY_REPLACE;
							}

							break TRY_REPLACE;
						}
					}
				}
			}

			if(listPosChannel.size()>0)
			{
				for(BlockPos posChannel:listPosChannel)
				{
					world.setBlockState(posChannel,stateChannel);
				}
			}
			if(posLadder!=null)
			{
				world.setBlockState(posLadder,stateLadder);
			}

			int channelBack=8-listPosChannel.size();
			stackHeld.shrink(1);
			if(channelBack>0) Actions.CauseSpawnItem(player,new ItemStack(firok.tiths.common.Blocks.blockConsolidatedGlass));
			if(posLadder==null) Actions.CauseSpawnItem(player,new ItemStack(stateChannel.getBlock()));
		}

		return true;
	}
}
