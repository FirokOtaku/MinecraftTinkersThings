package firok.tiths.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	protected boolean isGlassLike=false;
	public BlockCompressed enableTransparent()
	{
		this.isGlassLike=true;
		return this;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return isGlassLike? BlockRenderLayer.CUTOUT: BlockRenderLayer.SOLID;
	}

	public boolean isFullCube(IBlockState state)
	{
		return !isGlassLike;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return !isGlassLike;
	}

	protected boolean canSilkHarvest()
	{
		return true;
	}

}
