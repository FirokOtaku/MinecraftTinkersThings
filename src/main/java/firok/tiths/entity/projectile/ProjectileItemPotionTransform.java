package firok.tiths.entity.projectile;

import firok.tiths.TinkersThings;
import firok.tiths.entity.trans.TransformingCloud;
import firok.tiths.util.Colors;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

// 投掷物-转化药水瓶
public class ProjectileItemPotionTransform extends EntityThrowable
{
	public ProjectileItemPotionTransform(World worldIn)
	{
		super(worldIn);
	}

	public ProjectileItemPotionTransform(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
	}
	public ProjectileItemPotionTransform(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}
	protected void entityInit()
	{}

	protected float getGravityVelocity()
	{
		return 0.05F;
	}

	protected void onImpact(RayTraceResult result)
	{
		if(!world.isRemote)
		{
			try
			{
				double tx=posX,ty=posY,tz=posZ;
				if (result.typeOfHit == RayTraceResult.Type.BLOCK)
				{
					BlockPos posExtinguishFire = result.getBlockPos().offset(result.sideHit);
					this.extinguishFires(posExtinguishFire, result.sideHit);
					for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
					{
						this.extinguishFires(posExtinguishFire.offset(enumfacing), enumfacing);
					}
					tx=posExtinguishFire.getX();
					ty=posExtinguishFire.getY()+1;
					tz=posExtinguishFire.getZ();
				}

				TransformingCloud cloud=new TransformingCloud(world);
				cloud.setPosition(tx,ty,tz);
				cloud.colorMain= Colors.Blue;
				cloud.timeMax=160;
				cloud.canTransform=(state)->state!=null&&state.getBlock()!= Blocks.AIR;
				cloud.stateTarget=Blocks.OBSIDIAN.getDefaultState();
				cloud.interval=4;
				cloud.offset=0;
				cloud.ratePerTime=1;
				cloud.countPerTime=2;
				world.spawnEntity(cloud);
				TinkersThings.log("spawned");

			}
			catch (Exception e){}
		}

		this.setDead();
	}

	private void extinguishFires(BlockPos pos, EnumFacing p_184542_2_)
	{
		if (this.world.getBlockState(pos).getBlock() == Blocks.FIRE)
		{
			this.world.extinguishFire(null, pos.offset(p_184542_2_), p_184542_2_.getOpposite());
		}
	}

	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
	}

	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
	}
}
