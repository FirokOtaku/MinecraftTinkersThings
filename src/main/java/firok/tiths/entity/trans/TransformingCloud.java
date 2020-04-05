package firok.tiths.entity.trans;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Predicate;

import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

public class TransformingCloud extends Entity
{
	/**
	 * 主颜色
	 */
	public int colorMain;
	/**
	 * 副颜色
	 */
	public int colorSub;
	/**
	 * 夹杂的颜色
	 */
	public int colorBase;

	/**
	 * 最大持续时间
	 */
	public int timeMax;
	/**
	 * 转变触发间隔
	 */
	public int interval;
	/**
	 * 转变触发偏移
	 */
	public int offset;
	/**
	 * 单次最多转换多少方块
	 */
	public int countPerTime;
	/**
	 * 单次转换成功几率
	 */
	public float ratePerTime;

	/**
	 * 当前已经转换了多少方块
	 */
	public int countNow;
	/**
	 * 总共最多转变多少方块
	 */
	public int countTotal;

	/**
	 * 判断什么样的方块可以转变
	 */
	public Predicate<IBlockState> canTransform;
	public IBlockState stateTarget;

	public TransformingCloud(World worldIn)
	{
		super(worldIn);
	}

	@Override
	protected void entityInit()
	{
		this.setSize(0,0);
	}

//	public TransformingCloud(World worldIn, double x, double y, double z)
//	{
//		super(worldIn, x, y, z);
//	}

	private static final float PI=(float)Math.PI;
	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		if(this.ticksExisted >= this.timeMax)
		{
//			TinkersThings.log(world.isRemote?"dead at client":"dead at server");
			this.setDead();
			return;
		}

		if(this.world.isRemote) // 客户端生成粒子
		{
//			float radius = Math.min( 6 , (this.timeMax - this.ticksExisted) * 2 ); // 半径 暂时没用

//			float pmx = MathHelper.sin(rand.nextFloat() * 2 * PI); // particle motion X
//			float pmz = MathHelper.cos(rand.nextFloat() * 2 * PI); // particle motion Z
//			float pmy = rand.nextFloat() * 0.15f * (rand.nextFloat() > 0.25 ? 1 : -1);
			int colorNow = this.colorMain;
			double cx=this.posX+rand.nextFloat() * 0.6 - 0.3;
			double cz=this.posZ+rand.nextFloat() * 0.6 - 0.3;
			int r = colorNow >> 16 & 255;
			int g = colorNow >> 8 & 255;
			int b = colorNow & 255;
			this.world.spawnAlwaysVisibleParticle(EnumParticleTypes.SPELL_MOB.getParticleID(),
					cx , this.posY, cz,
					(r / 255F), (g / 255F), (b / 255F)
//					pmx,pmy,pmz,
			);
		}
		else // 服务端转变方块
		{
			if(this.canTransform !=null && stateTarget!=null && canTick(this.ticksExisted,this.interval,this.offset) && canTrigger(rand,ratePerTime))
			{
				int tempInterval=(this.timeMax-this.ticksExisted)/43;
				int radius=1 + (tempInterval>0? rand.nextInt(tempInterval):0);

				for(int time=0;time<this.countPerTime;time++)
				{
					BlockPos posTemp = new BlockPos(
							(int)(this.posX+radius-rand.nextInt(2*radius)),
							(int)(this.posY+1-rand.nextInt(4)),
							(int)(this.posZ+radius-rand.nextInt(2*radius))
					);

					IBlockState state=world.getBlockState(posTemp);
//					log("block:"+state);

					boolean canTransform= this.canTransform.test(state);
					if(canTransform)
					{
						world.setBlockState(posTemp,stateTarget);
					}
				}
			}
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		this.colorMain=nbt.hasKey("COLOR_MAIN")?nbt.getInteger("COLOR_MAIN"):0;
		this.colorSub=nbt.hasKey("COLOR_SUB")?nbt.getInteger("COLOR_SUB"):0;
		this.colorBase=nbt.hasKey("COLOR_BASE")?nbt.getInteger("COLOR_BASE"):0;
		this.timeMax=nbt.hasKey("TIME_MAX")?nbt.getInteger("TIME_MAX"):160;
		this.interval=nbt.hasKey("INTERVAL")?nbt.getInteger("INTERVAL"):20;
		this.offset=nbt.hasKey("OFFSET")?nbt.getInteger("OFFSET"):0;
		this.countPerTime=nbt.hasKey("COUNT_PER_TIME")?nbt.getInteger("COUNT_PER_TIME"):1;
		this.countTotal=nbt.hasKey("COUNT_TOTAL")?nbt.getInteger("COUNT_TOTAL"):1;
		this.canTransform=null;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("COLOR_MAIN",this.colorMain);
		nbt.setInteger("COLOR_SUB",this.colorSub);
		nbt.setInteger("COLOR_BASE",this.colorBase);
		nbt.setInteger("TIME_MAX",this.timeMax);
		nbt.setInteger("INTERVAL",this.interval);
		nbt.setInteger("OFFSET",this.offset);
		nbt.setInteger("COUNT_PER_TIME",this.countPerTime);
		nbt.setInteger("COUNT_TOTAL",this.countTotal);

	}
}
