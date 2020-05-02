package firok.tiths.util;

import com.google.common.base.Predicate;
import firok.tiths.TinkersThings;
import firok.tiths.entity.projectile.ProjectileDashingStar;
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
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

// 所有的行为封装
public final class Actions
{
	private Actions(){}

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

	// 末影传送
	public static void CauseEnderTeleport(EntityLivingBase entity)
	{
		for (int i = 0; i < 64; ++i)
		{
			if (teleportRandomly(entity))
			{
				entity.setPositionNonDirty();
				return;
			}
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
	public static ProjectileDashingStar CauseStarDashing(World world,double fromX,double fromY,double fromZ,double toX,double toY,double toZ,float speed,float damage)
	{
//		TinkersThings.log("gen star..."+System.currentTimeMillis());
		ProjectileDashingStar star=new ProjectileDashingStar(world,fromX,fromY,fromZ);

		double mod= (fromX-toX)*(fromX-toX)+(fromY-toY)*(fromY-toY)+(fromZ-toZ)*(fromZ-toZ);
		mod= MathHelper.sqrt(mod);

		star.motionX= speed * (toX-fromX) / mod;
		star.motionY= speed * (toY-fromY) / mod + 0.2;
		star.motionZ= speed * (toZ-fromZ) / mod;

		star.damage=damage;

		world.spawnEntity(star);
		return star;
	}
	public static ProjectileDashingStar CauseStarDashing(Entity from,Entity to,float speed,float damage)
	{
		ProjectileDashingStar star=CauseStarDashing(from.world,from.posX,from.posY+3,from.posZ,to.posX,to.posY+to.getEyeHeight(),to.posZ,speed,damage);
		if(from instanceof EntityPlayer)
		{
			star.shotter=((EntityPlayer) from).getDisplayNameString();
		}
		return star;
	}
	private static final float OffsetY=0.866f;
	private static final float OffsetY2=1.732f;
	public static ProjectileDashingStar[] CauseStarDashing(final World world,final double centerX,final double centerY,final double centerZ,final int amount,final float speed,final float damage)
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

			ret[i]=CauseStarDashing(world,fromX,fromY,fromZ,toX,toY,toZ,speed,damage);

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

	/**
	 * 用反射抓数据
	 */
	public static Object get(Class<?> clasz,String fieldName,Object obj) throws NoSuchFieldException, IllegalAccessException
	{
		Field field=clasz.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(obj);
	}

	/**
	 * 清除实体对玩家的攻击AI
	 */
	public static void CauseAIIgnore(EntityLiving living)
	{
		Iterator< EntityAITasks.EntityAITaskEntry > iter= living.targetTasks.taskEntries.iterator();
		EntityAINearestAttackableTarget<?> aiAtkOld=null;
		int pri=-1;
		while(iter.hasNext())
		{
			EntityAITasks.EntityAITaskEntry aiEntry=iter.next();
			EntityAIBase ai=aiEntry.action;
			try
			{

				if(ai instanceof EntityAINearestAttackableTarget)
				{
					EntityAINearestAttackableTarget<?> aiAtk=(EntityAINearestAttackableTarget)ai;
					Class<? extends EntityLivingBase> targetClass=(Class) get(EntityAINearestAttackableTarget.class,"targetClass",aiAtk);
					if(targetClass.isAssignableFrom(EntityPlayer.class))
					{
						living.targetTasks.removeTask(ai);

						aiAtkOld=aiAtk;
						pri=aiEntry.priority;
					}
				}
			}
			catch (Exception e)
			{
				TinkersThings.log(e);
			}
		}

		try
		{
			if(aiAtkOld!=null)
			{
				living.setAttackTarget(null); // 清空实体索敌信息

				// 获取基类EntityAITarget参数 // RE-RE-REFLECTION !!! HELL YEAH !!!
				EntityCreature creature=(EntityCreature) get(EntityAITarget.class,"taskOwner",aiAtkOld);
				boolean shouldCheckSight=(boolean) get(EntityAITarget.class,"shouldCheckSight",aiAtkOld);
				boolean nearbyOnly=(boolean) get(EntityAITarget.class,"nearbyOnly",aiAtkOld);

				Class<?> classTarget=(Class<?>) get(EntityAINearestAttackableTarget.class,"targetClass",aiAtkOld);
				int targetChance=(int) get(EntityAINearestAttackableTarget.class,"targetChance",aiAtkOld);
				Predicate<EntityLivingBase> targetEntitySelectorOrigin=(Predicate<EntityLivingBase>) get(EntityAINearestAttackableTarget.class,"targetEntitySelector",aiAtkOld);

				Predicate<EntityLivingBase> pre= target ->
				{
					if(target instanceof EntityPlayer) return false;
					return targetEntitySelectorOrigin.apply(target);
				};

				EntityAINearestAttackableTarget<?> aiNew=new EntityAINearestAttackableTarget(creature,classTarget,targetChance,shouldCheckSight,nearbyOnly,pre);
				living.targetTasks.addTask(pri,aiNew);
			}
		}
		catch (Exception e)
		{
			TinkersThings.log(e);
		}
	}
}
