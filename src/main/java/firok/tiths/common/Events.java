package firok.tiths.common;


import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static firok.tiths.common.Traits.thermalGathering;
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
			int amount=1+world.rand.nextInt(3);
			ItemStack stack=new ItemStack(Items.brokenIce,amount);
			EntityItem ei=new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(),stack);

			world.spawnEntity(ei); // spawn broken ice
			break TRY_DROP_BROKEN_ICE;
		}
		// 贝壳
		else TRY_DROP_SHELL:if(block== Blocks.SAND)
		{
			if(!canTrigger(world,0.14f)) break TRY_DROP_SHELL;

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
		else if(living instanceof EntityDragon) // 末影龙
		{
			stack2drop=new ItemStack(Items.enderDragonSquama);
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

	@SubscribeEvent
	public static void onEntityJoin(net.minecraftforge.event.entity.EntityJoinWorldEvent event)
	{
		World world=event.getWorld();
		Random rand=world.rand;
		if(world.isRemote) return; // 只在服务端进行

		Entity en=event.getEntity();
		if(en instanceof EntityLightningBolt)
		{
			BlockPos pos=en.getPosition(); // 中心位置

			IBlockState stateFulgurite=firok.tiths.common.Blocks.blockFulgurite.getDefaultState(); // 闪电熔岩

			final short depth=(short)(-5-rand.nextInt(4));
			for(int ox=-1;ox<=1;ox++)
			{
				for(int oz=-1;oz<=1;oz++)
				{
					for(int oy=0;oy>=depth;oy--)
					{
						BlockPos posTemp=pos.add(ox,oy,oz);
						Block block=world.getBlockState(posTemp).getBlock();


						if(ox==oz && ox==0 && oy!=depth)
						{
							if(block==Blocks.STONE ||
								block==Blocks.COBBLESTONE ||
								block==Blocks.DIRT ||
								block==Blocks.SAND ||
								block instanceof BlockFluidBase ||
								block instanceof BlockLeaves ||
								block instanceof BlockLog
							)
								world.setBlockToAir(posTemp);
						}
						else
						{
							if(block==Blocks.STONE || block==Blocks.COBBLESTONE)
								world.setBlockState(posTemp,stateFulgurite);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onMobDrop(LivingDropsEvent event)
	{
		EntityLivingBase enlb=event.getEntityLiving();
		EntityLivingBase enlbRevTarget=enlb.getRevengeTarget();
		if(enlbRevTarget instanceof EntityPlayer && enlbRevTarget.isEntityAlive())
		{
			EntityPlayer player=(EntityPlayer)enlbRevTarget;
			ItemStack stack=player.getHeldItemMainhand();
			final List<EntityItem> drops=event.getDrops();

			// 检查有没有各类属性
			boolean hasMidasDesiring=ToolHelper.getTraits(stack).contains(Traits.midasDesiring);
			boolean hasGluttonic=ToolHelper.getTraits(stack).contains(Traits.gluttonic);

			if(hasMidasDesiring && !hasGluttonic) // 迈达斯之欲 转化物品 // 有暴食的话没必要执行了 // info 其实还能优化 但是懒得优化了
			{
				for(EntityItem ei:drops)
				{
					ItemStack stackEi=ei.getItem();
					if(stackEi.getItem()== Item.getItemFromBlock(Blocks.GOLD_ORE))
					{
						ei.setItem(new ItemStack(net.minecraft.init.Items.GOLD_INGOT,stackEi.getCount()*2));
					}
					else if(canTrigger(enlb.world,0.04f))
					{
						ei.setItem(new ItemStack(net.minecraft.init.Items.GOLD_INGOT,stackEi.getCount()));
					}
				}
			}
			if(hasGluttonic) drops.clear(); // 暴食 清空掉落物
		}

	}

	@SubscribeEvent
	public static void onEntityDamaged(LivingHurtEvent event)
	{
		EntityLivingBase enlb=event.getEntityLiving();
		DamageSource source=event.getSource();
		float originDamage=event.getAmount();
		if(source.isFireDamage()) // 判断是不是火焰伤害
		{
			List<ITrait> traits=new ArrayList<>();
			traits.addAll(ToolHelper.getTraits(enlb.getHeldItemMainhand()));
			traits.addAll(ToolHelper.getTraits(enlb.getHeldItemOffhand()));
			if(traits.contains(thermalGathering))
			{
				event.setAmount( originDamage / 2 );
			}
		}
	}
}
