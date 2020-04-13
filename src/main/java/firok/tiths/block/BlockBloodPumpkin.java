package firok.tiths.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

/**
 * 血南瓜
 */
public class BlockBloodPumpkin extends BlockHorizontal
{
	public BlockBloodPumpkin()
	{
		super(Material.WOOD);
//		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

//	private BlockPattern snowmanBasePattern;
//	private BlockPattern snowmanPattern;
//	private BlockPattern golemBasePattern;
//	private BlockPattern golemPattern;
//	private static final Predicate<IBlockState> IS_BLOOD_PUMPKIN =
//			blockstate -> blockstate != null && blockstate.getBlock() == firok.tiths.common.Blocks.blockBloodPumpkin;
//
//	/**
//	 * Called after the block is set in the Chunk data, but before the Tile Entity is set
//	 */
//	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
//	{
//		super.onBlockAdded(worldIn, pos, state);
//		this.trySpawnGolem(worldIn, pos);
//	}
//
//	public boolean canDispenserPlace(World worldIn, BlockPos pos)
//	{
//		return this.getGolemBasePattern().match(worldIn, pos) != null;
//	}
//
//	private void trySpawnGolem(World worldIn, BlockPos pos)
//	{
//		BlockPattern.PatternHelper patternHelper = this.getGolemPattern().match(worldIn, pos);
//
//		if (patternHelper != null)
//		{
//			for (int i = 0; i < this.getGolemPattern().getThumbLength(); ++i)
//			{
//				BlockWorldState blockworldstate = patternHelper.translateOffset(0, i, 0);
//				worldIn.setBlockState(blockworldstate.getPos(), Blocks.AIR.getDefaultState(), 2);
//			}
//
//			EntitySnowman entity2spawn = new EntitySnowman(worldIn);
//			BlockPos pos2spawn = patternHelper.translateOffset(0, 2, 0).getPos();
//			entity2spawn.setLocationAndAngles((double)pos2spawn.getX() + 0.5D, (double)pos2spawn.getY() + 0.05D, (double)pos2spawn.getZ() + 0.5D, 0.0F, 0.0F);
//			worldIn.spawnEntity(entity2spawn);
//
//			for (EntityPlayerMP entityplayermp : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, entity2spawn.getEntityBoundingBox().grow(5.0D)))
//			{
//				CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp, entity2spawn);
//			}
//
//			for (int l = 0; l < 120; ++l)
//			{
//				worldIn.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, (double)pos2spawn.getX() + worldIn.rand.nextDouble(), (double)pos2spawn.getY() + worldIn.rand.nextDouble() * 2.5D, (double)pos2spawn.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
//			}
//
//			for (int i1 = 0; i1 < this.getSnowmanPattern().getThumbLength(); ++i1)
//			{
//				BlockWorldState blockworldstate2 = patternHelper.translateOffset(0, i1, 0);
//				worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.AIR, false);
//			}
//		}
//	}
//
//	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
//	{
//		return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) &&
//		       worldIn.isSideSolid(pos.down(), EnumFacing.UP);
//	}
//
//	/**
//	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
//	 * blockstate.
//	 */
//	public IBlockState withRotation(IBlockState state, Rotation rot)
//	{
//		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
//	}
//
//	/**
//	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
//	 * blockstate.
//	 */
//	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
//	{
//		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
//	}
//
//	/**
//	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
//	 * IBlockstate
//	 */
//	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
//	{
//		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
//	}
//
//	/**
//	 * Convert the given metadata into a BlockState for this Block
//	 */
//	public IBlockState getStateFromMeta(int meta)
//	{
//		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
//	}
//
//	/**
//	 * Convert the BlockState into the correct metadata value
//	 */
//	public int getMetaFromState(IBlockState state)
//	{
//		return (state.getValue(FACING)).getHorizontalIndex();
//	}
//
//	protected BlockStateContainer createBlockState()
//	{
//		return new BlockStateContainer(this, new IProperty[] {FACING});
//	}
//
//	protected BlockPattern getGolemBasePattern()
//	{
//		if (this.golemBasePattern == null)
//		{
//			this.golemBasePattern =
//			FactoryBlockPattern.start()
//			.aisle("~ ~", "###", "~#~")
//			.where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(firok.tiths.common.Blocks.blockMercurySulfide)))
//			.where('~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR)))
//			.build();
//		}
//
//		return this.golemBasePattern;
//	}
//
//	protected BlockPattern getGolemPattern()
//	{
//		if (this.golemPattern == null)
//		{
//			this.golemPattern =
//			FactoryBlockPattern.start()
//			.aisle("~^~", "###", "~#~").where('^', BlockWorldState.hasState(IS_BLOOD_PUMPKIN))
//			.where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(firok.tiths.common.Blocks.blockMercurySulfide)))
//			.where('~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR)))
//			.build();
//		}
//
//		return this.golemPattern;
//	}
}
