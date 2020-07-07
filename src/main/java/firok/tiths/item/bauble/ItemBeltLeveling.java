package firok.tiths.item.bauble;

import baubles.api.BaubleType;
import firok.tiths.util.InnerActions;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static firok.tiths.util.Predicates.canTick;
import static net.minecraft.item.ItemBlock.setTileEntityNBT;

/**
 * @author Firok
 */
public class ItemBeltLeveling extends ItemBauble
{
	public ItemBeltLeveling()
	{
		super(BaubleType.BELT);
	}

	public IBlockState getBindState(ItemStack stack)
	{
		try
		{
			NBTTagCompound nbt=InnerActions.getNBT(stack);

			int idState=nbt.hasKey("state_id")?nbt.getInteger("state_id"):-1;
			if(idState<0) return null;

			return Block.getStateById(idState);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack=player.getHeldItem(hand);
		if(stack.getItem()!=this || stack.isEmpty()) return EnumActionResult.PASS;
		NBTTagCompound nbt= InnerActions.getNBT(stack);

		if(player.isSneaking()) // 潜行改变方块种类
		{
			IBlockState stateOld=getBindState(stack);
			boolean hasBindBlock=stateOld!=null;

			if(hasBindBlock) // 之前绑定了state
			{
				nbt.removeTag("state_id");
			}
			else // 之前没有绑定state
			{
				IBlockState stateNew=world.getBlockState(pos);
				Block blockNew=stateNew.getBlock();
				boolean hasTile=blockNew.hasTileEntity(stateNew);
				if(!hasTile)
				{
					nbt.setInteger("state_id",Block.getStateId(blockNew.getDefaultState()));
				}
			}
		}
		else // 非潜行改变维度和高度
		{
			int idDim=world.provider.getDimension();
			int height=pos.getY();
			nbt.setInteger("dim_id",idDim);
			nbt.setInteger("height",height);
		}

		return EnumActionResult.SUCCESS;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity)
	{
		if(entity==null || entity.world==null || !(entity instanceof EntityPlayer) || !canTick(entity.world,2,0)) return;
		World world=entity.world;
		EntityPlayer player=(EntityPlayer)entity;

		NBTTagCompound nbt=InnerActions.getNBT(itemstack);
		int idDim=nbt.hasKey("dim_id")?nbt.getInteger("dim_id"):Integer.MIN_VALUE;
		int height=nbt.hasKey("height")?nbt.getInteger("height"):-1;
		if(entity.posY>height && entity.posY-height<3 && idDim!=Integer.MIN_VALUE && height>=0 && world.provider.getDimension()==idDim)
		{
			int idState=nbt.hasKey("state_id")?nbt.getInteger("state_id"):-1;
			if(idState<0) return;

			IBlockState stateNew=Block.getStateById(idState);
			if(stateNew==null) return;
			ItemStack stackBlock=findBlockStack(player,stateNew);
			if(stackBlock==null) return;

			BlockPos posPlayer=entity.getPosition();
			BlockPos pos=new BlockPos(posPlayer.getX(),height,posPlayer.getZ());
			for(int ox=-1;ox<=1;ox++)
			{
				for(int oz=-1;oz<=1;oz++)
				{
					BlockPos posTemp=pos.add(ox,0,oz);
//					IBlockState stateOld=world.getBlockState(posTemp);
//					Block blockOld=stateOld.getBlock();
					placeAt(player,world,posTemp,stackBlock,stateNew);
				}
			}
		}
	}

	public int placeAt(EntityPlayer player, World world, BlockPos pos, ItemStack stackBlock, IBlockState stateNew)
	{
		IBlockState stateOld = world.getBlockState(pos);
		Block blockOld = stateOld.getBlock();

		if(!blockOld.isReplaceable(world, pos)) return -1;

		Block blockNew=stateNew.getBlock();

		if (!stackBlock.isEmpty() && player.canPlayerEdit(pos, EnumFacing.UP, stackBlock) && world.mayPlace(blockNew, pos, false, EnumFacing.UP, player))
		{
			if (placeBlockAt(stackBlock, player, world, pos, stateNew))
			{
				SoundType soundtype = stateNew.getBlock().getSoundType(stateNew, world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				if(!player.isCreative()) stackBlock.shrink(1);
			}

			return 1;
		}
		else
		{
			return 0;
		}
	}

	public boolean placeBlockAt(ItemStack stackBlock, EntityPlayer player, World world, BlockPos pos, IBlockState stateNew)
	{
		if (!world.setBlockState(pos, stateNew, 11)) return false;

		setTileEntityNBT(world, player, pos, stackBlock);
		stateNew.getBlock().onBlockPlacedBy(world, pos, stateNew, player, stackBlock);

		if (player instanceof EntityPlayerMP)
			CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stackBlock);

		return true;
	}

	public ItemStack findBlockStack(EntityPlayer player,IBlockState state)
	{
		if(player==null || player.inventory==null) return null;

		InventoryPlayer inv=player.inventory;
		int size=inv.getSizeInventory();
		for(int i=0;i<size;i++)
		{
			ItemStack stack=inv.getStackInSlot(i);
			if(stack.isEmpty()) continue;
			Item item=stack.getItem();
			if(!(item instanceof ItemBlock)) continue;
			ItemBlock itemBlock=(ItemBlock)item;
			Block block=itemBlock.getBlock();
			IBlockState stateBlock=block.getDefaultState();

			if(Block.getStateId(state)==Block.getStateId(stateBlock)) return stack;
		}

		return null;
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn)
	{
		super.addInformation(stack, world, list, flagIn);
		NBTTagCompound nbt=InnerActions.getNBT(stack);
		int idState=nbt.hasKey("state_id")?nbt.getInteger("state_id"):-1;
		int idDim=nbt.hasKey("dim_id")?nbt.getInteger("dim_id"):Integer.MIN_VALUE;
		int height=nbt.hasKey("height")?nbt.getInteger("height"):-1;

		if(idState>=0)
		{
			IBlockState state=Block.getStateById(idState);
			list.add(state.getBlock().getLocalizedName());
		}

		if(idDim!=Integer.MIN_VALUE && height>=0)
		{
			list.add(String.valueOf(idDim));
			list.add(String.valueOf(height));
		}
	}
}
