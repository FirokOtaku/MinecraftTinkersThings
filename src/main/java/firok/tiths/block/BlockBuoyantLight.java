package firok.tiths.block;

import firok.tiths.common.Items;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

/**
 * 浮灯
 */
public class BlockBuoyantLight extends BlockCompressed
{
	public BlockBuoyantLight()
	{
		super(Material.GLASS);
		this.setHardness(0.1f);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return net.minecraft.init.Items.AIR;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Items.buoyantLight);
	}
}
