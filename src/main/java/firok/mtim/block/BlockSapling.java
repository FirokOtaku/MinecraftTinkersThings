package firok.mtim.block;


import firok.mtim.world.IGenerate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BlockSapling extends net.minecraft.block.BlockSapling
{
	IGenerate generate=null;
	int maxStage=0;
	public BlockSapling()
	{
//		super();
	}
	public BlockSapling(IGenerate generate,int maxStage)
	{
		this();
		this.generate=generate;
		this.maxStage=maxStage;
	}

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

//	protected BlockSapling() {
//		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockPlanks.EnumType.OAK).withProperty(STAGE, 0));
//		this.setCreativeTab(CreativeTabs.DECORATIONS);
//	}

	public String getLocalizedName() {
		return I18n.translateToLocal(this.getUnlocalizedName() + "." + BlockPlanks.EnumType.OAK.getUnlocalizedName() + ".name");
	}

	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (!world.isRemote) {
			super.updateTick(world, pos, state, rand);
			if (!world.isAreaLoaded(pos, 1)) {
				return;
			}

			if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				this.grow(world, pos, state, rand);
			}
		}

	}

	public void grow(World world, BlockPos pos, IBlockState state, Random rand) {
		if(this.maxStage<=0) return;
		int stage=state.getValue(STAGE)+1;
		if(stage==maxStage)
		{
			generateTree(world, pos, state, rand);
		}
		else
		{
			world.setBlockState(pos, null, 4); // fixme
		}
	}

	public void generateTree(World world, BlockPos pos, IBlockState state, Random rand) {
		if (TerrainGen.saplingGrowTree(world, rand, pos)) {
			generate.gen(world,pos);
		}
	}

	public boolean isTypeAt(World p_isTypeAt_1_, BlockPos p_isTypeAt_2_, BlockPlanks.EnumType p_isTypeAt_3_) {
		return false;
	}

	public int damageDropped(IBlockState p_damageDropped_1_) {
		return 0;
	}

	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this));
	}

	public boolean canGrow(World p_canGrow_1_, BlockPos p_canGrow_2_, IBlockState p_canGrow_3_, boolean p_canGrow_4_) {
		return true;
	}

	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
		return maxStage>0 && rand.nextFloat()>0.45f;
	}

//	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
//		this.grow(world, pos, state, rand);
//	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(STAGE).intValue();
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{TYPE, STAGE});
	}
}
