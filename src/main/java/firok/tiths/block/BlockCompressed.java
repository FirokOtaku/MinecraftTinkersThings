package firok.tiths.block;

import firok.tiths.util.InnerActions;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.Util;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

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
		setHardness(20);
		setResistance(30);
	}

	protected boolean isTransparent = false;
	protected boolean isBeaconBase = false;

	public BlockCompressed setBeaconBase()
	{
		this.isBeaconBase=true;
		return this;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
	{
		return this.isBeaconBase;
	}

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

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		InnerActions.addInformation(this, tooltip, advanced);

		if(isBeaconBase) tooltip.add(I18n.format("tooltip.tiths.beacon_base"));
	}

}
