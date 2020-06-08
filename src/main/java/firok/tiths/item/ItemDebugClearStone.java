package firok.tiths.item;

import firok.tiths.block.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static firok.tiths.util.Predicates.isAnyStone;

// 调试工具-清空石头
public class ItemDebugClearStone extends ItemCustom
{
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if(!world.isRemote && player.isCreative())
		{
			final int x=(int)player.posX,y=(int)player.posY,z=(int)player.posZ;
			for(int tx=x-30;tx<=x+30;tx++)
			{
				for(int tz=z-30;tz<=z+30;tz++)
				{
					for(int ty=y+10;ty>=0;ty--)
					{
						BlockPos pos=new BlockPos(tx,ty,tz);
						IBlockState state=world.getBlockState(pos);
						Block block=state.getBlock();
						if( block ==Blocks.LAVA || block==Blocks.FLOWING_LAVA || block==Blocks.NETHERRACK || block==Blocks.END_STONE ||
								block ==Blocks.WATER || block==Blocks.FLOWING_WATER || block==Blocks.BEDROCK
										|| (isAnyStone(block) || block == Blocks.DIRT || block == Blocks.GRASS) && ! (block instanceof BlockOre) )
						{
							world.setBlockToAir(pos);
						}
					}
				}
			}
		}

		return super.onItemRightClick(world, player, hand);
	}
}