package firok.tiths.block;

import firok.tiths.common.DamageSources;
import firok.tiths.util.Actions;
import firok.tiths.util.InnerActions;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static firok.tiths.util.Predicates.canTick;

public class BlockShadowFire extends Block
{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);

	public BlockShadowFire()
	{
		super(Material.GROUND);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(AGE, 0));
		this.setTickRandomly(true);
		this.setLightLevel(0);
		this.setSoundType(SoundType.CLOTH);
	}
	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state;
	}


	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random random)
	{
		return 0;
	}

	/**
	 * How many world ticks before ticking
	 */
	public int tickRate(World worldIn)
	{
		return 30;
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isAreaLoaded(pos, 2)) return; // Forge: prevent loading unloaded chunks when spreading fire

		IBlockState stateDown = worldIn.getBlockState(pos.down());
		Block blockDown = stateDown.getBlock();
		boolean solidTopDown = blockDown.isTopSolid(stateDown);

		if (!solidTopDown || Actions.getLight(worldIn,pos) >= 7)
		{
			worldIn.setBlockToAir(pos);
		}

		if (blockDown != Blocks.OBSIDIAN)
		{
			int age = state.getValue(AGE);
			age += rand.nextFloat() > 0.35 ? 1 : 0;
			if(age >= 15)
			{
				worldIn.setBlockToAir(pos);
			}
			else
			{
				worldIn.setBlockState(pos,state.withProperty(AGE,age));
			}
		}
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn)+rand.nextInt(10));
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(!worldIn.isRemote && entityIn instanceof EntityLivingBase && canTick(worldIn,5,0))
		{
			int light = Actions.getLight(entityIn);
			if(light < 7)
			{
				entityIn.attackEntityFrom(DamageSources.ShadowFireDamage, 3);
			}
			else
			{
				worldIn.setBlockToAir(pos);
			}
		}
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).isTopSolid();
	}

	/**
	 * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * block, etc.
	 */
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!worldIn.getBlockState(pos.down()).isTopSolid())
		{
			worldIn.setBlockToAir(pos);
		}
	}

	public boolean requiresUpdates()
	{
		return false;
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile Entity is set
	 */
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!worldIn.getBlockState(pos.down()).isTopSolid())
		{
			worldIn.setBlockToAir(pos);
		}
		else
		{
			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn)+worldIn.rand.nextInt(10) );
		}
	}

	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return MapColor.WATER;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (rand.nextInt(24) == 0)
		{
			worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(AGE, meta);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(AGE);
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, AGE);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		InnerActions.addInformation(this, tooltip, advanced);
	}
}
