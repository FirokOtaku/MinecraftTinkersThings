package firok.tiths.entity.special;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import static firok.tiths.util.Predicates.canTick;

// 末影信标
public class EnderBeacon extends Entity
{
	public int lastTime; // 剩余持续时间
	public EnderBeacon(World worldIn)
	{
		super(worldIn);
		setSize(0,0);
		setNoGravity(true);
		isImmuneToFire=true;
	}
	public EnderBeacon(World world,int lastTime)
	{
		this(world);
		this.lastTime=lastTime;
	}

	@Override
	protected void entityInit()
	{}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		this.lastTime=nbt.hasKey("last_time")?nbt.getInteger("last_time"):160;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("last_time",lastTime);
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

//		if(canTick(world,10,0))
//		{
//			TinkersThings.log("ticking ender beacon"+world.getWorldTime());
//		}

		lastTime--;
		if(lastTime<=0)
		{
			this.setDead();
			return;
		}

//		this.rotationPitch=(this.rotationPitch+5)%360;

		if(world.isRemote) // 客户端spawn粒子
		{
			for(int i=0;i<3;i++)
			{
				this.world.spawnParticle(EnumParticleTypes.PORTAL,
						this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width,
						this.posY + this.rand.nextDouble() * (double)this.height - 0.25D,
						this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width,
						(this.rand.nextDouble() - 0.5D) * 2.0D,
						-this.rand.nextDouble(),
						(this.rand.nextDouble() - 0.5D) * 2.0D);
			}
			if(canTick(lastTime,5,0))
				world.spawnParticle(EnumParticleTypes.CRIT_MAGIC,
						posX,posY,posZ,0,0,0);
		}
	}
}
