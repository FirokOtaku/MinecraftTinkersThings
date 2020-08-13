package firok.tiths.block.punji;

import com.google.common.collect.ImmutableMap;
import firok.tiths.block.BlockCompressed;
import firok.tiths.common.DamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.TinkerRegistry;

import javax.annotation.Nonnull;
import java.util.Locale;

public abstract class BlockPunjiCustom extends BlockCompressed
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool NORTHEAST = PropertyBool.create("northeast");
	public static final PropertyBool NORTHWEST = PropertyBool.create("northwest");

	public BlockPunjiCustom()
	{
		this(Material.ROCK,SoundType.STONE);
	}
	public BlockPunjiCustom(Material material, SoundType soundType) {
		super(material);
		this.setSoundType(soundType);
		this.setCreativeTab(TinkerRegistry.tabGadgets);
		this.setHardness(3.0f);

		this.setDefaultState(getBlockState().getBaseState()
				.withProperty(FACING, EnumFacing.DOWN)
				.withProperty(NORTH, false)
				.withProperty(EAST, false)
				.withProperty(NORTHEAST, false)
				.withProperty(NORTHWEST, false));
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, NORTH, EAST, NORTHEAST, NORTHWEST);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta >= EnumFacing.values().length) {
			meta = EnumFacing.DOWN.ordinal();
		}
		EnumFacing face = EnumFacing.values()[meta];

		return this.getDefaultState().withProperty(FACING, face);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).ordinal();
	}

	@Nonnull
	@Override
	public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);

		int off = -facing.ordinal() % 2;

		EnumFacing face1 = EnumFacing.values()[(facing.ordinal() + 2) % 6];
		EnumFacing face2 = EnumFacing.values()[(facing.ordinal() + 4 + off) % 6];

		// North/East Connector
		IBlockState north = worldIn.getBlockState(pos.offset(face1));
		IBlockState east = worldIn.getBlockState(pos.offset(face2));
		if(north.getBlock() == this && north.getValue(FACING) == facing) {
			state = state.withProperty(NORTH, true);
		}
		if(east.getBlock() == this && east.getValue(FACING) == facing) {
			state = state.withProperty(EAST, true);
		}

		// Diagonal connections
		IBlockState northeast = worldIn.getBlockState(pos.offset(face1).offset(face2));
		IBlockState northwest = worldIn.getBlockState(pos.offset(face1).offset(face2.getOpposite()));
		if(northeast.getBlock() == this && northeast.getValue(FACING) == facing) {
			state = state.withProperty(NORTHEAST, true);
		}
		if(northwest.getBlock() == this && northwest.getValue(FACING) == facing) {
			state = state.withProperty(NORTHWEST, true);
		}


		return state;
	}


	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	 * IBlockstate
	 */
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		EnumFacing enumfacing = facing.getOpposite();

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public boolean canPlaceBlockOnSide(@Nonnull World worldIn, @Nonnull BlockPos pos, EnumFacing side) {
		return worldIn.isSideSolid(pos.offset(side.getOpposite()), side, true);
	}


	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		EnumFacing facing = state.getValue(FACING);

		if(!worldIn.isSideSolid(pos.offset(facing), facing.getOpposite(), true)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	/* Bounds */
	private static final ImmutableMap<EnumFacing, AxisAlignedBB> BOUNDS;
	static {
		ImmutableMap.Builder<EnumFacing, AxisAlignedBB> builder = ImmutableMap.builder();
		builder.put(EnumFacing.DOWN,  new AxisAlignedBB(0.1875, 0,      0.1875,  0.8125, 0.375, 0.8125));
		builder.put(EnumFacing.UP,    new AxisAlignedBB(0.1875, 0.625,  0.1875,  0.8125, 1,     0.8125));
		builder.put(EnumFacing.NORTH, new AxisAlignedBB(0.1875, 0.1875, 0,       0.8125, 0.8125, 0.375));
		builder.put(EnumFacing.SOUTH, new AxisAlignedBB(0.1875, 0.1875, 0.625,   0.8125, 0.8125, 1));
		builder.put(EnumFacing.EAST,  new AxisAlignedBB(0.625,  0.1875, 0.1875,  1,      0.8125, 0.8125));
		builder.put(EnumFacing.WEST,  new AxisAlignedBB(0,      0.1875, 0.1875,  0.375,  0.8125, 0.8125));

		BOUNDS = builder.build();
	}

	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDS.get(state.getValue(FACING));
	}

//	@Override
//	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
//		if(entity instanceof EntityLivingBase) {
//			float damage = 3f;
//			if(entity.fallDistance > 0) {
//				damage += entity.fallDistance * 1.5f + 2f;
//			}
//			entity.attackEntityFrom(DamageSource.CACTUS, damage);
//			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 1));
//		}
//	}
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if(entity instanceof EntityLivingBase)
		{
			hurtLiving(world,pos,state,(EntityLivingBase)entity);
		}
	}

	public void hurtLiving(World world, BlockPos pos, IBlockState state, EntityLivingBase enlb)
	{
		float damage = 3f;
		if(enlb.fallDistance > 0)
		{
			damage += enlb.fallDistance * 1.5f + 2f;
		}
		enlb.attackEntityFrom(DamageSources.PunjiDamage, damage);
		enlb.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 1));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
		return BlockFaceShape.UNDEFINED;
	}

	private enum Corner implements IStringSerializable
	{
		NONE_UP,
		NORTH_DOWN,
		EAST_UP,
		EAST_DOWN,
		SOUTH_UP,
		SOUTH_DOWN,
		WEST_UP,
		WEST_DOWN;

		@Override
		public String getName() {
			return this.toString().toLowerCase(Locale.US);
		}
	}
}
