package firok.tiths.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 錾制血腥石英块
 */
public class BlockBloodyChiseledQuartzBlock extends Block
{
	public BlockBloodyChiseledQuartzBlock()
	{
		super(Material.ROCK);
	}

	@Override
	public void onFallenUpon(World world, BlockPos pos, Entity entityIn, float fallDistance)
	{
		super.onFallenUpon(world, pos, entityIn, fallDistance);

//		if(entityIn instanceof EntityItem)
//		{
//			EntityItem ei=(EntityItem)entityIn;
//			ItemStack stack=ei.getItem();
//			Item item=stack.getItem();
//
//			if( item == Items.GHAST_TEAR)
//			{
//				stack.shrink(1);
//				ei.setItem(stack);
//
//				if(stack.getCount()>0)
//				{
//					ei.motionX+=-2+world.rand.nextFloat()*4;
//					ei.motionZ+=-2+world.rand.nextFloat()*4;
//				}
//			}
//		}
	}
}
