package firok.tiths.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 重力方块 掉落上来的实体按照指定倍数计算坠落距离
 */
public class BlockGravity extends BlockCompressed
{
	public float factor;
	public BlockGravity(Material blockMaterialIn,float factor)
	{
		super(blockMaterialIn);
		this.factor = factor;
	}

	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
	{
		super.onFallenUpon(worldIn, pos, entityIn, fallDistance * factor);
	}
}
