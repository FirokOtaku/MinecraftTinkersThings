package firok.tiths.common;


import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

import static firok.tiths.traits.TraitStonePhasing.costStone;
import static firok.tiths.util.Predicates.canTrigger;

@Mod.EventBusSubscriber
public class Events
{
	@SubscribeEvent
	public static void onBlockBroken(BlockEvent.BreakEvent event)
	{
		World world=event.getWorld();
		if(world.isRemote) return; // 只在服务端执行

		BlockPos pos=event.getPos();
		Block block=world.getBlockState(pos).getBlock();

		// 碎冰
		TRY_DROP_BROKEN_ICE:if(block== Blocks.ICE)
		{
			int amount=1+world.rand.nextInt(6);
			ItemStack stack=new ItemStack(Items.brokenIce,amount);
			EntityItem ei=new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(),stack);

			world.spawnEntity(ei); // spawn broken ice
			break TRY_DROP_BROKEN_ICE;
		}
		// 贝壳
		else TRY_DROP_SHELL:if(block== Blocks.SAND)
		{
			if(!canTrigger(world,0.28f)) break TRY_DROP_SHELL;

			Biome biome=world.getBiome(pos);
			if(biome== Biomes.BEACH ||
					biome==Biomes.COLD_BEACH ||
					biome==Biomes.OCEAN ||
					biome==Biomes.DEEP_OCEAN
			){
				int amount= 1+ world.rand.nextInt(2);
				ItemStack stack=new ItemStack(Items.shell,amount);
				EntityItem ei=new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(),stack);
				world.spawnEntity(ei);
			}
			break TRY_DROP_SHELL;
		}
	}

	@SubscribeEvent
	public static void onEntityDead(LivingDeathEvent event)
	{
		EntityLivingBase living=event.getEntityLiving();
		World world=living.world;
		if(world.isRemote) return;
		Random rand=world.rand;

		ItemStack stack2drop=null;
		if(living instanceof EntityCaveSpider) // 洞穴蜘蛛
		{
			if(canTrigger(rand,0.35f))
			{
				stack2drop=new ItemStack(Items.spiderLeg,1+rand.nextInt(2));
			}
		}
		else if(living instanceof EntitySpider) // 蜘蛛
		{
			if(canTrigger(rand,0.45f))
			{
				stack2drop=new ItemStack(canTrigger(rand,0.3f)?Items.hardSpiderLeg:Items.spiderLeg,1+rand.nextInt(1));
			}
		}
		else if(living instanceof EntityWitherSkeleton) // 凋灵骷髅
		{
			stack2drop=new ItemStack(Items.ingotInertWitherium,2+rand.nextInt(4));
		}
		else if(living instanceof EntityWither) // 凋灵
		{
			stack2drop=new ItemStack(Items.ingotWitherium,4+rand.nextInt(4));
		}

		// 掉落物品
		if(stack2drop!=null)
		{
			EntityItem ei=new EntityItem(world,living.posX,living.posY,living.posZ,stack2drop);
			world.spawnEntity(ei);
		}
	}

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
	{
		World world=event.getWorld();
		if(world.isRemote) return;

		try
		{
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
