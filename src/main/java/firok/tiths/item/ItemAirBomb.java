package firok.tiths.item;

import firok.tiths.util.InnerActions;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.BlockFlags;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 空气弹
 */
public class ItemAirBomb extends ItemCustom
{
	public ItemAirBomb()
	{
		super();
		this.setMaxStackSize(16);
	}

	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
	{
		if(!world.isRemote)
		{
			IBlockState air=Blocks.AIR.getDefaultState();
			BlockPos center=entity.getPosition();
			final int flag=BlockFlags.SEND_TO_CLIENTS | BlockFlags.NO_RERENDER;
			FOR_X:for(int ix=-5;ix<=5;ix++)
			{
				FOR_Y:for(int iy=-5;iy<=5;iy++)
				{
					FOR_Z:for(int iz=-5;iz<=5;iz++)
					{
						if(ix * ix + iy* iy + iz * iz > 25) continue FOR_Z;

						BlockPos posTemp=center.add(ix,iy,iz);

						IBlockState stateTemp= world.getBlockState(posTemp);
						Block blockTemp=stateTemp.getBlock();

						if( blockTemp == Blocks.WATER || blockTemp == Blocks.FLOWING_WATER)
						{
							world.setBlockState(posTemp, air, flag);
						}
					}
				}
			}

			world.playSound(null,center,SoundEvents.ENTITY_GENERIC_EXPLODE,SoundCategory.MASTER,1,1);
			// todo 这个地方的音效可能要再加上个玻璃碎掉的声音 因为合成表里有玻璃制品
			world.markBlockRangeForRenderUpdate(-5,-5,-5,5,5,5);

			entity.setAir(300);
		}

		stack.shrink(1);
		return stack;
	}

	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 36;
	}

	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		InnerActions.addInformation(this, tooltip, flagIn);
	}

}
