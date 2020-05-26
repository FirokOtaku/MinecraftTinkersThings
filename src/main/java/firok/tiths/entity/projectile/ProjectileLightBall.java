package firok.tiths.entity.projectile;

import com.google.common.base.Optional;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Selectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.InnerActions.FAC;

public class ProjectileLightBall extends Entity
{

	private static final DataParameter<Optional<UUID>> UUID_ENTITY_CENTER
			= EntityDataManager.createKey(ProjectileLightBall.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	private static final DataParameter<Float> ANGLE_SURROUNDING
			= EntityDataManager.createKey(ProjectileLightBall.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> HEIGHT_SURROUNDING
			= EntityDataManager.createKey(ProjectileLightBall.class, DataSerializers.FLOAT);

	/**
	 * 中心实体
	 */
	Entity entityCenter;
	UUID getUUIDEntityCenter()
	{
		return this.dataManager.get(UUID_ENTITY_CENTER).orNull();
	}
	void setUUIDEntityCenter(UUID uuidCenter)
	{
		this.dataManager.set(UUID_ENTITY_CENTER,Optional.fromNullable(uuidCenter));
	}

	/**
	 * 飞行偏转角
	 */
	float angleSurrounding;
	float getAngleSurrounding()
	{
		return this.angleSurrounding;
	}
	void setAngleSurrounding(float angleSurrounding)
	{
		this.angleSurrounding=angleSurrounding;
		this.dataManager.set(ANGLE_SURROUNDING,angleSurrounding);
	}

	/**
	 * 飞行高度
	 */
	float heightSurrounding;
	float getHeightSurrounding()
	{
		return heightSurrounding;
	}
	void setHeightSurrounding(float heightSurrounding)
	{
		this.heightSurrounding=heightSurrounding;
		this.dataManager.set(HEIGHT_SURROUNDING,heightSurrounding);
	}

	boolean refresh()
	{
		if(this.world.isRemote && this.dataManager.isDirty())
		{
			List<EntityDataManager.DataEntry<?>> entries=this.dataManager.getDirty();
			if(entries!=null)
			{
				for(EntityDataManager.DataEntry<?> entry:entries)
				{
					DataParameter<?> dp=entry.getKey();
					Object value=entry.getValue();

					if(dp.equals(HEIGHT_SURROUNDING))
					{
						this.heightSurrounding=(float)value;
					}
					else if(dp.equals(ANGLE_SURROUNDING))
					{
						this.angleSurrounding=(float)value;
					}
					else if(dp.equals(UUID_ENTITY_CENTER))
					{
						this.entityCenter=world.getPlayerEntityByUUID((UUID)value);
						if(entityCenter==null)
						{
							this.setDead();
							return false;
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * 剩余存在时间
	 */
	int tickRemain;

	public ProjectileLightBall(World world)
	{
		super(world);
	}

	@Override
	protected void entityInit()
	{
		this.setSize(0,0);
		this.tickRemain=100;

		this.getDataManager().register(ANGLE_SURROUNDING, (float) world.rand.nextInt(360));
		this.getDataManager().register(HEIGHT_SURROUNDING, world.rand.nextFloat()*1.5f);
		this.getDataManager().register(UUID_ENTITY_CENTER, Optional.absent());

	}

	@Override
	public void onEntityUpdate()
	{
//		super.onEntityUpdate();

		if(--tickRemain<0)
		{
			this.setDead();
			return;
		}

		if(world.isRemote)
		{
			refresh();
		}

		world.spawnParticle(EnumParticleTypes.FLAME,
				this.posX,this.posY,this.posZ,
				0,0,0);

		long now=getNow();
		boolean calcPosResult=calcPos(now);
		if(!calcPosResult) return;

		if(canTick(now,3,0))
		{
			List<EntityLivingBase> list=checkCompact();
			if(list.size()>0)
			{
				for(EntityLivingBase enlb:list)
				{
					enlb.hurtResistantTime=0;
					enlb.attackEntityFrom(EntityDamageSource.IN_FIRE,1);
				}
				this.setDead();
			}
		}
	}


	/**
	 * 根据时间计算当前应该处于的位置
	 */
	public boolean calcPos(long now)
	{
		// 先检查玩家
		if(entityCenter==null)
		{
			UUID uuidEntityCenter=getUUIDEntityCenter();
			if(uuidEntityCenter==null)
			{
				this.setDead();
				return false;
			}
			EntityPlayer player=world.getPlayerEntityByUUID(uuidEntityCenter);
			if(player==null || !player.isEntityAlive() || player.world!=this.world)
			{
				this.setDead();
				return false;
			}
			entityCenter=player;
		}

		double nextPosX,nextPosY,nextPosZ;
		nextPosY=entityCenter.posY + heightSurrounding + 0.5;

		float angleTemp=angleSurrounding + now%40 * 9 * FAC;
		nextPosX=entityCenter.posX + MathHelper.cos(angleTemp) * 1.3;
		nextPosZ=entityCenter.posZ + MathHelper.sin(angleTemp) * 1.3;

		this.posX= nextPosX;
		this.posY= nextPosY;
		this.posZ= nextPosZ;
		this.setPositionNonDirty();
		this.world.updateEntityWithOptionalForce(this, false);

		BlockPos posNow=getPosition();
		if(!world.isAirBlock(posNow))
		{
			this.setDead();
			return false;
		}

		return true;
	}

	/**
	 * 计算碰撞生物列表
	 */
	public List<EntityLivingBase> checkCompact()
	{
		return new ArrayList<>((List) EntityFinders.Nearby(this, 1.6, Selectors.mobAlive));
	}

	long getNow()
	{
		return world.getTotalWorldTime();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		try
		{
			this.setUUIDEntityCenter(nbt.hasKey("uuidCenter")? UUID.fromString(nbt.getString("uuidCenter")):null);

			this.angleSurrounding= nbt.hasKey("angleSurrounding")? nbt.getFloat("angleSurrounding"): rand.nextFloat()*360;
			this.heightSurrounding= nbt.hasKey("heightSurrounding")? nbt.getFloat("heightSurrounding"): rand.nextFloat()*1.5f;
			this.tickRemain= nbt.hasKey("tickRemain")? nbt.getInteger("tickRemain"): 0;
		}
		catch (Exception e)
		{
			this.setDead();
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		UUID uuidCenter=getUUIDEntityCenter();
		if(uuidCenter!=null) nbt.setString("uuidCenter",uuidCenter.toString());

		nbt.setFloat("angleSurrounding",getAngleSurrounding());
		nbt.setFloat("heightSurrounding",getHeightSurrounding());
		nbt.setInteger("tickRemain",this.tickRemain);
	}

}
