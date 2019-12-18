package firok.tiths.common;


import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static firok.tiths.traits.TraitStonePhasing.costStone;

@Mod.EventBusSubscriber
public class Events
{
	@SubscribeEvent
	public static void onBlockBroken(BlockEvent.BreakEvent event)
	{
		World world=event.getWorld();
		if(world.isRemote) return;
		BlockPos pos=event.getPos();
		Block block=world.getBlockState(pos).getBlock();
		if(block== Blocks.ICE)
		{
			int amount=1+world.rand.nextInt(6);
			ItemStack stack=new ItemStack(Items.brokenIce,amount);
			EntityItem ei=new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(),stack);

			world.spawnEntity(ei); // spawn broken ice
		}

	}


	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
	{
		try
		{
			World world=event.getWorld();
			if(world.isRemote) return;

			// 石之相变
			Entity entity=event.getEntity();
			if(entity instanceof EntityPlayer)
			{
				BlockPos posClicked=event.getPos();
				BlockPos posReplace=posClicked.offset(event.getFace());
				boolean canReplace=world.getBlockState(posReplace).getBlock().isReplaceable(world,posReplace);

				if(canReplace)
				{
					EntityPlayer player=(EntityPlayer)entity;
					ItemStack stackMain=player.getHeldItem(EnumHand.MAIN_HAND);
					ItemStack stackSub=player.getHeldItem(EnumHand.OFF_HAND);


					if(costStone(stackMain) || costStone(stackSub))
					{
						world.setBlockState(posReplace,Blocks.COBBLESTONE.getDefaultState());
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
