package firok.tiths.common;


import firok.tiths.TinkersThings;
import firok.tiths.entity.special.EnderBeacon;
import firok.tiths.intergration.conarm.ArmorEvents;
import firok.tiths.item.IFluid;
import firok.tiths.item.ItemFluidBall;
import firok.tiths.util.Actions;
import firok.tiths.util.InnerActions;
import firok.tiths.util.Ranges;
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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.smeltery.events.TinkerSmelteryEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static firok.tiths.common.Configs.Traits.enable_gluttonic_clear;
import static firok.tiths.common.Traits.thermalGathering;
import static firok.tiths.traits.TraitStonePhasing.costStone;
import static firok.tiths.util.Predicates.canTrigger;

@Mod.EventBusSubscriber(modid=TinkersThings.MOD_ID)
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
		else if(living instanceof EntityWitherSkeleton && canTrigger(rand,0.3)) // 凋灵骷髅
		{
			stack2drop=new ItemStack(Items.witheringEssence,1);
		}
		else if(living instanceof EntityWither) // 凋灵
		{
			stack2drop=new ItemStack(Items.witheringEssence,2+rand.nextInt(3));
		}
		else if(living instanceof EntityDragon) // 末影龙
		{
			stack2drop=new ItemStack(Items.enderDragonSquama);
		}

		// 掉落物品
		if(stack2drop!=null)
		{
			Actions.CauseSpawnItem(living,stack2drop);
		}

		if(canTrigger(rand,0.005))
		{
			int temp=rand.nextInt(100);
			ItemStack record2drop;
			if(temp<33)
			{
				record2drop=new ItemStack(Items.recordTinkersEfforts);
			}
			else if(temp<66)
			{
				record2drop=new ItemStack(Items.recordTinkersImagination);
			}
			else
			{
				record2drop=new ItemStack(Items.recordTinkersWill);
			}
			Actions.CauseSpawnItem(living,record2drop);
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
		else if(en instanceof EntityFishHook)
		{
			try
			{
				EntityFishHook hook=(EntityFishHook)en;

				EntityPlayer player=hook.getAngler();
				if(player==null) return;

				InventoryPlayer inv=player.inventory;
				final int size=inv.getSizeInventory();
				for(int i=0;i<size;i++)
				{
					ItemStack stack=inv.getStackInSlot(i);
					if(stack.isEmpty()) continue;

					if(stack.getItem()!=Items.shell) continue;

					stack.shrink(1);

					hook.setLureSpeed(1);

					int lureSpeed=(int) InnerActions.get(EntityFishHook.class,"lureSpeed",hook);
					lureSpeed++;
					InnerActions.set(EntityFishHook.class,"lureSpeed",hook,lureSpeed);

					break;
				}
			}
			catch (Exception ignored) { }
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
			boolean hasGluttonic=enable_gluttonic_clear && ToolHelper.getTraits(stack).contains(Traits.gluttonic);

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

	@SuppressWarnings("all")
	@SubscribeEvent
	public static void onEntityEnderTeleport(EnderTeleportEvent event)
	{

		EntityLivingBase enlb=event.getEntityLiving();

		// 掉落末影裂隙碎片
		if(!enlb.world.isRemote && canTrigger(enlb.world,0.15f))
		{
			Actions.CauseSpawnItem(enlb,new ItemStack(Items.enderCreviceShard));
		}

		// 寻找周围的末影信标
		List<EnderBeacon> beacons=(List<EnderBeacon>)(List)enlb.world.getEntitiesWithinAABB(EnderBeacon.class,
				Ranges.Neighbour(enlb,16)
		);

		if(beacons.size()<=0) return;

		// 找一个最近的
		float minDistance=Float.MAX_VALUE;
		EnderBeacon beacon=null;

		for(EnderBeacon beaconTemp:beacons)
		{
			float distanceTemp=beaconTemp.getDistanceToEntity(enlb);
			if(distanceTemp<minDistance)
			{
				minDistance=distanceTemp;
				beacon=beaconTemp;
			}
		}

		// 传送过去
		event.setTargetX(beacon.posX);
		event.setTargetY(beacon.posY);
		event.setTargetZ(beacon.posZ);
	}

	// 允许在游戏内菜单更改配置文件
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(TinkersThings.MOD_ID))
		{
			ConfigManager.sync(TinkersThings.MOD_ID, Config.Type.INSTANCE);
		}
	}

	@SubscribeEvent
	public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(Configs.General.isShowLoginWarning())
		{
			EntityPlayer player=event.player;
			String name=TinkersThings.version.name().toLowerCase();
			player.sendMessage( new TextComponentTranslation("warning.tiths."+name) );
		}
	}

	@SubscribeEvent
	public static void onItemUsed(LivingEntityUseItemEvent.Finish event)
	{
		if(TinkersThings.enableConarm())
		{
			ArmorEvents.onItemUsed(event);
			/*
			广域化 - 护甲
			* */
		}
	}

	@SubscribeEvent
	public static void onPlayerWaken(PlayerWakeUpEvent event)
	{
		if(TinkersThings.enableConarm())
		{
			ArmorEvents.onPlayerWaken(event);
			/*
			相关内容:
			温软 - 护甲
			* */
		}
	}

//	@SubscribeEvent
//	public static void onLoot(LootTableLoadEvent event)
//	{
//		ResourceLocation name=event.getName();
//		LootTableManager manager=event.getLootTableManager();
//		LootTable table=event.getTable();
//
//		LootPool pool=new LootPool();
//	}

	private static boolean has(String[] strs,String str)
	{
		for(String temp:strs) if(str.equals(temp)) return true;
		return false;
	}

	@SubscribeEvent
	public static void onToolCrafting(TinkerCraftingEvent.ToolCraftingEvent event)
	{
		EntityPlayer player=event.getPlayer();
		ItemStack stack=event.getItemStack();
		Item item=stack.getItem();
		if(!player.world.isRemote)
		{
			try
			{
				// 输出部件列表信息
//				System.out.println("部件列表");
//				List<ItemStack> toolparts=event.getToolParts();
//				for(ItemStack toolpart:toolparts)
//				{
//					if(toolpart.isEmpty()) continue;
//					System.out.println(toolpart.getUnlocalizedName() + " : "+((IMaterialItem)toolpart.getItem()).getMaterialID(toolpart));
//				}

//				List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(stack));
//				List<PartMaterialType> component = itemToolCore.getRequiredComponents();

				// 获取输出材料和部件信息
//				System.out.println("\n材料:");
//				for(Material mat:materials)
//				{
//					System.out.print(mat.identifier);
//				}
//				System.out.println("\npmts :");
//				int i=0;
//				for(PartMaterialType pmt:component)
//				{
//					System.out.println("pmt "+i);
//					Set<IToolPart> parts=(Set<IToolPart>)Actions.get(PartMaterialType.class,"neededPart",pmt);
//					String[] neededTypes=(String[])Actions.get(PartMaterialType.class,"neededTypes",pmt);
//					System.out.println("parts :");
//					for(IToolPart part:parts)
//					{
//						System.out.println(part.toString());
//						if(part instanceof ToolPart)
//						{
//							ToolPart _part=(ToolPart)part;
//							System.out.println(_part.getUnlocalizedName());
//						}
//					}
//					System.out.println("needed types :");
//					System.out.println(Arrays.toString(neededTypes));
//					i++;
//				}

				// 工作台gui的代码
//				for(int i = 0; i < component.size(); i++) {
//					PartMaterialType pmt = component.get(i);
//					Material material = materials.get(i);
//
//					// get (one possible) toolpart used to craft the thing
//					Iterator<IToolPart> partIter = pmt.getPossibleParts().iterator();
//					if(!partIter.hasNext()) {
//						continue;
//					}
//
//					IToolPart part = partIter.next();
//					ItemStack partStack = part.getItemstackWithMaterial(material);
//					if(partStack != null) {
//						// we have the part, add it
//						tooltips.add(material.getTextColor() + TextFormatting.UNDERLINE + partStack.getDisplayName());
//
//						Set<ITrait> usedTraits = Sets.newHashSet();
//						// find out which stats and traits it contributes and add it to the tooltip
//						for(IMaterialStats stats : material.getAllStats()) {
//							if(pmt.usesStat(stats.getIdentifier())) {
//								tooltips.addAll(stats.getLocalizedInfo());
//								for(ITrait trait : pmt.getApplicableTraitsForMaterial(material)) {
//									if(!usedTraits.contains(trait)) {
//										tooltips.add(material.getTextColor() + trait.getLocalizedName());
//										usedTraits.add(trait);
//									}
//								}
//							}
//						}
//						tooltips.add("");
//					}
//				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onClientSoundPlay(PlaySoundEvent event)
	{
		if(TinkersThings.enableConarm())
		{
			ArmorEvents.onClientSoundPlay(event);
		}
	}

	@SubscribeEvent
	public static void onMelting(TinkerSmelteryEvent.OnMelting event)
	{
		if(event.itemStack==null) return;
		Item item=event.itemStack.getItem();
		if(item instanceof IFluid)
		{
			IFluid itemFluid=(IFluid)item;
			FluidStack fluidStack=itemFluid.getFluidStack(event.itemStack);
			if(fluidStack==null) return;

			event.result=fluidStack;
		}

	}
}
