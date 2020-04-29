package firok.tiths.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("all")
public class BlockCompressed extends Block
{
	public BlockCompressed()
	{
		this(Material.IRON);
	}
	public BlockCompressed(Material material)
	{
		this(material,material.getMaterialMapColor());
	}
	public BlockCompressed(Material material, MapColor color)
	{
		super(material, color);
	}

	protected boolean isTransparent =false;

	/**
	 * 启用方块渲染透明度
	 */
	public BlockCompressed enableTransparent()
	{
		this.isTransparent =true;
		return this;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockstateThis, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		if(!isTransparent) return super.shouldSideBeRendered(blockstateThis, world, pos, side);

		IBlockState blockstateNearby = world.getBlockState(pos.offset(side));
		Block block = blockstateNearby.getBlock();

		if (blockstateThis != blockstateNearby)
		{
			return true;
		}

		if (block == this)
		{
			return false;
		}

		return super.shouldSideBeRendered(blockstateThis, world, pos, side);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return isTransparent ? BlockRenderLayer.TRANSLUCENT: BlockRenderLayer.SOLID;
	}

	public boolean isFullCube(IBlockState state)
	{
		return !isTransparent;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return !isTransparent;
	}

	protected boolean canSilkHarvest()
	{
		return true;
	}

}
