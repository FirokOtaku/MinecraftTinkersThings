package firok.tiths.block;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BlockLeaf extends BlockLeaves
{
	// 这两个数字都是0~100的整数
	private Block blockSapling;
	private int rateSapling;
	private Item itemExtra;
	private int rateExtra;

	public BlockLeaf()
	{
		super();
		this.setHardness(0.3f);

//		this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, true));
	}
	public BlockLeaf(Block itemSapling,int rateSapling)
	{
		this();

		this.blockSapling =itemSapling;
		this.rateSapling=rateSapling;
	}
	public BlockLeaf(Block itemSapling,int rateSapling,Item itemExtra,int rateExtra)
	{
		this(itemSapling,rateSapling);

		this.itemExtra=itemExtra;
		this.rateExtra=rateExtra;
	}

//	@Override
//	public void updateTick(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, Random rand) {
//		super.updateTick(worldIn, pos, state, rand);
//	}

//	@Override
//	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
//		;
//	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return Blocks.LEAVES.isOpaqueCube(state);
	}

	@Nonnull
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return Blocks.LEAVES.getBlockLayer();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		// isOpaqueCube returns !leavesFancy to us. We have to fix the variable before calling super
		this.leavesFancy = !Blocks.LEAVES.isOpaqueCube(blockState);

		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	protected int getSaplingDropChance(IBlockState state) {
		return rateSapling;
	}

	// sapling item
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return blockSapling!=null?Item.getItemFromBlock(blockSapling):null;
	}

	@Override
	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
		if(itemExtra!=null && rateExtra>0 && worldIn.rand.nextInt(100)>this.rateExtra)
		{
			ItemStack stack=new ItemStack(itemExtra);
			spawnAsEntity(worldIn, pos, stack);
		}
//		if(worldIn.rand.nextInt(chance) == 0) {
//			ItemStack stack = null;
//			if(state.getValue(BlockSlimeGrass.FOLIAGE) == BlockSlimeGrass.FoliageType.PURPLE) {
//				stack = TinkerCommons.matSlimeBallPurple.copy();
//			}
//			else if(state.getValue(BlockSlimeGrass.FOLIAGE) == BlockSlimeGrass.FoliageType.BLUE) {
//				if(worldIn.rand.nextInt(3) == 0) {
//					stack = TinkerCommons.matSlimeBallBlue.copy();
//				}
//				else {
//					stack = new ItemStack(Items.SLIME_BALL);
//				}
//			}
//
//			if(stack != null) {
//				spawnAsEntity(worldIn, pos, stack);
//			}
//		}
	}

	// sapling meta
	@Override
	public int damageDropped(IBlockState state) {
//		return (state.getValue(BlockSlimeGrass.FOLIAGE)).ordinal() & 3; // only first 2 bits
		return 0;
	}

	// item dropped on silktouching

	@Nonnull
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this));
	}

//	@Nonnull
//	@Override
//	protected BlockStateContainer createBlockState() {
//		return new BlockStateContainer(this, BlockSlimeGrass.FOLIAGE, CHECK_DECAY, DECAYABLE);
//	}

//	@Nonnull
//	@Override
//	public IBlockState getStateFromMeta(int meta) {
//		int type = meta % 4;
//		if(type < 0 || type >= BlockSlimeGrass.FoliageType.values().length) {
//			type = 0;
//		}
//		BlockSlimeGrass.FoliageType grass = BlockSlimeGrass.FoliageType.values()[type];
//		return this.getDefaultState()
//				.withProperty(BlockSlimeGrass.FOLIAGE, grass)
//				.withProperty(DECAYABLE, (meta & 4) == 0)
//				.withProperty(CHECK_DECAY, (meta & 8) > 0);
//	}

//	@Override
//	public int getMetaFromState(IBlockState state) {
//
//		int meta = (state.getValue(BlockSlimeGrass.FOLIAGE)).ordinal() & 3; // only first 2 bits
//
//		if(!state.getValue(DECAYABLE)) {
//			meta |= 4;
//		}
//
//		if(state.getValue(CHECK_DECAY)) {
//			meta |= 8;
//		}
//
//		return meta;
//	}

	@Nonnull
	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		throw new UnsupportedOperationException(); // unused by our code.
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		IBlockState state = world.getBlockState(pos);
		return Lists.newArrayList(getSilkTouchDrop(state));
	}

	@Override
	public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}
}
