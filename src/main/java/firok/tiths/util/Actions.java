package firok.tiths.util;

import com.google.common.base.Predicate;
import firok.tiths.TinkersThings;
import firok.tiths.common.Configs;
import firok.tiths.common.Potions;
import firok.tiths.entity.ai.EntityAIAvoidEntityFear;
import firok.tiths.entity.projectile.ProjectileDashingStar;
import firok.tiths.tile.logic.TEEnderInterferedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import static firok.tiths.util.InnerActions.*;
import static firok.tiths.util.Predicates.canTrigger;

// 所有的行为封装
public final class Actions
{
	private Actions(){}

	// 恐惧效果
	public static void CauseTargetFear(EntityLivingBase target,Class<? extends Entity> classToAvoid,int time)
	{
		World world=target.world;
		if(!world.isRemote)
		{
			if(target instanceof EntityMob)
			{
				EntityMob mob=(EntityMob)target;

				boolean hasFearAI=false;
				for(EntityAITasks.EntityAITaskEntry en:mob.tasks.taskEntries)
				{
					if(en.action instanceof EntityAIAvoidEntityFear && ((EntityAIAvoidEntityFear<? extends Entity>) en.action).getClassToAvoid() == classToAvoid)
					{
						hasFearAI=true;
						break;
					}
				}
				if(!hasFearAI)
				{
					mob.tasks.addTask(0, new EntityAIAvoidEntityFear<>(mob, classToAvoid, 12.0F, 1.0D, 1));
				}

				target.addPotionEffect(new PotionEffect(Potions.fear,time,0));
			}
		}
	}
	// 为目标叠加状态
	public static void CauseAccumEffect(EntityLivingBase target,PotionEffect pe)
	{
		if(pe==null) return;

		Potion potion=pe.getPotion();
		PotionEffect peOrigin=target.getActivePotionEffect(potion);
		if(peOrigin==null || peOrigin.getAmplifier()<pe.getAmplifier()) target.addPotionEffect(pe);
		else if(peOrigin.getAmplifier()==pe.getAmplifier())
		{
			target.addPotionEffect(new PotionEffect(potion,peOrigin.getDuration()+pe.getDuration(),pe.getAmplifier()));
		}
	}

	// 在世界生成物品
	public static void CauseSpawnItem(Entity target, ItemStack stack)
	{
		World world=target.world;
		EntityItem ei=new EntityItem(world,target.posX,target.posY,target.posZ,stack);
		world.spawnEntity(ei);
	}

	/**
	 * @param center 中心实体
	 * @param range 范围
	 * @param maxCharge 最大充能
	 * @param oneCharge 单次充能
	 * @param costMaxCharge 最大充能消耗单位
	 * @param costOneCharge 单次充能消耗单位
	 * @return 充能数量
	 */
	// 末影干涉
	public static int CauseEnderInterfering(
			Entity center, int range,
			int maxCharge, int oneCharge,
			int costMaxCharge, int costOneCharge
	)
	{
		return CauseEnderInterfering(center.world, center.getPosition(), range, maxCharge, oneCharge, costMaxCharge, costOneCharge);
	}
	public static int CauseEnderInterfering(
			World world, BlockPos center, int range,
			int maxCharge, int oneCharge,
			int costMaxCharge, int costOneCharge
	)
	{
		final IBlockState stateInterfered = firok.tiths.common.Blocks.blockEnderInterferedBlock.getDefaultState();
		int cost = 0;

		FOR_X: for (int ox = -range; ox <= range; ox++)
		{
			FOR_Y: for (int oy = 0; oy <= range; oy++)
			{
				FOR_Z: for (int oz = -range; oz <= range; oz++)
				{
					BlockPos posOffset = center.add(ox,oy,oz);
					IBlockState state = world.getBlockState(posOffset);
					Block block = state.getBlock();

					if(block == net.minecraft.init.Blocks.AIR ||
							block.getBlockHardness(state,world,posOffset) < 0 ||
							block.hasTileEntity(state) ||
							block instanceof BlockFluidBase ||
							block instanceof BlockLiquid)
						continue FOR_Z; // 不能干涉这类的

					TEEnderInterferedBlock te;
					if(block == firok.tiths.common.Blocks.blockEnderInterferedBlock) // 变了
					{
						TileEntity teTemp = world.getTileEntity(posOffset);
						if(teTemp == null) // 没设定TileEntity
						{
							// 这种情况不应该
							// 应该破坏掉这个方块 吧

//								te = new TEEnderInterferedBlock(state,20);
//								te.setWorld(world);
//								te.setPos(posOffset);
//								world.setTileEntity(posOffset,te);
//
//								cost += 2;
						}
						if(teTemp instanceof TEEnderInterferedBlock) // 内部已经有了
						{
							te = (TEEnderInterferedBlock) teTemp;
							if(te.ticksCharge <= oneCharge)
							{
								te.ticksCharge += oneCharge;
								cost += costOneCharge;
							}
						}
						else
						{
							continue FOR_Z;
						}

					}
					else // 还没变
					{
						te = new TEEnderInterferedBlock(state,maxCharge);
						te.setWorld(world);
						te.setPos(posOffset);

						world.setBlockState(posOffset,stateInterfered);
						world.setTileEntity(posOffset,te);

						cost += costMaxCharge;
					}
				}
			}
		}

		return cost;
	}

	// 末影传送
	public static void CauseEnderTeleport(EntityLivingBase entity)
	{
		if (teleportRandomly(entity))
		{
			entity.setPositionNonDirty();
			return;
		}
	}

	protected static boolean teleportRandomly(EntityLivingBase entity)
	{
		Random rand=entity.world.rand;
		double d0 = entity.posX + (rand.nextDouble() - 0.5D) * 64.0D;
		double d1 = entity.posY + (rand.nextInt(64) - 32);
		double d2 = entity.posZ + (rand.nextDouble() - 0.5D) * 64.0D;
		return teleportTo(entity,d0, d1, d2);
	}

	private static boolean teleportTo(EntityLivingBase entity,double x, double y, double z)
	{
		net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(entity, x, y, z, 0);
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;

		boolean flag = entity.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

		if (flag)
		{
			entity.world.playSound(null, entity.prevPosX, entity.prevPosY, entity.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, entity.getSoundCategory(), 1.0F, 1.0F);
			entity.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
		}

		return flag;
	}

	// 生成一个宝藏屋
	public static void CauseGeneratingTreasureRoom(World world, BlockPos pos)
	{
		final int cx=pos.getX(),cy=pos.getY(),cz=pos.getZ();
		final int mx=5,my=5,mz=5;
		for(byte tx=0;tx<mx;tx++)
		{
			for(byte tz=0;tz<mz;tz++)
			{
				for(byte ty=0;ty<my;ty++)
				{
					BlockPos posTemp=new BlockPos(cx+tx,cy+ty,cz+tz);
					if(tx==0||tx==mx-1||tz==0||tz==mz-1||ty==0||ty==my-1)
					{
						world.setBlockState(posTemp,Blocks.STONEBRICK.getDefaultState());
					}
					else
					{
						world.setBlockToAir(posTemp);

						if(ty==1 && tx==2 && tz==2)
						{
							world.setBlockState(posTemp,Blocks.CHEST.getDefaultState());
							TileEntityChest chest=(TileEntityChest)world.getTileEntity(posTemp);
							if(chest==null)
							{
								chest=new TileEntityChest();
								chest.setWorld(world);
								chest.setPos(posTemp);
								world.setTileEntity(posTemp,chest);
							}
							ResourceLocation listLootTable=null;
							double temp=world.rand.nextDouble();
							// fixme 这个表以后再改吧
							if(temp>0.4) listLootTable=LootTableList.CHESTS_SIMPLE_DUNGEON;
							else if(temp>0.2) listLootTable=LootTableList.CHESTS_VILLAGE_BLACKSMITH;
							else if(temp>0.15) listLootTable=LootTableList.CHESTS_NETHER_BRIDGE;
							else if(temp<0.025) listLootTable=LootTableList.CHESTS_END_CITY_TREASURE;
							chest.setLootTable(listLootTable,world.getTotalWorldTime());
//							{
//								LootTableManager manager = world.getLootTableManager()
//
//								ResourceLocation lootTableLocator = ;
//								LootTable table = manager.getLootTableFromLocation(lootTableLocator);
//// 注：LootContext.Builder 的构造器只接受 WorldServer，但 getLootTableManager 方法是 World 里的。
//								LootContext context = LootContext.Builder(world).withPlayer(player).withDamageSource(...).build();
//								List<ItemStack> loots = table.generateLootForPools(world.rand, context);
//							}
						}
					}
				}
			}
		}
	}

	private static final ArrayList<Class<? extends EntityLivingBase>> entityPassives=new ArrayList<>();
	static
	{
		entityPassives.add(EntityCow.class);
		entityPassives.add(EntitySheep.class);
		entityPassives.add(EntityPig.class);
		entityPassives.add(EntityChicken.class);
	}
	public static void registerPassive(Class<? extends EntityLivingBase> classPassive)
	{
		entityPassives.add(classPassive);
	}
	// 诱食 - 触发召唤
	public static void CauseSpawningPassives(EntityLivingBase player)
	{
		try
		{
			if(entityPassives.size()<=0) return;

			World world=player.world;
			Random rand=world.rand;
			// 寻找召唤位置
			final int cx=(int)player.posX,cy=(int)player.posY,cz=(int)player.posZ;
			final int tx=cx+rand.nextInt(16)-8,tz=cz+rand.nextInt(16)-8;

			Class<? extends EntityLivingBase> entityPassiveClass=entityPassives.get(rand.nextInt(entityPassives.size()));
			Constructor<? extends EntityLivingBase> con=entityPassiveClass.getConstructor(World.class);
			EntityLivingBase passive=con.newInstance(world);

			byte countAir=0;
			FOR_FIND_LOCATION_SPAWN:for(int ty=cy+5;ty>cy-5;ty--)
			{
				BlockPos posTemp=new BlockPos(tx,ty,tz);
				IBlockState state=world.getBlockState(posTemp);
				if(state.getBlock() == Blocks.AIR)
				{
					countAir++;
				}
				else // is not air
				{
					if(countAir>=3 && state.canEntitySpawn(passive))
					{
						passive.setPosition(posTemp.getX(),posTemp.getY()+2.5,posTemp.getZ());
						world.spawnEntity(passive);
						break FOR_FIND_LOCATION_SPAWN;
					}

					countAir=0;
				}
			}
		}
		catch(Exception ignored){}
	}
	// 亡灵呼唤 - 触发召唤
	public static void CauseSpawningUndead(EntityLivingBase player)
	{
		try
		{
			World world=player.world;
			Random rand=world.rand;
			// 寻找召唤位置
			final int cx=(int)player.posX,cy=(int)player.posY,cz=(int)player.posZ;
			final int tx=cx+rand.nextInt(18)-9,tz=cz+rand.nextInt(18)-9;

			EntityMob entity=rand.nextBoolean()?new EntityZombie(world):new EntitySkeleton(world);

			byte countAir=0;
			FOR_FIND_LOCATION_SPAWN:for(int ty=cy+5;ty>cy-5;ty--)
			{
				BlockPos posTemp=new BlockPos(tx,ty,tz);
				IBlockState state=world.getBlockState(posTemp);
				if(state.getBlock() == Blocks.AIR)
				{
					countAir++;
				}
				else // is not air
				{
					if(countAir>=3 && state.canEntitySpawn(entity))
					{
						entity.setPosition(posTemp.getX(),posTemp.getY()+2.5,posTemp.getZ());
						entity.setLastAttackedEntity(player);
						entity.playLivingSound();
						world.spawnEntity(entity);
						break FOR_FIND_LOCATION_SPAWN;
					}

					countAir=0;
				}
			}
		}catch(Exception ignored){}
	}
	// 喀嚓 - 触发召唤
	public static void CauseSpawningSilverfish(EntityLivingBase player, double x, double y, double z, World world)
	{
		try
		{
			EntitySilverfish entity = new EntitySilverfish(world);
			entity.setPosition(x, y, z);
			world.spawnEntity(entity);
			entity.setLastAttackedEntity(player);
			entity.playLivingSound();
		}catch(Exception ignored){}
	}

	// 异象 - 触发召唤
	public static void CauseSpawningPhantom(EntityLivingBase player)
	{
//		if(player.isServerWorld()) return; // 只在客户端召唤
//		World world=player.world;
//		Random rand=TinkersThings.randClient;
//		EntityLivingBase entity2spawn = new EntityZombie(world);
//
//		double px=player.posX-10+rand.nextInt(20);
//		double py=player.posY-10+rand.nextInt(20);
//		double pz=player.posZ-10+rand.nextInt(20);
//		entity2spawn.setPosition(px,py,pz);
//		entity2spawn.addPotionEffect(new PotionEffect(Potions.disappear,80,0));
//
//		world.spawnEntity(entity2spawn);
	}

	// 星绽 - 创建粒子
	public static ProjectileDashingStar CauseStarDashing(World world,double fromX,double fromY,double fromZ,double toX,double toY,double toZ,float speed,float damage,Entity shootingEntity)
	{
//		TinkersThings.log("gen star..."+System.currentTimeMillis());
		ProjectileDashingStar star=new ProjectileDashingStar(world,fromX,fromY,fromZ);
		star.shootingEntity=shootingEntity;

		double mod= (fromX-toX)*(fromX-toX)+(fromY-toY)*(fromY-toY)+(fromZ-toZ)*(fromZ-toZ);
		mod= MathHelper.sqrt(mod);

		star.motionX= speed * (toX-fromX) / mod;
		star.motionY= speed * (toY-fromY) / mod + 0.2;
		star.motionZ= speed * (toZ-fromZ) / mod;

		star.damage=damage;

		world.spawnEntity(star);
		return star;
	}
	public static ProjectileDashingStar CauseStarDashing(Entity from,Entity to,float speed,float damage,Entity shootingEntity)
	{
		ProjectileDashingStar star=CauseStarDashing(from.world,from.posX,from.posY+3,from.posZ,to.posX,to.posY+to.getEyeHeight(),to.posZ,speed,damage,shootingEntity);
		return star;
	}
	private static final float OffsetY=0.866f;
	private static final float OffsetY2=1.732f;
	public static ProjectileDashingStar[] CauseStarDashing(final World world,final double centerX,final double centerY,final double centerZ,final int amount,final float speed,final float damage,Entity shootingEntity)
	{
		ProjectileDashingStar[] ret=new ProjectileDashingStar[amount];
		Random rand=world.rand;

		for(int i=0;i<amount;i++)
		{
			double fromY= centerY-OffsetY+rand.nextDouble()*OffsetY2;
			double toY= fromY-OffsetY+rand.nextDouble()*OffsetY2;

			float rotXZ=rand.nextFloat()*2*(float)PI; // 从中心点到第一圈的角度
			float rotXZ2=rotXZ- (float)PI_6 +rand.nextFloat()*2*(float)PI_6; // 从第一圈向外的角度

			double fromX=centerX+ MathHelper.cos(rotXZ)*2;
			double fromZ=centerZ+ MathHelper.sin(rotXZ)*2;

			double toX=fromX+ MathHelper.cos(rotXZ2);
			double toZ=fromZ+ MathHelper.sin(rotXZ2);

			ret[i]=CauseStarDashing(world,fromX,fromY,fromZ,toX,toY,toZ,speed,damage,shootingEntity);

//			world.spawnEntity(ret[i]);
		}

//		if(!world.isRemote)
		world.playSound(null,centerX,centerY,centerZ, firok.tiths.common.SoundEvents.effectFire, SoundCategory.MASTER, 1, 1);

		return ret;
	}

	// 换位 - 转移
	public static void CauseSwitching(EntityLivingBase entity,EntityLivingBase target)
	{
		double pPosX=entity.posX,pPosY=entity.posY,pPosZ=entity.posZ;
		float pRotPitch=entity.rotationPitch,pRotYaw=entity.rotationYaw,pCamPitch=entity.cameraPitch;

		entity.rotationYaw=target.rotationYaw;
		entity.rotationPitch=target.rotationPitch;
		entity.setPositionAndUpdate(target.posX,target.posY,target.posZ);

		target.cameraPitch=pCamPitch;
		target.rotationYaw=pRotYaw;
		target.rotationPitch=pRotPitch;
		target.setPositionAndUpdate(pPosX,pPosY,pPosZ);
	}

	public static final double PI_2=Math.PI/2,PI_4=Math.PI/4,PI_6=Math.PI/6,PI=Math.PI;
	public static void CauseGatewayTeleport(Entity entity,float distance)
	{
		final World world=entity.world;
		final double px=entity.posX,pz=entity.posZ;
		final long lpx=(long)px,lpz=(long)pz;
		final int byt= (lpx%2==0?0:1) | (lpz%2==0?0:2);
		final float angle = (float)( -PI_4 + (byt* PI_2) + (world.rand.nextDouble()*PI_2) );
		final float distanceX=MathHelper.sin(angle)*distance;
		final float distanceZ=MathHelper.cos(angle)*distance;
		final BlockPos posTop=world.getTopSolidOrLiquidBlock(new BlockPos((int)distanceX+px,0,(int)distanceZ+pz));
		entity.setPosition(posTop.getX(),posTop.getY()+2,posTop.getZ());
	}

	public static void CauseAcidDamage(EntityLivingBase entity,int damage,boolean playSound)
	{
		for(ItemStack stackEqui:entity.getEquipmentAndArmor())
		{
			if(stackEqui.isItemStackDamageable())
				stackEqui.damageItem(damage,entity);
		}
	}

	public static void CauseLiquidFroze(World world,BlockPos center,int radius,float rate)
	{
		if(!world.isRemote && center!=null && radius>0 && rate>0)
		{
			final IBlockState stateIce=Blocks.ICE.getDefaultState();
			final IBlockState stateObsidian=Blocks.OBSIDIAN.getDefaultState();
			final IBlockState stateStone=Blocks.STONE.getDefaultState();
			FOR_X:for(int ox=-radius;ox<=radius;ox++)
			{
				FOR_Z:for(int oz=-radius;oz<=radius;oz++)
				{
					FOR_Y:for(int oy=-radius;oy<=radius;oy++)
					{
						BlockPos posTemp=center.add(ox,oy,oz);
						if(center.distanceSq(posTemp)>16 || !canTrigger(world,rate)) continue FOR_Y;

						IBlockState stateTemp=world.getBlockState(posTemp);
						Block blockTemp=stateTemp.getBlock();

						IBlockState stateNew=null;

						if( blockTemp == Blocks.WATER || blockTemp == Blocks.FLOWING_WATER)
						{
							stateNew = stateIce;
						}
						else if(blockTemp == Blocks.LAVA)
						{
							stateNew = stateObsidian;
						}
						else if(blockTemp == Blocks.FLOWING_LAVA)
						{
							stateNew = stateStone;
						}

						if(stateNew != null)
						{
							world.setBlockState(posTemp, stateNew);
						}
					}
				}
			}
		}
	}

	// 灭火
	public static void CauseFireExtinguishing(World world,Entity entity,float radius,boolean hasSteam,boolean hasSound)
	{
		CauseFireExtinguishing(world,entity.getPosition(),radius,hasSteam,hasSound);
	}
	public static void CauseFireExtinguishing(World world,int centerX,int centerY,int centerZ,float radius,boolean hasSteam,boolean hasSound)
	{
		CauseFireExtinguishing(world, new BlockPos(centerX, centerY, centerZ), radius, hasSteam, hasSound);
	}
	public static void CauseFireExtinguishing(World world,BlockPos pos,float radius,boolean hasSteam,boolean hasSound)
	{
		final int radiusInt=(int)radius;
		final float radiusSq=radius * radius;
		final int centerX=pos.getX(),centerY=pos.getY(),centerZ=pos.getZ();
		final boolean isRemote=world.isRemote;

		int countFireBlocks=0;
		FOR_X: for(int tempX=centerX-radiusInt;tempX<=centerX+radiusInt;tempX++)
		{
			FOR_Z: for(int tempZ=centerZ-radiusInt;tempZ<=centerZ+radiusInt;tempZ++)
			{
				FOR_Y: for(int tempY=centerY-radiusInt;tempY<=centerY+radiusInt;tempY++)
				{
					BlockPos posTemp=new BlockPos(tempX,tempY,tempZ);
					if(posTemp.distanceSq(pos) > radiusSq) continue FOR_Y;

					IBlockState stateTemp=world.getBlockState(posTemp);
					Block blockTemp=stateTemp.getBlock();
					boolean isFireTemp=blockTemp == Blocks.FIRE;

					if(!isFireTemp) continue FOR_Y;

					if(!isRemote) // 服务端 扑灭火焰
					{
						world.setBlockToAir(posTemp);
					}
					else // 产生烟雾
					{
						// todo 粒子类型估计不对
						world.spawnParticle(EnumParticleTypes.CLOUD,posTemp.getX(),posTemp.getY(),posTemp.getZ(),0,0,0);
					}

					countFireBlocks++;
				}
			}
		}

		if(hasSound && countFireBlocks > 0) // 播放音效
		{
			;
		}
	}

	/**
	 * 清除实体对玩家的攻击AI
	 */
	@Deprecated // 暂时没啥用了 估计要删掉
	public static void CauseAIIgnore(EntityLiving living)
	{
//		Iterator< EntityAITasks.EntityAITaskEntry > iter= living.targetTasks.taskEntries.iterator();
//		EntityAINearestAttackableTarget<?> aiAtkOld=null;
//		int pri=-1;
//		while(iter.hasNext())
//		{
//			EntityAITasks.EntityAITaskEntry aiEntry=iter.next();
//			EntityAIBase ai=aiEntry.action;
//			try
//			{
//
//				if(ai instanceof EntityAINearestAttackableTarget)
//				{
//					EntityAINearestAttackableTarget<?> aiAtk=(EntityAINearestAttackableTarget)ai;
//					Class<? extends EntityLivingBase> targetClass=
//							ObfuscationReflectionHelper.getPrivateValue(EntityAINearestAttackableTarget.class,aiAtk,"field_75307_b"); // (Class) get(EntityAINearestAttackableTarget.class,"targetClass",aiAtk);
//					if(targetClass.isAssignableFrom(EntityPlayer.class))
//					{
//						living.targetTasks.removeTask(ai);
//
//						aiAtkOld=aiAtk;
//						pri=aiEntry.priority;
//					}
//				}
//			}
//			catch (Exception e)
//			{
//				TinkersThings.log(e);
//			}
//		}
//
//		try
//		{
//			if(aiAtkOld!=null)
//			{
//				living.setAttackTarget(null); // 清空实体索敌信息
//
//				// 获取基类EntityAITarget参数 // RE-RE-REFLECTION !!! HELL YEAH !!!
//				EntityCreature creature=(EntityCreature) get(EntityAITarget.class,"taskOwner",aiAtkOld);
//				boolean shouldCheckSight=(boolean) get(EntityAITarget.class,"shouldCheckSight",aiAtkOld);
//				boolean nearbyOnly=(boolean) get(EntityAITarget.class,"nearbyOnly",aiAtkOld);
//
//				Class<?> classTarget=(Class<?>) get(EntityAINearestAttackableTarget.class,"targetClass",aiAtkOld);
//				int targetChance=(int) get(EntityAINearestAttackableTarget.class,"targetChance",aiAtkOld);
//				Predicate<EntityLivingBase> targetEntitySelectorOrigin=(Predicate<EntityLivingBase>) get(EntityAINearestAttackableTarget.class,"targetEntitySelector",aiAtkOld);
//
//				Predicate<EntityLivingBase> pre= target ->
//				{
//					if(target instanceof EntityPlayer) return false;
//					return targetEntitySelectorOrigin.apply(target);
//				};
//
//				EntityAINearestAttackableTarget<?> aiNew=new EntityAINearestAttackableTarget(creature,classTarget,targetChance,shouldCheckSight,nearbyOnly,pre);
//				living.targetTasks.addTask(pri,aiNew);
//			}
//		}
//		catch (Exception e)
//		{
//			TinkersThings.log(e);
//		}
	}

	/**
	 * 计算单位向量
	 */
	public static Vec3d CalcUnitVector(Vec3d vec)
	{
		double length=vec.x * vec.x + vec.y * vec.y + vec.z * vec.z;
		length = Math.sqrt(length);

		if(length!=0) return new Vec3d( vec.x / length, vec.y / length, vec.z / length );
		else return null;
	}

	/**
	 * 将输入值限定在对应正负性的范围内
	 */
	public static double rabs(double value,double abs)
	{
		double absMath=Math.abs(value); // 数学绝对值
		if(absMath < abs) // 输入值绝对值在范围内
		{
			return value;
		}
		else // 输入值不在范围内
		{
			return value > 0 ? abs : - abs;
		}
	}

	/**
	 * 用来求若干点到直线距离
	 * @param p1 直线上一点坐标
	 * @param facing 方向向量
	 * @param points 若干需要求的点
	 * @return 直线导致这些点的距离
	 */
	public static double[] distanceToLine(Vec3d p1,Vec3d facing,Vec3d... points)
	{
		if(points==null) return new double[0];
		double[] ret=new double[points.length];

		Vec3d p2=p1.add(facing); // 第2个点

		double d12=p1.distanceTo(p2); // 底边长度
		for(int i=0;i<points.length;i++)
		{
			// 海伦公式 Heron's formula
			// https://baike.baidu.com/item/%E6%B5%B7%E4%BC%A6%E5%85%AC%E5%BC%8F
			Vec3d d3=points[i];

			// 求出剩下两边长
			double d32=d3.distanceTo(p2);
			double d31=d3.distanceTo(p1);

			double p=(d12+d32+d31)/2; // 半周长
			double pp1=Math.abs(p-d12),pp2=Math.abs(p-d32),pp3=Math.abs(p-d31);

			double area= MathHelper.sqrt( p*pp1*pp2*pp3 ); // 三角形面积

			// 三角形面积=底×高÷2 => 三角形面积×2÷底=高
			ret[i] = area * 2 / d12;

//			System.out.printf("d12[%f],d31[%f],d32[%f],p[%f],area[%f],ret[%f],pp1~3[%f,%f,%f]\n",d12,d31,d32,p,area,ret[i],pp1,pp2,pp3);
		}

//		System.out.println("到直线距离"+Arrays.toString(ret));

		return ret;
	}

	public static int withShift(int minValue,int maxValue,float minRange,float maxRange,float current)
	{
		return (int)( minValue + (maxValue-minValue)*(current-minRange)/(maxRange-minRange) );
	}

	private static int _get_light_light=0;
	private static World _get_light_world=null;
	private static int _get_light_pos_x=Integer.MIN_VALUE;
	private static int _get_light_pos_y=Integer.MIN_VALUE;
	private static int _get_light_pos_z=Integer.MIN_VALUE;
	public static int getLight(Entity entity)
	{
		World world=entity.world;
		BlockPos pos=entity.getPosition();
		return getLight(world,pos);
	}
	/**
	 * 获取亮度
	 */
	public static int getLight(World world,BlockPos pos)
	{
		if(Configs.General.enable_single_thread_optimization)
		{
			if(world==_get_light_world && _get_light_pos_x==pos.getX() && _get_light_pos_y==pos.getY() && _get_light_pos_z==pos.getZ())
			{
				return _get_light_light;
			}
			_get_light_world=world;
			_get_light_pos_x=pos.getX();
			_get_light_pos_y=pos.getY();
			_get_light_pos_z=pos.getZ();
			_get_light_light=world.getLightFromNeighbors(pos);

			return _get_light_light;
		}
		else
		{
			return world.getLightFromNeighbors(pos);
		}
	}

	@SafeVarargs
	public static <T> T oneOf(Random rand,T...values)
	{
		if(rand==null || values==null || values.length<=0) return null;

		return values[rand.nextInt(values.length)];
	}
}
