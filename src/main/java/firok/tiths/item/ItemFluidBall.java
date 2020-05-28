package firok.tiths.item;

import firok.tiths.util.InnerActions;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.List;

/**
 * 液体球
 */
public class ItemFluidBall extends Item implements IFluid
{
	public ItemFluidBall()
	{
		super();
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack=player.getHeldItem(hand);

		if(FluidStack.loadFluidStackFromNBT(stack.getTagCompound())!=null) return ActionResult.newResult(EnumActionResult.PASS,stack);

//		if(stack.getItem() != this) return ActionResult.newResult(EnumActionResult.PASS,stack);

		BlockPos posCenter=player.getPosition();
		Fluid fluidType=null;
		int amount=0;

		for(int ox=-3;ox<=3;ox++)
		{
			for(int oz=-3;oz<=3;oz++)
			{
				for(int oy=-3;oy<=3;oy++)
				{
					BlockPos posTemp=posCenter.add(ox,oy,oz);
					IBlockState stateTemp=world.getBlockState(posTemp);
					Block blockTemp=stateTemp.getBlock();

					if(blockTemp==Blocks.AIR) continue;

					Fluid fluidTemp=null;
					int amountTemp=0;

					if(blockTemp instanceof BlockFluidBase)
					{
						BlockFluidBase blockFluid=(BlockFluidBase)blockTemp;
//						int quantaValue=blockFluid.getQuantaValue(world,posTemp);
//						int a=blockFluid.quantaPerBlock;
//						float per=blockFluid.getQuantaPercentage(world,posTemp);
//						System.out.printf("f:%s, qV:%d, per:%f\n",blockFluid.getLocalizedName(),quantaValue,per);
						fluidTemp=blockFluid.getFluid();
						float percent=blockFluid.getFilledPercentage(world,posTemp);
						if(percent>=1) amountTemp=1000;
					}
					else if(blockTemp == Blocks.WATER)
					{
						fluidTemp=FluidRegistry.WATER;
						amountTemp=1000;
					}
					else if(blockTemp == Blocks.LAVA)
					{
						fluidTemp=FluidRegistry.LAVA;
						amountTemp=1000;
					}

					if(fluidTemp==null || amountTemp<1000) continue;

					if(fluidType==null) // 之前没找到
					{
						fluidType=fluidTemp;
						amount+=amountTemp;
						world.setBlockToAir(posTemp);
					}
					else if(fluidType==fluidTemp) // 之前找到的和现在的一样
					{
						amount+=amountTemp;
						world.setBlockToAir(posTemp);
					}
				}
			}
		}

		if(fluidType!=null && amount>0)
		{
			setFluidStack(stack,new FluidStack(fluidType,amount));
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		InnerActions.addInformation(this,tooltip,flag);

		FluidStack fluidStack=getFluidStack(stack);

		if(fluidStack==null) return;

		tooltip.add(fluidStack.getUnlocalizedName());
		tooltip.add(fluidStack.amount + " mL");
	}
}
