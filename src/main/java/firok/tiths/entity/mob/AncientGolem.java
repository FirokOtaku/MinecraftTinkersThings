package firok.tiths.entity.mob;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

// 远古傀儡
public class AncientGolem extends EntityLiving
{
	boolean isCharging; // 是否在充能
	int chargeProcessMax; // 充能用时
	int chargeProcess; // 当前充能进度
	int chargePoint; // 每tick充能数量
	int energy; // 剩余能量
	int energyMax; // 最大能量

	public AncientGolem(World worldIn)
	{
		super(worldIn);
	}

	// 处理充能流程
	void processCharging()
	{
		if(isCharging)
		{
			chargeProcess++;
			if(chargeProcess>chargeProcessMax)
			{
				isCharging=false;
				chargeProcess=0;
			}
			energy+=chargePoint;
			if(energy>energyMax) energy=energyMax;
		}
	}

	// 开始充能
	void startCharging()
	{
		this.isCharging=true;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(energy<=0 || isCharging)
		{
			processCharging();
		}


	}
}
